n=input('order of your square matrix is? ');
for ind=1:n^2
    a(ind)=input('element-');
end
A=reshape(a,n,n);
B=A;
pn=trace(B);
P=[1 -pn];
pn1=0;
E=eye(n);
for ind=2:n
    C=B-E*pn;
    B=A*C;
    pn1=pn;
    pn=1/ind*trace(B);
    P=[P(1:end) -pn];
end
inv=(1/pn)*C;
disp(inv);

PD=polyder(P);
rad=roots(PD);
prev=cast(rad(1),'int32');
curr=cast(rad(1),'int32');
disp(curr);
for i=2:size(rad)
    curr=cast(rad(i),'int32');
    disp(curr);
    if prev*curr<0
       while curr*prev<0
            curr=curr-1;
       end
       disp([curr,curr+1]);
    end
    prev=cast(rad(i),'int32');
end





