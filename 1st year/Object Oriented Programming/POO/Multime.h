#pragma once
class Multime
{
	int Numere[100];
	int Count;
public:
	Multime(int *lista, int size);
	Multime();
	~Multime();
	int Max();
	int Min();
	void Sort(int(*compare)(int, int));
	int GetSize();
	int* operator[](int);
	operator double();
	Multime operator&(const Multime &m);
	Multime operator|(const Multime &m);
};

