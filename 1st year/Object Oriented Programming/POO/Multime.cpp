#include "stdafx.h"
#include "Multime.h"

int min(int a, int b) {
	if (a < b) {
		return a;
	}
	return b;
}
int max(int, int);


Multime::Multime(int * lista, int size)
{
	for (int i = 0;i < size; i++){
		for (int j= i + 1;j < size; j++){
			if (lista[i] > lista[j]){
				std::swap(lista[i], lista[j]);
			}
		}
	}
	int j = 0;
	for (int i = 0; i < 100; i++) {
		Count++;
		Numere[i] = lista[j];
		while (lista[j] == lista[j + 1] && j < size){
			j++;
		}
		if (j == size){
			break;
		}
	}
}

Multime::Multime()
{
	Count = 0;
}

Multime::~Multime()
{
	Count = 0;
}

int Multime::Max() {
	int maxim = Numere[0];
	for (int i = 0; i < Count; i++) {
		maxim = max(maxim, Numere[i]);
	}
	return maxim;
}

int Multime::Min() {
	int minim = Numere[0];
	for (int i = 0; i < Count; i++) {
		minim = min(minim, Numere[i]);
	}
	return minim;
}

void Multime::Sort(int(*compare)(int, int))
{
	for (int i = 0; i < Count; i++) {
		for (int j = i + 1; j < Count; j++) {
			if (compare(Numere[i], Numere[j]) == 0) {
				int aux = Numere[i];
				Numere[i] = Numere[j];
				Numere[j] = Numere[i];
			}
		}
	}
}

int Multime::GetSize() {
	return Count;
}

int * Multime::operator[](int index)
{
	if (index > Count) {
		return nullptr;
	}
	return &(Numere[index]);
}

Multime::operator double()
{
	double s = 0;
	for (int i = 0; i < Count; i++) {
		s += Numere[i];
	}
	return s;
}

Multime Multime::operator|(const Multime & m)
{
	Multime ans;
	for (int i = 0; i < Count; i++) {
		for (int j = 0; j < m.Count; i++) {
			if (Numere[i] == m.Numere[j] && ans.GetSize() < 100) {
				ans.Numere[ans.Count++] = Numere[i];
			}
		}
	}
	return ans;
}

int crescator(int a, int b) {
	return a < b;
}

Multime Multime::operator&(const Multime & m)
{
	Multime ans;
	Multime M = m;
	M.Sort(crescator);
	Sort(crescator);
	int i = 0, j = 0;
	while (i < Count&&j < m.Count && ans.Count<100) {
		
		if (Numere[i] < M.Numere[j]) {
			ans.Numere[ans.Count++] = Numere[i];
			i++;
			continue;
		}
		if (Numere[i] > M.Numere[j]) {
			ans.Numere[ans.Count++] = M.Numere[j];
			j++;
			continue;
		}
		ans.Numere[ans.Count++] = Numere[i];
		i++;
		j++;
	}
	while (i < Count&&ans.Count < 100) {
		ans.Numere[ans.Count++] = Numere[i];
		i++;
	}
	while (j < M.Count&&ans.Count < 100) {
		ans.Numere[ans.Count++] = M.Numere[j];
		j++;
	}
	return ans;
}