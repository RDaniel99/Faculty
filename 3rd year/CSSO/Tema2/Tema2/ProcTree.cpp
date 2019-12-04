#include <Windows.h>
#include <vector>
#include <tchar.h>
#include <stdio.h>
#include <iostream>
#include <psapi.h>
#include <cstring>
#include <TlHelp32.h>
#include <cstdlib>
#include <Wdbgexts.h>
#include <atlbase.h>
#include <queue>
#pragma warning(disable:4996)

using namespace std;

struct Node
{
	DWORD pid;
	DWORD ppid;
	TCHAR procName[261];
	vector<Node*> children;
};

class ProcTree
{
private:
	Node* root;

	bool internalHasRootOf(DWORD ppid, Node* node)
	{
		if (node->pid == ppid)
			return true;

		bool has = false;
		for (int i = 0; i < node->children.size(); ++i)
		{
			has = has || internalHasRootOf(ppid, node->children[i]);
		}

		return has;
	}

	void internalInsertChild(DWORD ppid, DWORD pid, TCHAR* procName, Node* node)
	{
		if (node->pid == ppid)
		{
			Node* newNode = new Node();
			newNode->pid = pid;
			newNode->ppid = ppid;
			strcpy(newNode->procName, procName);
			node->children.push_back(newNode);
		}

		for (int i = 0; i < node->children.size(); ++i)
		{
			internalInsertChild(ppid, pid, procName, node->children[i]);
		}
	}

	void internalDumpTreeContent(Node* node, int padTabs)
	{
		for (int i = 0; i < padTabs; ++i)
		{
			_tprintf(_T("    "));
		}
		_tprintf(_T("<%d><%d><%s>\n"), node->pid, node->ppid, node->procName);

		for (int i = 0; i < node->children.size(); ++i)
		{
			internalDumpTreeContent(node->children[i], padTabs + 1);
		}
	}

	void internalKill(Node* node)
	{
		for (int i = 0; i < node->children.size(); ++i)
		{
			internalKill(node->children[i]);
		}

		HANDLE handy = OpenProcess(SYNCHRONIZE | PROCESS_TERMINATE, TRUE, node->pid);
		TerminateProcess(handy, 0);
	}

public:
	ProcTree(DWORD pid, TCHAR* procName)
	{
		root = new Node();
		root->pid = pid;
		root->ppid = -1;
		strcpy(root->procName, procName);
	}

	DWORD getRootPid()
	{
		return root->pid;
	}

	bool hasRootOf(DWORD ppid)
	{
		return internalHasRootOf(ppid, root);
	}

	void insert(DWORD pid, DWORD ppid, TCHAR* procName)
	{
		internalInsertChild(ppid, pid, procName, root);
	}

	void dumpTree(int i)
	{
		_tprintf(_T("Tree number: %d\n"), i);
		internalDumpTreeContent(root, 0);
	}

	void kill()
	{
		internalKill(root);
	}
};