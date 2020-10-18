#include <initializer_list>
#include <cstring>
#pragma once
class StrVector
{
public:
	int NumberOfElements;
	char **V;
	StrVector();
	~StrVector();
	char **GetIterator();
	int GetCount();
	char **begin();
	char **end();
	StrVector(std::initializer_list <const char*>);
};

