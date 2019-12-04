#include <iostream>
using namespace std;
class Complex
{
	friend class Matrice;
	friend class Matrice_patratica;
	friend class Matrice_oarecare;
	float re;
	float im;
public:
	Complex();
	Complex(float,float);
	Complex(const Complex&);
	Complex& operator= (const Complex &);
	friend istream& operator>>(istream&,Complex&);
	friend ostream& operator<<(ostream&,Complex&);
	friend const Complex& operator+(const Complex&,const Complex&);
	friend Complex& operator-(const Complex&,const Complex&);
	friend Complex& operator*(const Complex&,const Complex&);
	friend Complex& operator*(const Complex&,float);
	~Complex();
};