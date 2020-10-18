#include "stdafx.h"
#include "BigNumber.h"
#include <cstring>
#include <climits>
#include <algorithm>
#pragma warning(disable:4996)
using namespace std;

BigNumber::BigNumber()
{
	Number[0] = '0';
	for (int i = 1; i < 256; i++) {
		Number[i] = '\0';
	}
	CharactersCount = 1;
}

BigNumber::BigNumber(int value)
{
	CharactersCount = 0;
	while (value) {
		Number[CharactersCount] = value % 10 + '0';
		value /= 10;
		CharactersCount++;
	}
	for (int i = CharactersCount; i < 256; i++) {
		Number[i] = '\0';
	}
}

BigNumber::BigNumber(const char * number)
{
	if (strlen(number) > 255) {
		return;
	}
	strcpy(Number,number);
	CharactersCount = strlen(number);
	for (int i = CharactersCount; i < 256; i++) {
		Number[i] = '\0';
	}
	Number[CharactersCount] = '\0';
}

BigNumber::BigNumber(const BigNumber & number)
{
	strcpy(Number, number.Number);
	CharactersCount = number.CharactersCount;
	for (int i = CharactersCount; i < 256; i++) {
		Number[i] = '\0';
	}
}

bool BigNumber::Set(int value)
{
	BigNumber n(value);
	strcpy(Number, n.Number);
	CharactersCount = n.CharactersCount;
	return true;
}

bool BigNumber::Set(const char * number)
{
	BigNumber n(number);
	strcpy(Number, n.Number);
	CharactersCount = n.CharactersCount;
	return true;
}

int max(int a, int b) {
	return a < b ? b : a;
}

BigNumber BigNumber::operator+(const BigNumber & number)
{
	BigNumber n(number);
	BigNumber suma;
	int t = 0;
	for (int i = 0; i < CharactersCount; i++) {
		Number[i] -= '0';
	}
	for (int i = 0; i < n.CharactersCount; i++) {
		n.Number[i] -= '0';
	}
	suma.CharactersCount = max(n.CharactersCount, CharactersCount);
	for (int i = 0; i<suma.CharactersCount; ++i)
	{
		suma.Number[i] = n.Number[i] + Number[i] + t;
		t = suma.Number[i] / 10;        
		suma.Number[i] = suma.Number[i] % 10;  
	}
	if (t)
		suma.Number[suma.CharactersCount++] = t;
	for (int i = 0; i < suma.CharactersCount; i++) {
		suma.Number[i] += '0';
	}
	for (int i = 0; i < CharactersCount; i++) {
		Number[i] += '0';
	}
	for (int i = 0; i < n.CharactersCount; i++) {
		n.Number[i] += '0';
	}
	return BigNumber(suma);
}

BigNumber BigNumber::operator+(int number)
{
	(*this) = (*this) + BigNumber(number);
	return BigNumber(*this);
}

BigNumber BigNumber::operator*(const BigNumber & number)
{
	int i = 0, j = 0, t = 0;

	BigNumber rez,n(number);
	for (int i = 0; i < CharactersCount; i++) {
		Number[i] -= '0';
	}
	for (int i = 0; i < n.CharactersCount; i++) {
		n.Number[i] -= '0';
	}
	for (i = 0; i<256; ++i)
		rez.Number[i] = 0;

	for (int i = 0; i < n.CharactersCount; i++) {

	}
	for (int i = 0; i < rez.CharactersCount; i++) {
		rez.Number[i] += '0';
	}
	for (int i = 0; i < CharactersCount; i++) {
		Number[i] += '0';
	}
	for (int i = 0; i < n.CharactersCount; i++) {
		n.Number[i] += '0';
	}
	return BigNumber(rez);
}

BigNumber BigNumber::operator*(int number)
{
	(*this) = (*this) * BigNumber(number);
	return BigNumber(*this);
}

