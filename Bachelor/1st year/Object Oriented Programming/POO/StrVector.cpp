#include "stdafx.h"
#include "StrVector.h"
#pragma warning(disable:4996)

StrVector::StrVector()
{
	NumberOfElements = 0;
	V = new char*;
}

StrVector::~StrVector()
{
	NumberOfElements = 0;
	delete V;
}

StrVector::StrVector(std::initializer_list <const char*> IL) {
	NumberOfElements = IL.size();
	V = new char*[NumberOfElements + 10];
	std::initializer_list <const char*>::iterator it;
	int index = 0;
	for (it = IL.begin(); it != IL.end(); it++, index++) {
		V[index] = new char[strlen(*it) + 5];
		strcpy(V[index], *it);
	}
}

int StrVector::GetCount() {
	return NumberOfElements;
}

char** StrVector::GetIterator() {
	return V;
}

char** StrVector::begin() {
	return &V[0];
}

char** StrVector::end() {
	return &V[NumberOfElements - 1] + 1;
}
