#include "stdafx.h"
#include "SUV.h"
#pragma once
class RangeRover:public SUV
{
	int consum;
public:
	RangeRover();
	~RangeRover();
	void setConsum(int);
	int getConsum();
	string getName();
};

