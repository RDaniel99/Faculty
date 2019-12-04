#include <iostream>
#include "Matrice.h"
using namespace std;
class Matrice_oarecare:public Matrice
{
	int lin;
	int col;
public:
	Matrice_oarecare(int, int);
	Matrice_oarecare(const Matrice_oarecare&);
	~Matrice_oarecare();
	void citire(istream &in);
	Matrice_oarecare& operator= (const Matrice_oarecare &);
	friend istream& operator>>(istream&,Matrice_oarecare&);
	friend ostream& operator<<(ostream&,Matrice_oarecare&);
	
};