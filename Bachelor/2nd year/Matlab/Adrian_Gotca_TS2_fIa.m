syms x(t) y(t) t;
U=t^5/7*(6*x-2*y-t/3-6/5);
verif(U);

function []=verif(expresie)
syms x(t) y(t) t;
f1=(t+1+2*y-4*x)/t;
f2=(2*t+y+3*x)/t;
confirm=diff(expresie, diff(x,t))*f1 + diff(expresie, diff(y,t))*f2 +diff(expresie, t)*1;
disp(confirm);
end