#include <string>
#include <iostream>
#include <cstdio>
#include <sys/wait.h>
#include <unistd.h>
#include <cstdlib>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/socket.h>
#include <fcntl.h>
#include <cstring>
#include <dirent.h>

using namespace std;

#define LOGIN "T1_FIFO_LOGIN"
#define QUIT "T1_FIFO_QUIT"

string command, comm;
string loggedIn;
string readString(int);
void login(string, int);
void quit(int);
void myStat(string, int);
void myFind(string, string, int);
int main() {
	while (1) {
		int pipeForCommand[2], socketPairForMyFind[2], socketPairForMyStat[2];
		if (pipe(pipeForCommand) == -1) {
			perror("Error when tried to call pipe: ");
			exit(1);
		}
		if (socketpair(AF_UNIX, SOCK_STREAM, 0, socketPairForMyFind) < 0)
		{
			perror("Error when tried to call socketpair: ");
			exit(1);
		}
		if (socketpair(AF_UNIX, SOCK_STREAM, 0, socketPairForMyStat) < 0)
		{
			perror("Error when tried to call socketpair: ");
			exit(1);
		}
		int pid = fork();
		mknod(LOGIN, S_IFIFO | 0666, 0);
		mknod(QUIT, S_IFIFO | 0666, 0);
		switch (pid) {
		case -1:
		{
			perror("Error when tried to call fork: ");
			exit(1);
		}
		case 0:
		{
			close(pipeForCommand[1]);
			command = "";
			command = readString(pipeForCommand[0]);
			close(pipeForCommand[0]);
			close(socketPairForMyFind[0]);
			close(socketPairForMyStat[0]);
			int fileDescriptorForLogin = open(LOGIN, O_WRONLY);
			int fileDescriptorForQuit = open(QUIT, O_WRONLY);
			if (command.substr(0, 5) == "login") {
				login(command.substr(6), fileDescriptorForLogin);
			}
			if (command.substr(0, 6) == "mystat") {
				myStat(command.substr(7), socketPairForMyStat[1]);
			}
			if (command.substr(0, 6) == "myfind") {
				myFind(".", command.substr(7), socketPairForMyFind[1]);
			}
			if (command.substr(0, 4) == "quit") {
				quit(fileDescriptorForQuit);
			}
			exit(0);
		}
		default:
		{
			getline(cin, command);
			close(pipeForCommand[0]);
			write(pipeForCommand[1], command.c_str(), command.size());
			close(pipeForCommand[1]);
			close(socketPairForMyFind[1]);
			close(socketPairForMyStat[1]);
			int fileDescriptorForLogin = open(LOGIN, O_RDONLY);
			int fileDescriptorForQuit = open(QUIT, O_RDONLY);
			wait(NULL);
			if (command.substr(0, 5) == "login") {
				string response = readString(fileDescriptorForLogin);
				if (response == "OK!") {
					loggedIn = command.substr(6);
				}
				cout << response << '\n';
			}
			if (command.substr(0, 6) == "mystat") {
				string response = readString(socketPairForMyStat[0]);
				cout << response;
			}
			if (command.substr(0, 6) == "myfind") {
				string response = readString(socketPairForMyFind[0]);
				cout << response;
			}
			if (command.substr(0, 4) == "quit") {
				string response = readString(fileDescriptorForQuit);
				if (response == "OK!") {
					exit(0);
				}
			}
		}
		}
	}
}

string readString(int fileDescriptor) {
	char chunk[1025];
	string str = "";
	int dim = 0;
	do {
		dim = read(fileDescriptor, chunk, 1024);
		chunk[dim] = '\0';
		str += chunk;
	} while (dim > 0);
	return str;
}

void login(string username, int fileDescriptor) {
	FILE* configFile = fopen("config.txt", "r");
	char user[300] = "";
	string okMessage = "OK!", notOkMessage = "Username is not in the config file!", alreadyLoggedIn = loggedIn + " is already logged in!";
	if (loggedIn != "") {
		write(fileDescriptor, alreadyLoggedIn.c_str(), alreadyLoggedIn.size());
		return;
	}
	while (fscanf(configFile, "%s\n", user) == 1) {
		string usr = user;
		if (usr == username) {
			write(fileDescriptor, okMessage.c_str(), okMessage.size());
			return;
		}
	}
	write(fileDescriptor, notOkMessage.c_str(), notOkMessage.size());
}

