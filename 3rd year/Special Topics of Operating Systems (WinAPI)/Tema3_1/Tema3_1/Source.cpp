#include <windows.h>
#include <iostream>
#include <string.h>
#include <tchar.h>

using namespace std;

int main()
{
	DWORD memSize = 800 * sizeof(DWORD);
	HANDLE map_file = CreateFileMapping(NULL, NULL, PAGE_READWRITE, 0, memSize, TEXT("mem1"));

	if (map_file == NULL)
	{
		_tprintf(_T("(Parent) File mapping is null\n"));
		return 1;
	}

	char* map_ptr = (char*)MapViewOfFile(map_file, FILE_MAP_WRITE, 0, 0, 0);
	if (map_ptr == NULL)
	{
		_tprintf(_T("(Parent) PTR is null \n"));
	}

	//_tprintf(_T("Initializing the memory"));

	//for (int i = 0; i <= 400; ++i)
	//{
	//	DWORD placeholder = -1;
	//	CopyMemory((LPVOID)map_ptr, &placeholder, sizeof(DWORD));
	//	map_ptr += sizeof(DWORD);

	//	CopyMemory((LPVOID)map_ptr, &placeholder, sizeof(DWORD));
	//	map_ptr += sizeof(DWORD);
	//}

	//map_ptr -= sizeof(DWORD) * 802; // reset the pointer
	LPTSTR szCmdline = _tcsdup(TEXT("D:\\ADRIAN\\ady_info\\anul 3\\CSSO\\Tema3_2\\Debug\\Tema3_2.exe"));
	STARTUPINFO si;
	PROCESS_INFORMATION pi;
	ZeroMemory(&si, sizeof(si));
	si.cb = sizeof(si);
	ZeroMemory(&pi, sizeof(pi));

	if (!CreateProcess(NULL, szCmdline, NULL, NULL, FALSE, 0, NULL, NULL, &si, &pi))
	{
		_tprintf(_T("Process creation error\n"));
	}

	_tprintf(_T("pare ca s-a creat"));
	HANDLE writeEvent = CreateEvent(NULL, FALSE, TRUE, TEXT("write_event"));
	HANDLE readEvent = CreateEvent(NULL, FALSE, FALSE, TEXT("read_event"));

	for (int i = 1; i <= 400; ++i)
	{
		WaitForSingleObject(writeEvent, INFINITE);

		DWORD a = i, b = 2 * i;
		CopyMemory((LPVOID)map_ptr, &a, sizeof(DWORD));
		map_ptr += sizeof(DWORD);

		CopyMemory((LPVOID)map_ptr, &b, sizeof(DWORD));
		map_ptr += sizeof(DWORD);

		_tprintf(_T("( %d %d )\n"), a, b);

		SetEvent(readEvent);
	}

	int n;
	cin >> n;
	CloseHandle(writeEvent);
	CloseHandle(readEvent);
	CloseHandle(map_ptr);
	CloseHandle(map_file);

	return 0;
}