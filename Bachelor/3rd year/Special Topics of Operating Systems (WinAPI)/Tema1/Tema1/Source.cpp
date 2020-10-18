#include <Windows.h>
#include <string>
#include <iostream>

using namespace std;

string directoryName;
void putRegistryKeys(string, string);
int main() {
	cout << "Dati un director sub forma drive:\\director1\\director2 samd";
	cin >> directoryName;
	putRegistryKeys(directoryName, "Software\\CSSO");
}

void putRegistryKeys(string filePath, string registryPath) {
	WIN32_FIND_DATA fd;
	HANDLE hFind = FindFirstFile((filePath+"\\*").c_str(), &fd);
	if (hFind == INVALID_HANDLE_VALUE) {
		printf("error at FindFirstFile");
		exit(1);
	}
	do {
		string fileName = fd.cFileName;
		printf("%s\n", fileName.c_str());
		if (fileName == "." || fileName == "..") continue;
		if (fd.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY) {
			putRegistryKeys((filePath + "\\") + fd.cFileName, (registryPath + "\\" + fd.cFileName));
		}
		else {
			string registryName = fd.cFileName;
			LARGE_INTEGER fileSize;
			fileSize.HighPart = fd.nFileSizeHigh;
			fileSize.LowPart = fd.nFileSizeLow;
			long long registryValue = fileSize.QuadPart;
			HKEY registryKey;
			DWORD disposition = 0;
			if (RegCreateKeyEx(HKEY_LOCAL_MACHINE,registryPath.c_str(),0,NULL,REG_OPTION_NON_VOLATILE,KEY_WRITE,NULL,&registryKey,&disposition) != ERROR_SUCCESS) {
				printf("Nu s-a putut creea cheia. Cod eroare: %d\n", GetLastError());
				exit(1);
			}
			RegSetValueEx(
				registryKey,
				registryName.c_str(),
				0,
				REG_QWORD,
				(const BYTE*)&registryValue,
				sizeof(DWORDLONG));

			RegCloseKey(registryKey);
		}
	} while (FindNextFile(hFind, &fd));
}