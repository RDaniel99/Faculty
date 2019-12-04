var_ale=function(x,p){
  ok=0
  while (!ok){
    u=runif(1,0,1)
    s=0
    ind=0
    for (i in p){
      ind=ind+1
      fs=s
      s=s+i
      if (fs<=u&&u<s)
        return (ind)
    }
  }
}

matrix_product=function(A,B,C){
  n=nrow(A)
  r=matrix(0,nrow=n,ncol=1)
  x=matrix(0,nrow=n,ncol=1)
  y=matrix(0,nrow=n,ncol=1)
  r=sample(0:1,n,replace=TRUE)
  for (i in 1:n){
    x[i]=0
    for (j in 1:n){
      x[i]=(x[i]+B[i,j]*r[j])%%2
    }
  }
  for (i in 1:n){
    y[i]=0
    for (j in 1:n){
      y[i]=(y[i]+A[i,j]*x[j])%%2
    }
  }
  for (i in 1:n){
    x[i]=0
    for(j in 1:n){
      x[i]=(x[i]+C[i,j]*r[j])%%2
    }
  }
  for (i in 1:n){
    if (y[i]!=x[i]){
      return (FALSE)
    }
  }
  return (TRUE)
}

matrix_product_reduce=function(A,B,C,k){
  for (i in 1:k){
    if (!matrix_product(A,B,C)){
      return (FALSE)
    }
  }
  return (TRUE)
}

tree_eval=function(i,leaves){
  a=runif(1,0,1)
  len=length(leaves)
  if (log(i,2)>=log(len,2)-1){
    if (a<=0.5){
      if (leaves[2*i-len+1]==0){
        return (leaves[2*i+1-len+1])
      }
      return (1)
    }
    else {
      if (leaves[2*i+1-len+1]==0){
        return (leaves[2*i+1-len+1])
      }
      return (1)
    }
  }
  if (floor(log(i,2))%%2==0){
    if (a<=0.5){
      if (tree_eval(2*i,leaves)==1){
        return (tree_eval(2*i+1,leaves))
      }
      return (0);
    }
    else {
      if (tree_eval(2*i+1,leaves)==1){
        return (tree_eval(2*i,leaves))
      }
      return (0)
    }
  }
  else {
    if (a<=0.5){
      if (tree_eval(2*i,leaves)==0){
        return (tree_eval(2*i+1,leaves))
      }
      return (1)
    }
    else {
      if (tree_eval(2*i+1,leaves)==0){
        return (tree_eval(2*i,leaves))
      }
      return (1)
    }
  }
}

x=c(0,1,2)
p=c(0.3,0.2,0.5)
v=var_ale(x,p)
x=c(1,1,1)
y=c(1)
A=matrix(x,1,3)
B=matrix(x,3,1)
C=matrix(y,1,1)
matrix_product(A,B,C)
D=c(1,1,1,0,0,1,1,0,0,0,1,1,0,1,1,0)
tree_eval(8,D)

