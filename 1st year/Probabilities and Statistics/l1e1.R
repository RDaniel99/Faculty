b=scan(n=12)
minim=b[1]
maxim=b[1]
nrluni_40=0
nrluni_35=0
total=0
for (i in 1:12){
  if (b[i]<minim){
    minim=b[i]
  }
  if (b[i]>maxim){
    maxim=b[i];
  }
  if (b[i]>40){
    nrluni_40=nrluni_40+1
  }
  if (b[i]<35){
    nrluni_35=nrluni_35+1
  }
  total=total+b[i]
}
procent=nrluni_35/12*100
