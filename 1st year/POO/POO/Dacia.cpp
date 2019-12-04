#include "stdafx.h"
#include "Dacia.h"


Dacia::Dacia()
{
	capacitate = 0;
	culoare = "";
}

Dacia::~Dacia()
{
	delete this;
}

void Dacia::setCapacitate(int c) {
	capacitate = c;
}

void Dacia::setCuloare(string c) {
	culoare = c;
}

int Dacia::getCapacitate() {
	return capacitate;
}

string Dacia::getCuloare() {
	return culoare;
}

string Dacia::getName() {
	return "Dacia";
}