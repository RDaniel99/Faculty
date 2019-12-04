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

//ifstream adi("date.in");
ofstream isti("1.out");
vector <vector <int> > pop;
vector <int> bestRoadSoFar;
vector <double> fitness, values;
int nodes, node[NMAX], x[NMAX], y[NMAX], mat[NMAX][NMAX];
int bestSoFar=INT_MAX;
void read();
void eval();
double f(vector <int>);
void euristica();
void initializare();
void genetic();
int dist(int, int, int, int);
void crossover();
void crossover(int, int);
void mutation();
void select();
int main() {
	read();
	srand(time(NULL));
	genetic();
	return 0;
}

void euristica() {
	cout << "Euristica: ";
	vector <int> order;
	int Best = INT_MAX;
	for (int i = 1; i <= nodes; i++) {
		order.push_back(i);
	}
	do {
		int cost = 0;
		for (int i = 0; i < order.size() - 1; i++) {
			cost += mat[order[i]][order[i + 1]];
		}
		cost += mat[order[order.size() - 1]][order[0]];
		if (cost < Best) {
			Best = cost;
		}
	} while (next_permutation(order.begin(), order.end()));
	cout << Best << '\n';
}

void read() {
	ifstream adi("date.in");
	adi >> nodes;
	for (int i = 1; i <= nodes; i++) {
		adi >> node[i] >> x[i] >> y[i];
	}
	for (int i = 1; i <= nodes; i++) {
		for (int j = i + 1; j <= nodes; j++) {
			mat[node[i]][node[j]] = mat[node[j]][node[i]] = dist(x[i], y[i], x[j], y[j]);
		}
	}
}

int dist(int x1, int y1, int x2, int y2) {
	int dx = x1 - x2;
	int dy = y1 - y2;
	return int(sqrt(dx * dx + dy * dy) + 0.5);
}

void genetic() {
	initializare();
	eval();
	for (int gen = 1; gen <= NR_GENERATIONS; gen++) {
		cout << "Generatia " << gen << '\n';
		isti << "Generatia " << gen << '\n';
		vector <int> bestThis;
		int best = INT_MAX;
		for (int i = 0; i < POP_SIZE; i++) {
			if (values[i] < best) {
				bestThis = pop[i];
				best = values[i];
			}
		}
		isti << "Best in generation " << gen << ": " << best << '\n';
		for (int i = 0; i < nodes; i++) {
			isti << bestThis[i] << ' ';
		}
		isti << '\n';
		isti << "Best so far: " << bestSoFar << '\n';
		for (int i = 0; i < bestRoadSoFar.size(); i++) {
			isti << bestRoadSoFar[i] << ' ';
		}
		isti << bestRoadSoFar[0] << '\n';
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
	pop.resize(0);
	for (int i = 0; i < POP_SIZE; i++) {
		chromosome.resize(0);
		memset(viz, 0, sizeof(viz));
		int left = nodes;
		for (int i = 1; i <= nodes; i++) {
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
		pop.push_back(chromosome);
	}
}

void eval() {
	fitness.resize(0);
	values.resize(0);
	for (int i = 0; i < POP_SIZE; i++) {
		fitness.push_back(1.0/f(pop[i]));
		values.push_back(f(pop[i]));
		if (f(pop[i]) < bestSoFar) {
			bestSoFar = f(pop[i]);
			bestRoadSoFar = pop[i];
		}
	}
}

double f(vector <int> chromosome) {
	double s = 0;
	for (int i = 0; i < nodes - 1; i++) {
		s += mat[chromosome[i]][chromosome[i + 1]];
	}
	s += mat[chromosome[nodes - 1]][chromosome[0]];
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
		for (survival = 0; !(wheel[survival] <= r && r <= wheel[survival + 1]); survival++) {
			int whatever;
		}
		newpop.push_back(pop[survival]);
	}
	pop = newpop;
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
	for ( ; forCross.size();) {
		int j = rand() % forCross.size();
		while (j == 0) j = rand() % forCross.size();
		crossover(forCross[0], forCross[j]);
		forCross.erase(forCross.begin() + j);
		forCross.erase(forCross.begin());
	}
}

void crossover(int i, int j){
	vector <int> newpopI(nodes), newpopJ(nodes);
	vector <int> vizI(nodes + 1), vizJ(nodes + 1);
	int i1 = rand() % newpopI.size();
	int i2 = rand() % newpopI.size();
	if (i1 > i2) swap(i1, i2);
	for (int k = i1; k <= i2; k++) {
		newpopI[k] = pop[j][k]; vizI[pop[j][k]] = 1;
		newpopJ[k] = pop[i][k]; vizJ[pop[i][k]] = 1;
	}
	int it = 0;
	for (int k = 0; k < nodes; k++) {
		if (it == i1) it = i2 + 1;
		if (!vizI[pop[i][k]]) newpopI[it++] = pop[i][k];
	}
	it = 0;
	for (int k = 0; k < nodes; k++) {
		if (it == i1) it = i2 + 1;
		if (!vizJ[pop[j][k]]) newpopJ[it++] = pop[j][k];
	}
	pop[i] = newpopI;
	pop[j] = newpopJ;

}

void mutation() {
	for (int i = 0; i < POP_SIZE; i++) {
		vector <int> genes;
		for (int j = 0; j < nodes; j++) {
			double p = 1.0 * rand() / RAND_MAX;
			if (p < MUTATION_PROB) {
				genes.push_back(j);
			}
		}
		if (genes.size() % 2 == 1) genes.pop_back();
		for (; genes.size();) {
			int b = rand()%genes.size();
			while (b == 0) b = rand() % genes.size();
			swap(pop[i][genes[0]], pop[i][genes[b]]);
			genes.erase(genes.begin() + b);
			genes.erase(genes.begin());
		}
	}
}