#include <iostream>
#include "Complex.h"
using namespace std;
class Matrice
{
protected:
	Complex **a;
public:
	Matrice();
	Matrice(int,int);
	friend istream& operator>>(istream&,Matrice&);
	friend ostream& operator<<(ostream&,Matrice&);
	void afisare(const Matrice&,int,int);
	virtual void citire(const Matrice&,int,int){};
	Complex ia_element(int,int);
	~Matrice();
	virtual void citire(istream&);
	friend class Matrice_patratica;
};