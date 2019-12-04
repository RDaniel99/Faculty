#include "stdafx.h"
#include "Masina.h"
#pragma once
class MasinaOras:public Masina
{
public:
	virtual int getCapacitate() = 0;
	virtual string getCuloare() = 0;
};

