#include <iostream>
#include "Matrice_oarecare.h"
using namespace std;
class Matrice_patratica:public Matrice
{
	int dim;
public:
	
	Matrice_patratica(int);
	~Matrice_patratica();
	void citire(istream&);
	Matrice_patratica& operator= (const Matrice_patratica &);
	Complex& determ(const Matrice_patratica &a,int n);
	friend istream& operator>>(istream&,Matrice_patratica&);
	friend ostream& operator<<(ostream&,Matrice_patratica&);
	int verifica(Matrice&,Matrice_patratica&);
	
};