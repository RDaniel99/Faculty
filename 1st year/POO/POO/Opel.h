#include "stdafx.h"
#include "MasinaOras.h"
#pragma once
class Opel:public MasinaOras
{
	int capacitate, anFabricatie;
	string culoare;
public:
	Opel();
	~Opel();
	void setCapacitate(int);
	void setCuloare(string);
	void setAnFabricatie(int);
	int getAnFabricatie();
	int getCapacitate();
	string getCuloare();
	string getName();
};

