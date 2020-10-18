#include <algorithm>
#include <string>
#include <iostream>
#include <ctime>
#include <fstream>
#include <vector>
#define NMAX 10000
#define POP_SIZE 50
#define MUTATION_PROB 0.3
#define CROSSOVER_PROB 0.9
#define NR_GENERATIONS 1000

using namespace std;

ofstream georgiana("1.out");
vector <vector <int> > population;
vector <int> bestPathSoFar;
vector <double> fitness, values;
int nodeCount, node[NMAX], x[NMAX], y[NMAX];
double adjacencyMatrix[NMAX][NMAX], bestSoFar = INT_MAX;
void read();
void eval();
double f(vector <int>);
void euristica();
void initializare();
void genetic();
void crossover();
void crossover(int, int);
void mutation();
void select();
int main() {
	srand(time(NULL));
	read();
	genetic();
	//euristica();
	return 0;
}

void euristica() {
	cout << "Euristica: ";
	vector <int> order, bestRoad;
	int viz[NMAX];
	double best = INT_MAX;
	for (int i = 1; i <= nodeCount; i++) {
		order.resize(0);
		order.push_back(i);
		memset(viz, 0, sizeof(viz));
		viz[i] = 1;
		int node = 1;
		while (order.size() < nodeCount) {
			int ok = 0, chosen = 0;
			double minim = 0;
			for (int j = 1; j <= nodeCount; j++) {
				if (!viz[j] && (ok == 0 || adjacencyMatrix[node][j] < minim)) {
					minim = adjacencyMatrix[node][j], chosen = j, ok = 1;
				}
			}
			order.push_back(chosen);
			viz[chosen] = 1;
			node = chosen;
		}
		double cost = 0;
		for (int i = 0; i < order.size() - 1; i++) {
			cost += adjacencyMatrix[order[i]][order[i + 1]];
		}
		cost += adjacencyMatrix[order[order.size() - 1]][order[0]];
		if (cost < best) {
			best = cost;
			bestRoad = order;
		}
	}
	cout << "Road: ";
	for (auto x : bestRoad) {
		cout << x << ' ';
	}
	cout << '\n'<<"Cost: "<<best<< '\n';
}

void read() {
	ifstream sabina("date.in");
	sabina >> nodeCount;
	for (int i = 1; i <= nodeCount; i++) {
		sabina >> node[i] >> x[i] >> y[i];
	}
	for (int i = 1; i <= nodeCount; i++) {
		for (int j = i + 1; j <= nodeCount; j++) {
			int dx = x[i] - x[j];
			int dy = y[i] - y[j];
			adjacencyMatrix[node[i]][node[j]] = adjacencyMatrix[node[j]][node[i]] = sqrt(dx * dx + dy * dy);
		}
	}
}

void genetic() {
	initializare();
	eval();
	for (int gen = 1; gen <= NR_GENERATIONS; gen++) {
		cout << "Generatia " << gen << '\n';
		georgiana << "Generatia " << gen << '\n';
		vector <int> bestThis;
		double best = INT_MAX;
		for (int i = 0; i < POP_SIZE; i++) {
			if (values[i] < best) {
				bestThis = population[i];
				best = values[i];
			}
		}
		georgiana << "Best in generation " << gen << ": " << best << '\n';
		for (int i = 0; i < nodeCount; i++) {
			georgiana << bestThis[i] << ' ';
		}
		georgiana << '\n';
		georgiana << "Best so far: " << bestSoFar << '\n';
		for (int i = 0; i < bestPathSoFar.size(); i++) {
			georgiana << bestPathSoFar[i] << ' ';
		}
		georgiana << bestPathSoFar[0] << '\n';
		select();
		crossover();
		mutation();
		eval();
	}
}

void initializare() {
	int viz[NMAX];
	bestSoFar = INT_MAX;
	vector <int> chromosome;
	population.resize(0);
	for (int i = 0; i < POP_SIZE; i++) {
		chromosome.resize(0);
		memset(viz, 0, sizeof(viz));
		int left = nodeCount;
		for (int i = 1; i <= nodeCount; i++) {
			int p = rand() % left + 1;
			for (int j = 0, k = 1; j <= p; k++) {
				if (!viz[k]) j++;
				if (j == p) {
					chromosome.push_back(k);
					viz[k] = 1;
					break;
				}
			}
			left--;
		}
		population.push_back(chromosome);
	}
}

