#include "stdafx.h"
#include "ArrayIterator.h"
#include "Compare.h"
#include "Exceptions.h"

#pragma once
template<class T>
class Array
{
private:
	T * * List; // lista cu pointeri la obiecte de tipul T*
	int Capacity; // dimensiunea listei de pointeri
	int Size; // cate elemente sunt in lista
public:
	Array() {
		// Lista nu e alocata, Capacity si Size = 0
		List = new T*;
		Capacity = 1;
		List[0] = new T;
		Size = 0;
	}
	~Array() { 
		// destructor
		delete[]List;
		Capacity = 0;
		Size = 0;
	}
	Array(int capacity) { 
		// Lista e alocata cu 'capacity' elemente
		Size = 0;
		Capacity = capacity;
		List = new T*[Capacity];
		for (int i = 0; i < Capacity; i++) {
			List[i] = new T;
		}
	}
	Array(const Array<T> &otherArray) { 
		// constructor de copiere
		Capacity = otherArray.Capacity;
		Size = otherArray.Size;
		List = new T*[Capacity];
		for (int i = 0; i < Size; i++) {
			List[i] = otherArray.List[i];
		}
	}
	T& operator[] (int index) {
		// arunca exceptie daca index este out of range
		Exceptions E;
		try {
			if (index<0 || index>=Size) {
				throw E;
			}
		}
		catch (std::exception &e){
			cout << "Exceptie: " << e.what();
		}
		return *List[index];
	}
	const Array<T>& operator+=(const T &newElem) { 
		// adauga un element de tipul T la sfarsitul listei si returneaza this
		Array <T> A(*this);
		A.Capacity = Capacity;
		A.Size = Size; 
		if (Capacity == Size) {
			A.Capacity += 10;
			A.List = new T*[A.Capacity];
		}
		for (int i = 0; i < A.Capacity; i++) {
			A.List[i] = new T;
			if (i < Capacity) {
				A.List[i] = List[i];
			}
		}
		List = new T*[A.Capacity];
		for (int i = 0; i < A.Capacity; i++) {
			List[i] = new T;
			List[i] = A.List[i];
		}
		Size++;
		Capacity = A.Capacity;
		List[Size - 1] = (T*)(&newElem);
		return (*this);
	}
	const Array<T>& Insert(int index, const T &newElem) { 
		// adauga un element pe pozitia index, retureaza this. Daca index e invalid arunca o exceptie
		Exceptions E;
		try {
			if (index<0 || index>Size) {
				throw E;
			}
		}
		catch (std::exception &e) {
			cout << "Exceptie: " << e.what();
		}
		(*this) += newElem;
		for (int i = Size - 2; i >= index; i--) {
			List[i + 1] = List[i];
		}
		List[index] = (T*)(&newElem);
		return (*this);
	}
	const Array<T>& Insert(int index, const Array<T> otherArray) { 
		// adauga o lista pe pozitia index, retureaza this. Daca index e invalid arunca o exceptie
		Exceptions E;
		try {
			if (index<0 || index>Size) {
				throw E;
			}
		}
		catch (std::exception &e) {
			cout << "Exceptie: " << e.what();
		}

		for (int i = 0; i < otherArray.Size; i++) {
			(*this).Insert(i + index, *otherArray.List[i]);
		}
		return (*this);
	}
	const Array<T>& Delete(int index) {
		// sterge un element de pe pozitia index, returneaza this. Daca index e invalid arunca o exceptie
		Exceptions E;
		try {
			if (index<0 || index>Size) {
				throw E;
			}
		}
		catch (std::exception &e) {
			cout << "Exceptie: " << e.what();
		}
		for (int i = index; i < Size; i++) {
			List[i] = List[i + 1];
		}
		delete List[Size - 1];
		Size--;
		return (*this);
	}
	bool operator=(const Array<T> &otherArray) {
		if (Size != otherArray.Size) {
			return false;
		}
		for (int i = 0; i < Size; i++) {
			if (List[i] != otherArray.List[i]) {
				return false;
			}
		}
		return true;
	}
	void Sort() {
		// sorteaza folosind comparatia intre elementele din T
		sort(List, List + Size);
	}
	void Sort(int(*compare)(const T&, const T&)) {
		// sorteaza folosind o functie de comparatie
		sort(List, List + Size, compare);
	}
	void Sort(Compare *comparator) {
		// sorteaza folosind un obiect de comparatie
		sort(List, List + Size, comparator->CompareElements);
	}
									// functii de cautare - returneaza pozitia elementului sau -1 daca nu exista
	int BinarySearch(const T& elem) {
		// cauta un element folosind binary search in Array
		(*this).Sort();
		int st = 0, dr = Size - 1;
		while (st <= dr) {
			int mij = (st + dr) / 2;
			if (List[mij] == elem) {
				return mij;
			}
			if (List[mij] < elem) {
				st = mij + 1;
			}
			else {
				dr = mij - 1;
			}
		}
		return -1;
	}
	int BinarySearch(const T& elem, int(*compare)(const T&, const T&)) {
		//  cauta un element folosind binary search si o functie de comparatie
		(*this).Sort(compare);
		int st = 0, dr = Size - 1;
		while (st <= dr) {
			int mij = (st + dr) / 2;
			if (compare(List[mij], elem) == 0) {
				return mij;
			}
			if (compare(List[mij], elem) < 0) {
				st = mij + 1;
			}
			else {
				dr = mij - 1;
			}
		}
		return -1;
	}
	int BinarySearch(const T& elem, Compare *comparator) {
		//  cauta un element folosind binary search si un comparator
		return BinarySearch(elem, comparator->CompareElements);
	}
	int Find(const T& elem) {
		// cauta un element in Array
		return BinarySearch(elem);
	}
	int Find(const T& elem, int(*compare)(const T&, const T&)) {
		//  cauta un element folosind o functie de comparatie
		return BinarySearch(elem, compare);
	}
	int Find(const T& elem, Compare *comparator) {
		//  cauta un element folosind un comparator
		return BinarySearch(elem, comparator);
	}
	int GetSize() {
		return Size;
	}
	int GetCapacity() {
		return Capacity;
	}
	ArrayIterator<T> GetBeginIterator() {
		return List[0];
	}
	ArrayIterator<T> GetEndIterator() {
		return List[Size - 1] + 1;
	}
};