void quit(int fileDescriptor) {
	write(fileDescriptor, "OK!", 3);
}

void myStat(string filename, int fileDescriptor) {
	string notLoggedIn = "Nobody is logged in! Perform a login before trying to execute this command!\n";
	if (loggedIn == "") {
		write(fileDescriptor, notLoggedIn.c_str(), notLoggedIn.size());
		return;
	}
	struct stat fileAttributes;
	string statError = "Error at stat: ";
	if (stat(filename.c_str(), &fileAttributes) == -1) {
		statError += strerror(errno);
		write(fileDescriptor, statError.c_str(), statError.size());
		return;
	}
	string responseOk = "";
	responseOk += "User ID: " + to_string(fileAttributes.st_uid) + '\n';
	responseOk += "Group ID: " + to_string(fileAttributes.st_gid) + '\n';
	responseOk += "Preferred block size: " + to_string(fileAttributes.st_blksize) + '\n';
	responseOk += "Number of 512 bytes blocks: " + to_string(fileAttributes.st_blocks) + '\n';
	responseOk += "Size: " + to_string(fileAttributes.st_size) + '\n';
	char accessTime[100];
	strftime(accessTime, sizeof(accessTime), "%c", gmtime(&fileAttributes.st_atim.tv_sec));
	responseOk += "Last accessed: ", responseOk += accessTime, responseOk += '\n';
	char changedTime[100];
	strftime(changedTime, sizeof(changedTime), "%c", gmtime(&fileAttributes.st_ctim.tv_sec));
	responseOk += "Last changed: ", responseOk += changedTime, responseOk += '\n';
	char modifiedTime[100];
	strftime(modifiedTime, sizeof(modifiedTime), "%c", gmtime(&fileAttributes.st_mtim.tv_sec));
	responseOk += "Last modified: ", responseOk += modifiedTime, responseOk += '\n';
	responseOk += "Permissions (User, group and others): ";
	responseOk += (fileAttributes.st_mode & S_IRUSR) ? "r" : "-";
	responseOk += (fileAttributes.st_mode & S_IWUSR) ? "w" : "-";
	responseOk += (fileAttributes.st_mode & S_IXUSR) ? "x " : "- ";
	responseOk += (fileAttributes.st_mode & S_IRGRP) ? "r" : "-";
	responseOk += (fileAttributes.st_mode & S_IWGRP) ? "w" : "-";
	responseOk += (fileAttributes.st_mode & S_IXGRP) ? "x " : "- ";
	responseOk += (fileAttributes.st_mode & S_IROTH) ? "r" : "-";
	responseOk += (fileAttributes.st_mode & S_IWOTH) ? "w" : "-";
	responseOk += (fileAttributes.st_mode & S_IXOTH) ? "x\n" : "-\n";
	write(fileDescriptor, responseOk.c_str(), responseOk.size());
}

void myFind(string directoryName, string searchPiece, int fileDescriptor) {
	string notLoggedIn = "Nobody is logged in! Perform a login before trying to execute this command!\n";
	if (loggedIn == "") {
		write(fileDescriptor, notLoggedIn.c_str(), notLoggedIn.size());
		return;
	}
	DIR* dir = opendir(directoryName.c_str());
	struct dirent* entry;
	while ((entry = readdir(dir)) != NULL) {
		string name = entry->d_name;
		if (name == "." || name == "..") continue;
		if (entry->d_type == DT_DIR) {
			myFind(directoryName + '/' + name, searchPiece, fileDescriptor);
		}
		else {
			string filename = directoryName + '/' + entry->d_name;
			string type = "";
			type += (entry->d_type == DT_BLK) ? "Block device" : "";
			type += (entry->d_type == DT_CHR) ? "Character device" : "";
			type += (entry->d_type == DT_FIFO) ? "FIFO" : "";
			type += (entry->d_type == DT_LNK) ? "Symbolic link" : "";
			type += (entry->d_type == DT_REG) ? "Regular file" : "";
			type += (entry->d_type == DT_SOCK) ? "Local-domain socket" : "";
			type += (entry->d_type == DT_UNKNOWN) ? "Unknown" : "";
			if (filename.find(searchPiece) != string::npos) {
				write(fileDescriptor, ("Name: " + filename + '\n').c_str(), ("Name: " + filename + '\n').size());
				write(fileDescriptor, ("Type: " + type + '\n').c_str(), ("Type: " + type + '\n').size());
				myStat(filename, fileDescriptor);
			}
		}
	}
}