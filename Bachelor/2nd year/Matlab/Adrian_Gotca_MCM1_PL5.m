z=[2,-3];
x=z;
n=3;
s=[1,0];
for i=1:n
    s(1)=s(1)+x(1);
    s(2)=s(2)+x(2);
    a=x(1);
    b=x(2);
    c=z(1);
    d=z(2);
    x(1)=a*c-b*d;
    x(2)=b*c+a*d;
end
disp(s);