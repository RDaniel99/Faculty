#include "stdafx.h"
#include "Item.h"


Item::Item(){
	name = "";
}


Item::~Item(){}

string Item::getName() {
	return name;
}

void Item::setName(string nume) {
	name = nume;
}