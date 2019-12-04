#include "stdafx.h"
#include "Miscellaneous.h"


Miscellaneous::Miscellaneous() {
	count = 0;
}


Miscellaneous::~Miscellaneous() {}

string Miscellaneous::getInfo() {
	return to_string(count)+" items";
}

void Miscellaneous::setCount(int c) {
	count = c;
}
