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

b=scan("D:/ady_info/PS/vector.txt")
v1=logar(b)
v2=sub_b(b)

