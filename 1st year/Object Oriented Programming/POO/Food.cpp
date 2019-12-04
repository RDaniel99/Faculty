#include "stdafx.h"
#include "Food.h"


Food::Food() {
	quantity = 0;
}


Food::~Food() {}

void Food::setQuantity(float f) {
	quantity = f;
}

string Food::getInfo() {
	return to_string(quantity)+" kg";
}
