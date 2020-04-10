#include <gl/glut.h>
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <math.h>
#include <assert.h>
#include <float.h>
#define dim 500
#define MODMAX_JF 10000000
#define NRITER_JF 5000
#define RX_JF 0.005
#define RY_JF 0.005

unsigned char prevKey;
int nivel = 0;
#define double long double

class C2coord
{
public:
	C2coord()
	{
		m.x = m.y = 0;
	}

	C2coord(double x, double y)
	{
		m.x = x;
		m.y = y;
	}

	C2coord(const C2coord &p)
	{
		m.x = p.m.x;
		m.y = p.m.y;
	}

	C2coord &operator=(const C2coord &p)
	{
		m.x = p.m.x;
		m.y = p.m.y;
		return *this;
	}

	int operator==(C2coord &p)
	{
		return ((m.x == p.m.x) && (m.y == p.m.y));
	}

protected:
	struct SDate
	{
		double x, y;
	} m;
};

class CPunct : public C2coord
{
public:
	CPunct() : C2coord(0.0, 0.0)
	{}

	CPunct(double x, double y) : C2coord(x, y)
	{}

	CPunct(const CPunct &p)
	{
		m.x = p.m.x;
		m.y = p.m.y;
	}

	CPunct& operator=(const CPunct& p) {
		m.x = p.m.x;
		m.y = p.m.y;
		return *this;
	}

	void getxy(double &x, double &y)
	{
		x = m.x;
		y = m.y;
	}

	int operator==(CPunct &p)
	{
		return ((m.x == p.m.x) && (m.y == p.m.y));
	}

	void marcheaza()
	{
		glBegin(GL_POINTS);
		glVertex2d(m.x, m.y);
		glEnd();
	}

	void print(FILE *fis)
	{
		fprintf(fis, "(%+f,%+f)", m.x, m.y);
	}

};

class CVector : public C2coord
{
public:
	CVector() : C2coord(0.0, 0.0)
	{
		normalizare();
	}

	CVector(double x, double y) : C2coord(x, y)
	{
		normalizare();
	}

	CVector &operator=(CVector &p)
	{
		m.x = p.m.x;
		m.y = p.m.y;
		return *this;
	}

	int operator==(CVector &p)
	{
		return ((m.x == p.m.x) && (m.y == p.m.y));
	}

	CPunct getDest(CPunct &orig, double lungime)
	{
		double x, y;
		orig.getxy(x, y);
		CPunct p(x + m.x * lungime, y + m.y * lungime);
		return p;
	}

	void rotatie(double grade)
	{
		double x = m.x;
		double y = m.y;
		double t = 2 * (4.0 * atan2(1.0, 1.0)) * grade / 360.0;
		m.x = x * cos(t) - y * sin(t);
		m.y = x * sin(t) + y * cos(t);
		normalizare();
	}

	void deseneaza(CPunct p, double lungime)
	{
		double x, y;
		p.getxy(x, y);
		glColor3f(1.0, 0.1, 0.1);
		glBegin(GL_LINE_STRIP);
			glVertex2d(x, y);
			glVertex2d(x + m.x * lungime, y + m.y * lungime);
		glEnd();
	}

	void print(FILE *fis)
	{
		fprintf(fis, "%+fi %+fj", C2coord::m.x, C2coord::m.y);
	}

private:
	void normalizare()
	{
		double d = sqrt(C2coord::m.x * C2coord::m.x + C2coord::m.y * C2coord::m.y);
		if (d != 0.0)
		{
			C2coord::m.x = C2coord::m.x * 1.0 / d;
			C2coord::m.y = C2coord::m.y * 1.0 / d;
		}
	}
};

class CCurbaKoch {

public:
	
