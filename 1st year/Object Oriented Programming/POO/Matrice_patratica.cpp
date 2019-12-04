#include "stdafx.h"
#include "Matrice_patratica.h"
#include <cmath>
Matrice_patratica::Matrice_patratica(int x=0)
{
	dim=x;
	a=new Complex *[dim];
	for(int i=0;i<dim;i++)
		a[i]=new Complex [dim];
}
void Matrice_patratica::citire(istream& in)
{
	for(int i=0;i<dim;i++)
		for(int j=0;j<dim;j++)
			in>>a[i][j];
}
istream& operator>>(istream &in,Matrice_patratica &a)
{
	a.citire(in);
	return in;
}
ostream& operator<<(ostream&out,Matrice_patratica &a)
{
	for(int i=0;i<=a.dim;i++)
	{
		for(int j=0;j<=a.dim;j++)
			out<<a.a[i][j];
		out<<'\n';
	}
	return out;
}
Matrice_patratica& Matrice_patratica::operator=(const Matrice_patratica&b)
{
	dim=b.dim;
	int i,j;
	for(i=0;i<dim;i++)
		for(j=0;j<dim;j++)
			a[i][j]=b.a[i][j];
	return *this;
}
Matrice_patratica::~Matrice_patratica()
{
	for(int i=0;i<dim;i++)
		delete[] a[i];
//	delete a;
}
int Matrice_patratica:: verifica(Matrice &c,Matrice_patratica &b)
{
	int x=1;
	for(int i=0;i<=b.dim;i++)
	{
		for(int j=0;j<=x;j++)
			if(c.a[i][j].re!=b.a[i][j].re && c.a[i][j].im!=b.a[i][j].im)
				return 0;
		x++;
	}
	return 1;
}
Complex& Matrice_patratica::determ(const Matrice_patratica &a,int n)
{
  int p, h, k, i, j;
  Matrice_patratica temp(100);
  Complex *det=new Complex;
  Complex x,y;
  if(n==1) 
  {
    return a.a[0][0];
  } 
  else if(n==2) 
  {
    (*det)=(a.a[0][0]*a.a[1][1]-a.a[0][1]*a.a[1][0]);
    return *det;
  } 
  else 
  {
    for(p=0;p<n;p++) 
	{
		h = 0;
		k = 0;
		for(i=1;i<n;i++) 
		{
			for( j=0;j<n;j++) 
			{
			if(j==p) 
			{	  
				continue;
			}
			x=temp.a[h][k];
			y=a.a[i][j];
			//temp[h][k] = a.a[i][j];
			x=y;
			k++;
			if(k==n-1) 
			{
				h++;
				k = 0;
			}
			}
        }
	}
      (*det)=((*det+a.a[0][p])*pow(-1,p))*determ(temp,n-1);
  }
	return *det;
}