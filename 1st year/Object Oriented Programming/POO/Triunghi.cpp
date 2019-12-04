#include "stdafx.h"
#include "Triunghi.h"

double dist(int x1, int y1, int x2, int y2) {
	double dx = x1 - x2;
	double dy = y1 - y2;
	return sqrt(dx*dx + dy * dy);
}

Triunghi::Triunghi(int x1, int y1, int x2, int y2, int x3, int y3) {
	X1 = x1;
	X2 = x2;
	X3 = x3;
	Y3 = y3;
	Y2 = y2;
	Y1 = y1;
}

double Triunghi::ComputeArea() {
	double a = dist(X1, Y1, X2, Y2);
	double b = dist(X1, Y1, X3, Y3);
	double c = dist(X2, Y2, X3, Y3);
	double p = (a + b + c) / 2;
	return sqrt(p*(p - a)*(p - b)*(p - c));
}

const char* Triunghi::GetName() {
	return "Triunghi";
}