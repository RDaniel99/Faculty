#include <Windows.h>
#include <WinInet.h>
#include <iostream>
#include <string>

using namespace std;

string readString(HANDLE);
int main() {
	HANDLE hInternet = InternetOpen(NULL, INTERNET_OPEN_TYPE_PRECONFIG, NULL, NULL, NULL);
	if (hInternet == INVALID_HANDLE_VALUE) {
		perror("InternetOpen error:");
		return -1;
	}
	HANDLE hURL = InternetOpenUrl(hInternet, "http://students.info.uaic.ro/~adrian.gotca/config.txt", NULL, NULL, NULL, NULL);
	if (hURL == INVALID_HANDLE_VALUE) {
		perror("InternetOpenUrl error:");
		return -1;
	}
	string s = readString(hURL);
	int n = stoi(s);
	string address = readString(hURL);
	string username = readString(hURL);
	string password = readString(hURL);
	HANDLE hConnect = InternetConnect(hInternet, address.c_str(), 21, username.c_str(), password.c_str(), INTERNET_SERVICE_FTP, NULL, NULL);
	if (hConnect == INVALID_HANDLE_VALUE) {
		perror("InternetConnect error:");
		return -1;
	}
	DWORD dwTemp, dwBytesRead = 1;
	for (int i = 0; i < n; i++) {
		string comm = readString(hURL);
		string path = readString(hURL);
		if (comm == "PUT") {
			FtpPutFile(hConnect, path.c_str(), path.substr(path.find_last_of("\\")).c_str(), NULL, NULL);
		}
		else {
			string fullPath = "C:\\Users\\adria\\Downloads\\" + path;
			FtpGetFile(hConnect, path.c_str(), fullPath.c_str(), false, NULL, NULL, NULL);
			ShellExecute(NULL, "open", path.c_str(), NULL, "C:\\Users\\adria\\Downloads", SW_SHOW);
		}
	}
	InternetCloseHandle(hURL);
	InternetCloseHandle(hInternet);

	return 0;
}

string readString(HANDLE h) {
	DWORD dwBytesRead = 0;
	string s = "";
	char c[2]="\0";
	do {
		InternetReadFile(h, c, 1, &dwBytesRead);
		c[dwBytesRead] = '\0';
		if (c[0] != '\0' && c[0] != ' ' && c[0] != '\n') {
			s += c;
		}
	} while (c[0] != '\0' && c[0] != ' ' && c[0] != '\n' && dwBytesRead);
	cerr << s << '\n';
	return s;
}