BigNumber BigNumber::operator-(const BigNumber & number)
{
	int i, t = 0;
	BigNumber dif,n(number);
	dif.CharactersCount = CharactersCount;

	for (int i = 0; i < CharactersCount; i++) {
		Number[i] -= '0';
	}
	for (int i = 0; i < number.CharactersCount; i++) {
		n.Number[i] -= '0';
	}

	for (i = 0; i<dif.CharactersCount; ++i)
	{
		dif.Number[i] = Number[i] - n.Number[i] + t;
		if (dif.Number[i]<0)
		{
			dif.Number[i] = dif.Number[i] + 10;
			t = -1;
		}
		else
			t = 0;
	}

	i = dif.CharactersCount - 1;
	while (dif.Number[i] == 0)
		i--;

	dif.CharactersCount = i + 1;
	for (int i = 0; i < dif.CharactersCount; i++) {
		dif.Number[i] += '0';
	}
	for (int i = 0; i < CharactersCount; i++) {
		Number[i] += '0';
	}
	for (int i = 0; i < number.CharactersCount; i++) {
		n.Number[i] += '0';
	}
	return BigNumber(dif);
}

BigNumber BigNumber::operator-(int number)
{
	(*this) = (*this) - BigNumber(number);
	return BigNumber(*this);
}

BigNumber BigNumber::operator/(const BigNumber & number)
{
	BigNumber n(number),cat(0);
	while ((*this) > BigNumber(number)) {
		(*this) = (*this) - number;
		cat = cat + 1;
	}
	return BigNumber(cat);
}

BigNumber BigNumber::operator/(int number)
{
	(*this) = (*this) / BigNumber(number);
	return BigNumber(*this);
}

bool operator==(const BigNumber & n1, const BigNumber & n2)
{
	if (n1.CharactersCount != n2.CharactersCount) {
		return false;
	}
	for (int i = 0; i < n1.CharactersCount; i++) {
		if (n1.Number[i] != n2.Number[i]) {
			return false;
		}
	}
	return true;
}

bool operator!=(const BigNumber & n1, const BigNumber & n2)
{
	if (n1 == n2) {
		return false;
	}
	return true;
}

bool operator<(const BigNumber & n1, const BigNumber & n2)
{
	if (n1.CharactersCount < n2.CharactersCount) {
		return true;
	}
	if (n1.CharactersCount > n2.CharactersCount) {
		return false;
	}
	for (int i = 0; i < n1.CharactersCount; i++) {
		if (n1.Number[i] > n2.Number[i]) {
			return false;
		}
		if (n1.Number[i] < n2.Number[i]) {
			return true;
		}
	}
	return false;
}

bool operator>(const BigNumber & n1, const BigNumber & n2)
{
	if (n1.CharactersCount < n2.CharactersCount) {
		return false;
	}
	if (n1.CharactersCount > n2.CharactersCount) {
		return true;
	}
	for (int i = 0; i < n1.CharactersCount; i++) {
		if (n1.Number[i] > n2.Number[i]) {
			return true;
		}
		if (n1.Number[i] < n2.Number[i]) {
			return false;
		}
	}
	return false;
}

bool operator>=(const BigNumber & n1, const BigNumber & n2)
{
	if (!(n1 < n2)) {
		return true;
	}
	return false;
}

bool operator<=(const BigNumber & n1, const BigNumber & n2)
{
	if (!(n1 > n2)) {
		return true;
	}
	return false;
}

std::ostream & operator<<(std::ostream & os, const BigNumber & n)
{
	for (int i = n.CharactersCount - 1; i >= 0; i--) {
		os << n.Number[i];
	}
	os << '\n';
	return os;
}

BigNumber::operator int()
{
	if (CharactersCount > 9) {
		return INT_MIN;
	}
	int x = 0;
	for (int i = 0; i < CharactersCount; i++) {
		x = x * 10 + Number[i] - '0';
	}
	return x;
}

char BigNumber::operator[](int index)
{
	if (index < CharactersCount&&index >= 0) {
		return Number[index];
	}
	return 0;
}

BigNumber BigNumber::operator()(int start, int end)
{
	char s[256];
	for (int i = start, j = 0; i <= end; i++, j++) {
		s[j] = Number[i];
	}
	return BigNumber(s);
}
