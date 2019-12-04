#pragma once
class nod {
public:
	int info;
	nod *urm;
	nod() {
		urm = NULL;
		info = 0;
	}
	nod(int x) {
		urm = NULL;
		info = x;
	}
};
class LSI
{
	nod *p, *u;
public:
	LSI();
	~LSI();
	void add(int);
	int operator[](int);
	friend class nod;
	friend ostream& operator<<(ostream&, LSI&);
};