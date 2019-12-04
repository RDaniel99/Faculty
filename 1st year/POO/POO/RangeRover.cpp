#include "stdafx.h"
#include "RangeRover.h"


RangeRover::RangeRover()
{
	consum = 0;
}


RangeRover::~RangeRover()
{
	delete this;
}

void RangeRover::setConsum(int c) {
	consum = c;
}

int RangeRover::getConsum() {
	return consum;
}

string RangeRover::getName() {
	return "Range Rover";
}
