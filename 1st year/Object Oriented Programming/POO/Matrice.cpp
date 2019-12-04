#include "stdafx.h"
#include "Matrice.h"
Matrice::Matrice()
{
	a=NULL;
}
void Matrice::citire(istream &in)
{
	
}
Complex Matrice::ia_element(int i,int j)
{
	return a[i][j];
}
istream& operator>>(istream &in,Matrice &a)
{
	return in;
}
ostream& operator<<(ostream &out,Matrice &a)
{
	return out;
}
void Matrice::afisare(const Matrice &a,int l=0,int c=0)
{
	for(int i=0;i<l;i++)
		for(int j=0;j<c;j++)
			cout<<a.a[i][j];
}
Matrice::~Matrice()
{
	if (a)
		delete a;
	cout<<endl<<"Matrice distrusa"<<endl;
}

Matrice::Matrice(int x,int y)
{
a=new Complex*[x];
for(int i=0;i<x;i++)
	a[i]=new Complex[y];
}
