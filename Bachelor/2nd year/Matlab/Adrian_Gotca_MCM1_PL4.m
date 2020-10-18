format bank;
s=[struct('inaltime',1.75,'miop',1),struct('inaltime',1.75,'miop',0),struct('inaltime',1.65,'miop',0),struct('inaltime',1.5,'miop',1),struct('inaltime',1.96,'miop',0),struct('inaltime',1.66,'miop',1),struct('inaltime',1.43,'miop',0),struct('inaltime',1.97,'miop',0),struct('inaltime',1.87,'miop',1),struct('inaltime',1.55,'miop',0),struct('inaltime',1.77,'miop',0),struct('inaltime',1.86,'miop',0),struct('inaltime',1.98,'miop',0),struct('inaltime',1.67,'miop',0),struct('inaltime',2.1,'miop',1),struct('inaltime',1.98,'miop',0),struct('inaltime',1.78,'miop',0),struct('inaltime',1.88,'miop',0),struct('inaltime',1.59,'miop',0),struct('inaltime',1.68,'miop',0),struct('inaltime',1.98,'miop',0),struct('inaltime',1.38,'miop',0),struct('inaltime',1.66,'miop',0),struct('inaltime',1.73,'miop',1),struct('inaltime',1.59,'miop',0),struct('inaltime',1.69,'miop',0),struct('inaltime',1.79,'miop',0),struct('inaltime',1.76,'miop',0),struct('inaltime',1.76,'miop',0),struct('inaltime',1.45,'miop',0)];
s=sortare(s);
for i=1:length(s)
    coloana=mod(i,3);
    if coloana==0
        coloana=3;
    end
    disp(i/3);
    linie=floor(i/3);
    if coloana<3
        linie=linie+1;
    end
    t=s(i);
    inaltimi(linie,coloana)=t.inaltime;
    miopi(linie,coloana)=t.miop;    
end
disp(inaltimi);
disp(miopi);

function y = sortare(x)
    y=x;
    N=length(y);
    for i=1:N 
        for j=i+1:N
            if y(i).inaltime>y(j).inaltime || (y(i).inaltime==y(j).inaltime && y(i).miop==1)
                tmp=y(i);
                y(i)=y(j);
                y(j)=tmp;
            end
        end
    end
end
