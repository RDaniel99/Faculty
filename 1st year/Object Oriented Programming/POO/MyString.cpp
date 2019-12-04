#include "stdafx.h"
#include "MyString.h"

#pragma warning(disable:4996)

MyString::MyString()
{
	sir = new char[100];
	AllocatedSize = 100;
	Size = 0;
}

MyString::MyString(const char * text)
{
	if (AllocatedSize < strlen(text)) {
		sir = new char[strlen(text) + 15];
		AllocatedSize = strlen(text) + 15;
	}
	strcpy(sir, text);
	Size = strlen(text);
}

MyString::~MyString()
{
	delete[] sir;
}

unsigned int MyString::GetSize(){
	return Size;
	return 0;
}

void MyString::Set(const char * text)
{
	if (AllocatedSize < strlen(text)) {
		delete[] sir;
		sir = new char[strlen(text) + 15];
		AllocatedSize = strlen(text) + 15;
	}
	strcpy(sir, text);
	Size = strlen(text);
}

void MyString::Set(MyString & m)
{
	Set(m.sir);
}

void MyString::Add(const char * text)
{
	char *aux;
	Size = Size + strlen(text);
	if (Size > AllocatedSize) {
		aux = new char[AllocatedSize];
		strcpy(aux, sir);
		delete[] sir;
		AllocatedSize = Size + 15;
		sir = new char[AllocatedSize];
		strcpy(sir, aux);
		delete[] aux;
	}
	strcat(sir, text);
}

void MyString::Add(MyString & m)
{
	Add(m.sir);
}

const char * MyString::GetText()
{
	if (sir) {
		return sir;
	}
	return nullptr;
}

MyString * MyString::SubString(unsigned int start, unsigned int size)
{
	int stop = start + size - 1;
	if (start > Size || stop > Size) {
		return nullptr;
	}
	MyString *r=new MyString;
	r->Size=size;
	r->AllocatedSize=size+15;
	r->sir=new char[r->AllocatedSize];
	for (int i=start;i<=stop;i++){
		r->sir[i-start]=sir[i];
	}
	r->sir[r->Size] = '\0';
	return r;
}

bool MyString::Delete(unsigned int start, unsigned int size)
{
	int stop = start + size - 1;
	if (start > Size || stop > Size) {
		return false;
	}
	for (int i = start; i < Size; i++) {
		sir[i] = sir[i + size];
	}
	Size -= size;
	return true;
}

int MyString::Compare(const char * text)
{
	if (strcmp(sir, text) == 0) {
		return 0;
	}
	if (strcmp(sir, text) < 0) {
		return -1;
	}
	return 1;
}

int MyString::Compare(MyString & m)
{
	return Compare(m.sir);
}

char MyString::GetChar(unsigned int index)
{
	if (index >= Size || index < 0) {
		return 0;
	}
	return sir[index];
}

bool MyString::Insert(unsigned int index, const char * text)
{
	int size = strlen(text);
	char *aux = new char[Size];
	strcpy(aux, sir + index);
	Delete(index, Size - index + 1);
	Add(text);
	Add(aux);
	delete[] aux;
	return true;
}

bool MyString::Insert(unsigned int index, MyString &m){
	bool ins=Insert(index,m.sir);
	if (ins){
		return true;
	}
	return false;
}

int MyString::Find(const char * text){
	char *p=strstr(sir,text);
	if (p){
		return p-sir;
	}
	return -1;
}

int MyString::FindLast(const char * text){
	char *p=strstr(sir,text);
	if (p==nullptr){
		return -1;
	}
	char *aux=p;
	while (p){
		aux=p;
		p=strstr(p,text);
	}
	return aux-sir;
}