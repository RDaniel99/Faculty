mprobPoisson=function(n,l){
  x=seq(0,n,1)
  y=dpois(x,l)
  maxim=0
  for (i in 1:n+1){
    if (y[i]>maxim){
      maxim=y[i]
    }
  }
  return (maxim)
}

n=20
l=0.7
max=mprobPoisson(n,l)
