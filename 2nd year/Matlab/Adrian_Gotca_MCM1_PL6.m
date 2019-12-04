subplot(4,4,1);
xc=1; yc=1; zc=1;    % coordinated of the center
L=10;                 % cube size (length of an edge)
alpha=0.0;           % transparency (max=1=opaque)
X = [0 0 0 0 0 1; 1 0 1 1 1 1; 1 0 1 1 1 1; 0 0 0 0 0 1];
Y = [0 0 0 0 1 0; 0 1 0 0 1 1; 0 1 1 1 1 1; 0 0 1 1 1 0];
Z = [0 0 1 0 0 0; 0 0 1 0 0 0; 1 1 1 0 1 1; 1 1 1 0 1 1];
C='blue';                  % unicolor
X = L*X + xc;
Y = L*Y + yc;
Z = L*Z + zc; 
fill3(X,Y,Z,C,'FaceAlpha',alpha);    % draw cube
axis equal
subplot(4,4,[5;9;13]);
cylinder([100,50],5);
subplot(4,4,[2;6;10]);
X=[0,1,1,2,1,1,2,1,1,0,0,-1,0,0,-1,0];
Y=[0,0,1,2,3,4,5,6,7,7,6,5,4,3,2,1];
fill(X,Y,'cyan');
hold on;
X=[0.5,1,0.5,0];
Y=[5.5,5,4.5,5];
fill(X,Y,'white');
hold on;
X=[0.5,1,0.5,0];
Y=[2.5,2,1.5,2];
fill(X,Y,'white');
subplot(4,4,[3,4]);
[x,y]=meshgrid(-10:10);
meshc(x.^5-y.^4);
subplot(4,4,[7;11]);
cylinder([0,100],50);
subplot(4,4,8);
X=[0,1,2,3];
Y=[0,1,1,0];
fill(X,Y,'blue');
hold on;
Y=[0,-1,-1,0];
fill(X,Y,'yellow');
subplot(4,4,12);
sphere;
subplot(4,4,[14,15,16]);
x=5:0.05:10;
f=-sin(7*x).*exp(x-2);
g=cos(5*x).*exp(-x.*x-1);
plot(x,f,x,g);