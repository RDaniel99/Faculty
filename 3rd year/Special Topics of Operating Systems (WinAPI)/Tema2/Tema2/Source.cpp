#include "ProcTree.cpp"
#pragma warning(disable:4996)

struct Entry
{
	DWORD pid;
	DWORD ppid;
	TCHAR procName[261];
};

int main2();
int main()
{
	HANDLE hProcessSnap;
	PROCESSENTRY32 pe32;
	vector<Entry> entries;

	hProcessSnap = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);
	if (hProcessSnap == INVALID_HANDLE_VALUE)
	{
		printf("CreateToolhelp32Snapshot failed.err = %d \n", GetLastError());
		return (-1);
	}

	pe32.dwSize = sizeof(PROCESSENTRY32);
	if (!Process32First(hProcessSnap, &pe32))
	{
		printf("Process32First failed. err = %d \n", GetLastError());
		CloseHandle(hProcessSnap);
		return -1;
	}
	USES_CONVERSION;
	int i = 0;
	do
	{
		Entry e;
		e.pid = pe32.th32ProcessID;
		e.ppid = pe32.th32ParentProcessID;
		strcpy(e.procName, pe32.szExeFile);
		entries.push_back(e);
	} while (Process32Next(hProcessSnap, &pe32));

	DWORD memSize = 2 * (entries.size() * sizeof(DWORD)) + (entries.size() * 261 * sizeof(WCHAR)) + ERROR;
	//_tprintf(_T("==>%d<==\n"), memSize);
	//_tprintf(_T("%d\n"), entries.size());
	HANDLE map_file = CreateFileMapping(NULL, NULL, PAGE_READWRITE, 0, memSize, TEXT("mem"));

	if (map_file == NULL)
	{
		_tprintf(_T("File mapping is null\n"));
		return 1;
	}

	char* map_ptr = (char*)MapViewOfFile(map_file, FILE_MAP_WRITE | FILE_MAP_READ, 0, 0, 0);

	if (map_ptr == NULL)
	{
		_tprintf(_T("PTR is null \n"));
	}


	DWORD entriesSize = entries.size();
	CopyMemory((LPVOID)map_ptr, &entriesSize, sizeof(DWORD));
	map_ptr += sizeof(DWORD);

	for (int i = 0; i < entries.size(); ++i)
	{
		CopyMemory((LPVOID)map_ptr, &entries[i].pid, sizeof(DWORD));
		map_ptr += sizeof(DWORD);

		CopyMemory((LPVOID)map_ptr, &entries[i].ppid, sizeof(DWORD));
		map_ptr += sizeof(DWORD);

		CopyMemory((LPVOID)map_ptr, entries[i].procName, 261 * sizeof(TCHAR));
		map_ptr += (261 * sizeof(TCHAR));
	}

	main2();

	CloseHandle(hProcessSnap);
	UnmapViewOfFile(map_ptr);

	return 0;
}


