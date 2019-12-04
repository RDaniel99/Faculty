#include "stdafx.h"
#include "Exceptions.h"
const char* Exceptions::what() const throw() {
	return "Index out of range!!!";
}