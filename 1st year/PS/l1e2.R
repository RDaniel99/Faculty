logar = function(b){
  v=vector()
  for (i in 1:length(b)){
    v[i]=log(b[i])
  }
  return (v)
}

sub_b=function(b){
  v=vector()
  maxim=b[1]
  minim=b[1]
  for (i in 1:length(b)){
    if (maxim<b[i]){
      maxim=b[i]
    }
    if (minim>b[i]){
      minim=b[i]
    }
  }
  for (i in 1:length(b)){
    v[i]=(b[i]-maxim)/minim;
  }
  return (v)
}

x=scan(n=1)
b=scan(n=x[1])
v1=logar(b)
v2=sub_b(b)

