#include <windows.h>
#include <iostream>
#include <string.h>
#include <tchar.h>

int main()
{
	Sleep(100);
	HANDLE map_file = OpenFileMapping(FILE_MAP_READ, FALSE, TEXT("mem1"));

	if (map_file == NULL)
	{
		_tprintf(_T("(Child) File mapping is null\n"));
		return 1;
	}

	char* map_ptr = (char*)MapViewOfFile(map_file, FILE_MAP_READ, 0, 0, 0);

	if (map_ptr == NULL)
	{
		_tprintf(_T("(Child) PTR is null \n"));
	}

	_tprintf(_T("(CHILD) Before reading the first number\n"));
	HANDLE writeEvent = OpenEvent(NULL, TRUE, TEXT("write_event"));
	HANDLE readEvent = OpenEvent(NULL, TRUE, TEXT("read_event"));

	for (int i = 1; i <= 400; ++i)
	{
		WaitForSingleObject(readEvent, INFINITE);
		DWORD a, b;

		CopyMemory((LPVOID)&a, map_ptr, sizeof(DWORD));
		map_ptr += sizeof(DWORD);

		CopyMemory((LPVOID)&b, map_ptr, sizeof(DWORD));
		map_ptr += sizeof(DWORD);

		if (a * 2 == b) {
			_tprintf(_T("Correct!\n"));
		}
		else {
			_tprintf(_T("Incorrect!\n"));
		}

		SetEvent(writeEvent);
	}


	CloseHandle(map_file);
	CloseHandle(map_ptr);
	CloseHandle(writeEvent);
	CloseHandle(readEvent);

	return 0;
}