int main2()
{
	HANDLE ph;
	OpenProcessToken(GetCurrentProcess(), TOKEN_ADJUST_PRIVILEGES, &ph);

	LUID luid;

	TOKEN_PRIVILEGES tp;
	USES_CONVERSION;
	if (LookupPrivilegeValueW(NULL, A2W(SE_DEBUG_NAME), &luid))
	{

		tp.PrivilegeCount = 1;
		tp.Privileges[0].Luid = luid;

		AdjustTokenPrivileges(
			ph,
			false,
			&tp,
			sizeof(tp),
			(PTOKEN_PRIVILEGES)NULL,
			(PDWORD)NULL
		);
	}


	HANDLE map_file = OpenFileMapping(FILE_MAP_READ, false, TEXT("mem"));

	if (map_file == NULL)
	{
		_tprintf(_T("File mapping is null.\n"));
		std::cout << GetLastError();

		return 1;
	}

	char* map_ptr = (char*)MapViewOfFile(map_file, FILE_MAP_READ, 0, 0, 0);

	if (map_ptr == NULL)
	{
		_tprintf(_T("PTR is null \n"));
	}

	vector<Entry> entries;

	DWORD sz;
	DWORD actualRead;

	CopyMemory((LPVOID)&sz, map_ptr, sizeof(DWORD));
	map_ptr += sizeof(DWORD);

	for (int i = 0; i < sz; ++i)
	{
		Entry tmp;

		CopyMemory((LPVOID)&tmp.pid, map_ptr, sizeof(DWORD));
		map_ptr += sizeof(DWORD);

		CopyMemory((LPVOID)&tmp.ppid, map_ptr, sizeof(DWORD));
		map_ptr += sizeof(DWORD);

		CopyMemory((LPVOID)tmp.procName, map_ptr, sizeof(TCHAR) * 261);
		map_ptr += sizeof(TCHAR) * 261;

		if (tmp.pid == 0)
		{
			continue;
		}
		entries.push_back(tmp);
	}

	sz -= 1;

	// filter root nodes
	vector<ProcTree*> procTrees;
	vector<DWORD> roots;

	for (int i = 0; i < sz; ++i)
	{
		//_tprintf(_T("%d %d %s\n"), entries[i].pid, entries[i].ppid, entries[i].procName);
		bool foundRoot = true;
		for (int j = 0; j < sz; ++j)
		{
			if (i == j)
			{
				continue;
			}

			if (entries[i].ppid == entries[j].pid)
			{
				foundRoot = false;
				break;
			}
		}

		if (foundRoot)
		{
			procTrees.push_back(new ProcTree(entries[i].pid, entries[i].procName));
			roots.push_back(entries[i].pid);
			_tprintf(_T("Aici\n"));
		}
	}


	// distribute each process in it's corresponding tree process
	int allDistributed = sz - procTrees.size();

	while (allDistributed > 0)
	{
		for (int i = 0; i < sz; ++i)
		{
			// check if root
			for (int j = 0; j < procTrees.size(); ++j)
			{
				if (procTrees[j]->getRootPid() == entries[i].pid)
					// root found
					break;

				if (procTrees[j]->hasRootOf(entries[i].ppid))
				{
					procTrees[j]->insert(entries[i].pid, entries[i].ppid, entries[i].procName);
					allDistributed--;
					_tprintf(_T("Looks like it works %d\n"), i);
				}
			}
		}
	}

	for (int i = 0; i < procTrees.size(); ++i)
	{
		procTrees[i]->dumpTree(i + 1);
	}

	int option;

	std::cin >> option;

	if (option == 2) {
		int pid;
		std::cin >> pid;
		for (int i = 0; i < entries.size(); i++) {
			if (entries[i].pid == pid) {
				ProcTree* processTree = new ProcTree(pid, entries[i].procName);
				int ok = 1;
				while (ok > 0)
				{
					ok = 0;
					for (int i = 0; i < entries.size(); ++i)
					{
						if (processTree->getRootPid() == entries[i].pid)
							break;
						if (processTree->hasRootOf(entries[i].ppid))
						{
							processTree->insert(entries[i].pid, entries[i].ppid, entries[i].procName);
							ok = 1;
						}
					}
				}
				processTree->kill();
			}
		}
	}
	else {
		char nume[260];
		std::cin >> nume;
		for (int i = 0; i < entries.size(); i++) {
			if (strcmp(entries[i].procName, nume) == 0) {
				std::queue <int> q;
				q.push(entries[i].pid);
				int nr = 0;
				while (!q.empty()) {
					int pid = q.front();
					q.pop();
					for (int i = 0; i < entries.size(); i++) {
						if (entries[i].ppid == pid) {
							q.push(entries[i].pid);
						}
					}
					nr++;
				}
				std::cout << entries[i].pid << " " << nr - 1 << '\n';
			}
		}
	}
	return 0;
}