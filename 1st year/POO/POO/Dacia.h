#include "stdafx.h"
#include "MasinaOras.h"
#pragma once
class Dacia:public MasinaOras
{
	int capacitate;
	string culoare;
public:
	Dacia();
	~Dacia();
	void setCapacitate(int);
	void setCuloare(string);
	int getCapacitate();
	string getCuloare();
	string getName();
};

