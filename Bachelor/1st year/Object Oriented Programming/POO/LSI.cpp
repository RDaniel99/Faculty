#include "stdafx.h"
#include "LSI.h"


LSI::LSI() {
	p = new nod;
	u = p;
}

ostream& operator<<(ostream& out, LSI& l) {
	nod *p = l.p;
	while (p != NULL) {
		out << p->info << ' ';
		p = p->urm;
	}
	return out;
}

LSI::~LSI() {
	delete p;
	delete u;
}

void LSI::add(int x) {
	nod *n = new nod(x);
	u->urm = n;
	u = n;
}

int LSI::operator[](int pos) {
	int i = 1;
	nod *aux = p;
	while (i < pos&&aux != NULL) {
		aux = aux->urm;
	}
	if (aux == NULL) {
		return 0;
	}
	return aux->info;
}