	void segmentKoch(double lungime, int nivel, CPunct p, CVector v, int nr) {
		CPunct p1;
		int directionX[] = { 0, -1, 1, 1, -1 };
		int directionY[] = { 0, 1, 1, -1, -1 };
		glFlush();
		if (nivel == 0)
			v.deseneaza(p, lungime);
		else {

			v.deseneaza(p, lungime);
			double newX, newY;
			p.getxy(newX, newY);
			newX -= (lungime / 2) * directionX[nr];
			newY -= (lungime / 2) * directionY[nr];
			CPunct centru(newX, newY);
			double xCentru, yCentru;
			centru.getxy(xCentru, yCentru);
			int dx[] = {-1, -1, 0, 1, 1, 1, 0, -1}; // stanga mijloc -> sus stanga(sens trigonometric)
			int dy[] = {0, 1, 1, 1, 0, -1, -1, -1}; // la fel aici
			for (int i = 0; i < 8; i++) {
				CPunct next(xCentru + directionX[nr] * lungime / 6 + lungime * 2 * dx[i],
					yCentru + directionY[nr] * lungime / 6 + lungime * 2 * dy[i]);
				segmentKoch(lungime / 3, nivel - 1, next, v, nr);
			}
		}
	}

	void deseneazaContur(double lungime) {
		double X = lungime / 2;
		double Y = lungime / 2;
		CPunct p1(-X, Y);
		CVector v1(1, 0);
		v1.deseneaza(p1, lungime);

		CPunct p2(X, Y);
		CVector v2(0, -1);
		v2.deseneaza(p2, lungime);

		CPunct p3(X, -Y);
		CVector v3(-1, 0);
		v3.deseneaza(p3, lungime);

		CPunct p4(-X, -Y);
		CVector v4(0, 1);
		v4.deseneaza(p4, lungime);
	}

	void afisare(double lungime, int nivel) {
		double X = sqrt(2.0) / 32.0;
		double Y = sqrt(2.0) / 32.0;
		CPunct p1(-X, Y);
		CVector v1(1, 0);

		CPunct p2(X, Y);
		CVector v2(0, -1);

		CPunct p3(X, -Y);
		CVector v3(-1, 0);

		CPunct p4(-X, -Y);
		CVector v4(0, 1);

		deseneazaContur(lungime * (13 / 6) * 2 * 2);

		segmentKoch(lungime, nivel, p1, v1, 1);
		segmentKoch(lungime, nivel, p2, v2, 2);
		segmentKoch(lungime, nivel, p3, v3, 3);
		segmentKoch(lungime, nivel, p4, v4, 4);
	}
};

class CComplex {
public:
	CComplex() : re(0.0), im(0.0) {}
	CComplex(double re1, double im1) : re(re1 * 1.0), im(im1 * 1.0) {}
	CComplex(const CComplex& c) : re(c.re), im(c.im) {}
	~CComplex() {}

	CComplex& operator=(const CComplex& c)
	{
		re = c.re;
		im = c.im;
		return *this;
	}

	CComplex operator*(const CComplex& c)
	{
		return CComplex(re * c.re - im * c.im, re * c.im + im * c.re);
	}

	double getRe() { return re; }
	void setRe(double re1) { re = re1; }

	double getIm() { return im; }
	void setIm(double im1) { im = im1; }

	double getModul() { return sqrt(re * re + im * im); }

	int operator==(CComplex& c1)
	{
		return ((re == c1.re) && (im == c1.im));
	}

	CComplex pow2()
	{
		CComplex rez;
		rez.re = powl(re * 1.0, 2) - powl(im * 1.0, 2);
		rez.im = 2.0 * re * im;
		return rez;
	}

	CComplex operator+(CComplex const& c1)
	{
		return CComplex(c1.re + re, c1.im + im);
	}

	void print(FILE* f)
	{
		fprintf(f, "%.20f%+.20f i", re, im);
	}

private:
	double re, im;
};

class CMandelbrot {
public:
	CMandelbrot() {
		// m.c se initializeaza implicit cu 0+0i

		m.nriter = NRITER_JF;
		m.modmax = MODMAX_JF;
	}

	CMandelbrot(CComplex& c) {
		m.c = c;
		m.nriter = NRITER_JF;
		m.modmax = MODMAX_JF;
	}

	~CMandelbrot() {}

	void setmodmax(double v) { assert(v <= MODMAX_JF); m.modmax = v; }
	double getmodmax() { return m.modmax; }

	void setnriter(int v) { assert(v <= NRITER_JF); m.nriter = v; }
	int getnriter() { return m.nriter; }

