#include "stdafx.h"
#include "Cerc.h"


Cerc::Cerc(int raza) {
	Raza = raza;
}

double Cerc::ComputeArea() {
	return 3.1415926535897932384626433*Raza*Raza;
}

const char* Cerc::GetName() {
	return "Cerc";
}