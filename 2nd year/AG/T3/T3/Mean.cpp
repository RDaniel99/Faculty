#include <fstream>
#include <iostream>
#include <algorithm>

using namespace std;

ifstream res("results.txt");

int main() {
	int result[35], s = 0;
	for (int i = 1; i <= 30; i++) {
		res >> result[i];
		s += result[i];
	}
	double m = 1.0*s / 30;
	double sum = 0;
	for (int i = 1; i <= 30; i++) {
		sum += (1.0*result[i] - m)*(1.0*result[i] - m);
	}
	sum /= 29;
	sum = sqrt(sum);
	cout << "Deviatia standard: " << sum << '\n';
	int minim = result[1], maxim = result[1];
	for (int i = 2; i <= 30; i++) {
		minim = min(minim, result[i]);
		maxim = max(maxim, result[i]);
	}
	cout << fixed << "Minim: " << minim << '\n' << "Medie: " << m << '\n' << "Maxim: " << maxim << '\n';
	system("pause");
}