	// testeaza daca x apartine multimii Julia-Fatou Jc
	// returneaza 0 daca apartine, -1 daca converge finit, +1 daca converge infinit
	int isIn(CComplex& x) {
		int rez = -1;
		// tablou in care vor fi memorate valorile procesului iterativ z_n+1 = z_n * z_n + c
		CComplex z0, z1;

		z0 = CComplex(0, 0);
		for (int i = 1; i < m.nriter; i++) {
			z1 = z0 * z0 + x;
			if (z1 == z0) {
				rez = -1;
				break;
			}
			else if (z1.getModul() > m.modmax) {
				// x nu apartine m.J-F deoarece procesul iterativ converge la infinit
				rez = i;
				break;
			}
			z0 = z1;
		}
		return rez;
	}

	// afisarea multimii J-F care intersecteaza multimea argument
	void display(double xmin, double ymin, double xmax, double ymax) {
		glPushMatrix();
		glLoadIdentity();

		glTranslated((xmin + xmax) * fmax(fabs(xmax), fabs(xmin)) / (xmin - xmax),
			(ymin + ymax) * fmax(fabs(ymax), fabs(ymin)) / (ymin - ymax), 0);
		glScaled(fmax(fabs(xmax), fabs(xmin)) / (xmax - xmin),
			fmax(fabs(ymax), fabs(ymin)) / (ymax - ymin), 1);
		// afisarea propriu-zisa
		glBegin(GL_POINTS);
		for (double x = xmin; x <= xmax; x += RX_JF)
			for (double y = ymin; y <= ymax; y += RY_JF) {
				CComplex z(x, y);
				int r = isIn(z);
				if (r == -1)
					glColor3f(1.0, 0.1, 0.1), //rosu
					glVertex3d(x, y, 0); // apartine
				else
					glColor3f(0, 0, (1.0 / r) * 2), // albastru -> negru(nr mare iteratii, negru
					glVertex3d(x, y, 0); // Nu apartine
			}
		glEnd();
		glPopMatrix();
	}

private:
	struct SDate {
		CComplex c;
		// nr. de iteratii
		int nriter = 0;
		// modulul maxim
		double modmax = 0;
	} m;
};

class CSierpinski {

public:

	void curbaSierpinski(int nivel, double lungime, CPunct p, CVector v)
	{
		CPunct temp = p;
		if (nivel % 2 == 1)
		{
			curve(nivel, lungime, 60, p, v);
		}
		else
		{
			v.rotatie(60);
			curve(nivel, lungime, -60, p, v);
		}
	}

	void curve(int nivel, double lungime, double unghi, CPunct p, CVector v)
	{
		if (nivel == 0)
		{
			v.deseneaza(p, lungime);
		}
		else
		{
			v.rotatie(unghi);
			curve(nivel - 1, lungime / 2, -unghi, p, v);

			p = v.getDest(p, lungime / 2);
			v.rotatie(-unghi);
			curve(nivel - 1, lungime / 2, unghi, p, v);

			p = v.getDest(p, lungime / 2);
			v.rotatie(-unghi);
			curve(nivel - 1, lungime / 2, -unghi, p, v);
		}
	}

	void afisare(double lungime, int nivel)
	{
		CVector v(1.0, 1.0/sqrt(3));
		CPunct p(-0.7, -0.7);
		curbaSierpinski(nivel, lungime, p, v);
	}
};

class CArborePerron2 {

public:

	void arborePerron(double lungime, int nivel,
		double factordiviziune, CPunct p, CVector v) {
		assert(factordiviziune != 0);
		CPunct p1, p2;
		if (nivel == 0) { }
		else {
			v.rotatie(-45);
			v.deseneaza(p, lungime);
			p1 = v.getDest(p, lungime);
			arborePerron(lungime * factordiviziune, nivel - 1, factordiviziune, p1, v);

			v.rotatie(90);
			v.deseneaza(p, lungime);
			p1 = v.getDest(p, lungime);
			p2 = p1;

			v.rotatie(15);
			v.deseneaza(p1, lungime);
			p1 = v.getDest(p1, lungime);
			arborePerron(lungime * factordiviziune, nivel - 1, factordiviziune, p1, v);

			p1 = p2;
			v.rotatie(-60);
			v.deseneaza(p1, lungime);
			p1 = v.getDest(p1, lungime);
			p2 = p1;

			v.rotatie(-90);
			v.deseneaza(p1, lungime * 0.5);
			p1 = v.getDest(p1, lungime * 0.5);
			arborePerron(lungime * factordiviziune, nivel - 1, factordiviziune, p1, v);

			p1 = p2;
			v.rotatie(120);
			v.deseneaza(p1, lungime * 0.5);
			p1 = v.getDest(p1, lungime * 0.5);
			arborePerron(lungime * factordiviziune, nivel - 1, factordiviziune, p1, v);
		}
	}

