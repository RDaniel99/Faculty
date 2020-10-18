#pragma once
#include "Item.h"
class Miscellaneous: public Item
{
	int count;
public:
	Miscellaneous();
	~Miscellaneous();
	string getInfo();
	void setCount(int c);
};

