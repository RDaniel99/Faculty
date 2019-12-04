#include "stdafx.h"
#include "Complex.h"
Complex::Complex()
{
	re=0;
	im=0;
}
Complex::Complex(float x,float y=0)
{
	re=x;
	im=y;
}
Complex::Complex(const Complex &a)
{
	re=a.re;
	im=a.im;
}
Complex& Complex::operator= (const Complex &a)
{
	re=a.re;
	im=a.im;
	return *this;
}
const Complex& operator+(const Complex& a,const Complex& b)
{
	Complex *c=new Complex;
	(*c).re=a.re+b.re;
	(*c).im=a.im+b.im;
	return *c;
}
Complex& operator-(const Complex &a,const Complex &b)
{
	Complex *c=new Complex;
	(*c).re=a.re-b.re;
	(*c).im=a.im-b.im;
	return *c;
}
Complex& operator*(const Complex &a,const Complex &b)
{
	Complex *c=new Complex;
	(*c).re=a.re*b.re-a.im*b.im;
	(*c).im=a.re*b.im+b.re*a.im;
	return *c;
}
istream& operator>>(istream& in,Complex& x)
{
	in>>x.re>>x.im;
	return in;
}
ostream& operator<<(ostream &out,Complex& x)
{
	out<<x.re<<" "<<x.im<<"i"<<endl;
	return out;
}
Complex::~Complex()
{
	cout<<endl<<"Numar complex sters"<<endl;
}

Complex& operator*(const Complex& a,float x)
{
	Complex *b=new Complex;
	b->re=a.re*x;
	b->im=a.im*x;
	return (*b);
}