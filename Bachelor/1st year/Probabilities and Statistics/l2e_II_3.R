s=scan("myfile.txt")
ans=vector()
k=0
maxim=0
for (i in 2:length(s)){
  nr=1
  for (j in 1:(i-1)){
    if (s[i]==s[j]){
      nr=nr+1 
    }
  }
  if (nr>maxim){
    maxim=nr
    k=0
  }
  if (nr==maxim){
    k=k+1
    ans[k]=s[i]
  }
}
ans
