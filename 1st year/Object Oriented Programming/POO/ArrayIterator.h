#include "stdafx.h"
#pragma once
template<class T>
class ArrayIterator
{
private:
	int Current; // mai adaugati si alte date si functii necesare pentru iterator
public:
	ArrayIterator() {
		Current = 0;
	}
	ArrayIterator& operator ++ () {
		Current++;
		return (*this);
	}
	ArrayIterator& operator -- () {
		Curent--;
		return (*this);
	}
	bool operator= (ArrayIterator<T> &ai) {
		return (Current == ai.Current);
	}
	bool operator!=(ArrayIterator<T> &ai) {
		return !(ai == (*this));
	}
	T* GetElement() {

	}
};