void eval() {
	fitness.resize(0);
	values.resize(0);
	for (int i = 0; i < POP_SIZE; i++) {
		fitness.push_back(1.0/f(population[i]));
		values.push_back(f(population[i]));
		if (f(population[i]) < bestSoFar) {
			bestSoFar = f(population[i]);
			bestPathSoFar = population[i];
		}
	}
}

double f(vector <int> chromosome) {
	double s = 0;
	for (int i = 0; i < nodeCount - 1; i++) {
		s += adjacencyMatrix[chromosome[i]][chromosome[i + 1]];
	}
	s += adjacencyMatrix[chromosome[nodeCount - 1]][chromosome[0]];
	return s;
}

void select() {
	double s = 0;
	for (int i = 0; i < POP_SIZE; i++) {
		s += fitness[i];
	}
	vector <double> wheel;
	vector <vector <int> > newpop;
	wheel.push_back(0);
	for (int i = 0; i < POP_SIZE - 1; i++) {
		wheel.push_back(1.0 * fitness[i] / s);
		if (i != 0) wheel[wheel.size() - 1] += wheel[wheel.size() - 2];
	}
	wheel.push_back(1);
	for (int i = 0; i < POP_SIZE; i++) {
		double r = 1.0*rand() / RAND_MAX;
		int survival;
		for (survival = 0; !(wheel[survival] <= r && r <= wheel[survival + 1]); survival++);
		newpop.push_back(population[survival]);
	}
	population = newpop;
}

void crossover() {
	vector <int> forCross;
	for (int i = 0; i < POP_SIZE; i++) {
		double p1 = 1.0*rand() / RAND_MAX;
		if (p1 < CROSSOVER_PROB) {
			forCross.push_back(i);
		}
	}
	if (forCross.size() % 2 == 1) forCross.pop_back();
	while (forCross.size()) {
		int j = rand() % forCross.size();
		while (j == 0) j = rand() % forCross.size();
		crossover(forCross[0], forCross[j]);
		forCross.erase(forCross.begin() + j);
		forCross.erase(forCross.begin());
	}
}

void crossover(int i, int j){
	vector <int> newpopI(nodeCount), newpopJ(nodeCount);
	vector <int> vizI(nodeCount + 1), vizJ(nodeCount + 1);
	int i1 = rand() % newpopI.size();
	int i2 = rand() % newpopI.size();
	if (i1 > i2) swap(i1, i2);
	for (int k = i1; k <= i2; k++) {
		newpopI[k] = population[j][k]; vizI[population[j][k]] = 1;
		newpopJ[k] = population[i][k]; vizJ[population[i][k]] = 1;
	}
	int it = 0;
	for (int k = 0; k < nodeCount; k++) {
		if (it == i1) it = i2 + 1;
		if (!vizI[population[i][k]]) newpopI[it++] = population[i][k];
	}
	it = 0;
	for (int k = 0; k < nodeCount; k++) {
		if (it == i1) it = i2 + 1;
		if (!vizJ[population[j][k]]) newpopJ[it++] = population[j][k];
	}
	population[i] = newpopI;
	population[j] = newpopJ;

}

void mutation() {
	for (int i = 0; i < POP_SIZE; i++) {
		vector <int> genes;
		for (int j = 0; j < nodeCount; j++) {
			double p = 1.0 * rand() / RAND_MAX;
			if (p < MUTATION_PROB) {
				genes.push_back(j);
			}
		}
		if (genes.size() % 2 == 1) genes.pop_back();
		while (genes.size()) {
			int b = rand() % genes.size();
			while (b == 0) b = rand() % genes.size();
			swap(population[i][genes[0]], population[i][genes[b]]);
			genes.erase(genes.begin() + b);
			genes.erase(genes.begin());
		}
	}
}