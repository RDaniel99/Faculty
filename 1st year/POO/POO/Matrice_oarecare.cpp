#include "stdafx.h"
#include "Matrice_oarecare.h"
Matrice_oarecare::Matrice_oarecare(const Matrice_oarecare &b)
{
	int i,j;
	lin=b.lin;
	col=b.col;
	for(i=0;i<lin;i++)
		for(j=0;j<col;j++)
			a[i][j]=b.a[i][j];
}
void Matrice_oarecare::citire(istream &in)
{
	for(int i=0;i<lin;i++)
		for(int j=0;j<col;j++)
			in>>a[i][j];
}
istream& operator>>(istream &in,Matrice_oarecare &a)
{
	a.citire(in);
	return in;
}
ostream& operator<<(ostream &out,Matrice_oarecare &a)
{
	for(int i=0;i<=a.lin;i++)
	{
		for(int j=0;j<=a.col;j++)
			out<<a.a[i][j];
		out<<'\n';
	}
	return out;
}
Matrice_oarecare& Matrice_oarecare::operator= (const Matrice_oarecare &b)
{
	lin=b.lin;
	col=b.col;
	int i,j;
	for(i=0;i<lin;i++)
		for(j=0;j<col;j++)
			a[i][j]=b.a[i][j];
	return *this;
	
}
Matrice_oarecare::Matrice_oarecare(int x,int y)
{
	lin=x;
	col=y;
	a=new Complex*[lin];
	for(int i=0;i<lin;i++)
		a[i]=new Complex [col];
}
Matrice_oarecare::~Matrice_oarecare()
{
	for(int i=0;i<lin;i++)
		delete[] a[i];
//	delete a;
}
