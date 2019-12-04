#include "stdafx.h"
#pragma once
class Exceptions :public std::exception
{
	virtual const char* what()const throw();
};
