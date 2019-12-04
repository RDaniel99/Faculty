maxprob=function(n,p){
  x=seq(0,n,1)
  y=(dbinom(x,n,p))
  maxim=0
  for (i in 1:n+1){
    if (maxim<y[i]){
      maxim=y[i]
    }
  }
  return (maxim)
}

n=50
p=0.7
m=maxprob(n,p)
