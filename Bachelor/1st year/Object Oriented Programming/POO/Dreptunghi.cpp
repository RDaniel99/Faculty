#include "stdafx.h"
#include "Dreptunghi.h"


Dreptunghi::Dreptunghi(int lungime, int latime) {
	Latime = latime;
	Lungime = lungime;
}

double Dreptunghi::ComputeArea() {
	return Lungime * Latime;
}

const char* Dreptunghi::GetName() {
	return "Dreptunghi";
}