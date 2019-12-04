#include "stdafx.h"
#include "Opel.h"

Opel::Opel() {
	capacitate = 0;
	anFabricatie = 0;
	culoare = "";
}

Opel::~Opel() {
	delete this;
}

int Opel::getAnFabricatie(){
	return anFabricatie;
}

int Opel::getCapacitate() {
	return capacitate;
}

string Opel::getCuloare() {
	return culoare;
}

string Opel::getName() {
	return "Opel";
}

void Opel::setAnFabricatie(int an) {
	anFabricatie = an;
}

void Opel::setCapacitate(int c) {
	capacitate = c;
}

void Opel::setCuloare(string c){
	culoare = c;
}