	void afisare(double lungime, int nivel) {
		CVector v(0.0, -1.0);
		CPunct p(0.0, 3.0);
		v.deseneaza(p, lungime);
		p = v.getDest(p, lungime);
		arborePerron(lungime, nivel, 0.37, p, v);
	}
};

void Display1() {
	CCurbaKoch cck;
	cck.afisare(sqrt(2.0) / 16, nivel);

	char c[3];
	sprintf(c, "%2d", nivel);
	glRasterPos2d(-0.98, -0.98);
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'N');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'i');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'v');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'e');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'l');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, '=');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, c[0]);
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, c[1]);

	glRasterPos2d(-1.0, -0.9);
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'c');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'u');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'r');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'b');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'a');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, ' ');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'l');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'u');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'i');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, ' ');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'K');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'o');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'c');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'h');

	nivel++;
}

void Display2() {
	CArborePerron2 cap;

	char c[3];
	sprintf(c, "%2d", nivel);
	glRasterPos2d(-0.98, -0.98);
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'N');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'i');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'v');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'e');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'l');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, '=');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, c[0]);
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, c[1]);

	glRasterPos2d(-1.0, 0.9);

	glPushMatrix();
	glLoadIdentity();
	glScaled(0.4, 0.4, 1);
	glTranslated(-0.5, -0.5, 0.0);
	cap.afisare(1, nivel);
	glPopMatrix();
	nivel++;
}

void Display3() {
	CSierpinski cch;
	cch.afisare(1.5, nivel);

	char c[3];
	sprintf(c, "%2d", nivel);
	glRasterPos2d(-0.98, -0.98);
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'N');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'i');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'v');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'e');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'l');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, '=');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, c[0]);
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, c[1]);

	glRasterPos2d(-1.0, -0.9);
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'c');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'u');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'r');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'b');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'a');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, ' ');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'H');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'i');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'l');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'b');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'e');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 'r');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, 't');
	glutBitmapCharacter(GLUT_BITMAP_9_BY_15, '2');

	nivel++;
}

void Display4() {
	CComplex c(0, 0);
	CMandelbrot cm(c);

	glColor3f(1.0, 0.1, 0.1);
	cm.setmodmax(2);
	cm.setnriter(10);
	cm.display(-2, -2, 2, 2);
}

void Init(void) {

	glClearColor(1.0, 1.0, 1.0, 1.0);

	glLineWidth(1);

	glPointSize(4); 

	glPolygonMode(GL_FRONT, GL_LINE);
}

void Display(void) {
	switch (prevKey) {
	case '0':
		glClear(GL_COLOR_BUFFER_BIT);
		nivel = 0;
		fprintf(stderr, "nivel = %d\n", nivel);
		break;
	case '1':
		glClear(GL_COLOR_BUFFER_BIT);
		Display1();
		break;
	case '2':
		glClear(GL_COLOR_BUFFER_BIT);
		Display2();
		break;
	case '3':
		glClear(GL_COLOR_BUFFER_BIT);
		Display3();
		break;
	case '4':
		glClear(GL_COLOR_BUFFER_BIT);
		Display4();
		break;
	default:
		break;
	}

	glFlush();
}

void Reshape(int w, int h)
{
	glViewport(0, 0, (GLsizei)w, (GLsizei)h);
}

void KeyboardFunc(unsigned char key, int x, int y)
{
	prevKey = key;
	if (key == 27) // escape
		exit(0);
	glutPostRedisplay();
}

void MouseFunc(int button, int state, int x, int y)
{
}

int main(int argc, char** argv)
{
	glutInit(&argc, argv);

	glutInitWindowSize(dim, dim);

	glutInitWindowPosition(100, 100);

	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);

	glutCreateWindow(argv[0]);

	Init();

	glutReshapeFunc(Reshape);

	glutKeyboardFunc(KeyboardFunc);

	glutMouseFunc(MouseFunc);

	glutDisplayFunc(Display);

	glutMainLoop();

	return 0;
}