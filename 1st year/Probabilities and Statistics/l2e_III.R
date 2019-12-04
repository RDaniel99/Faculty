outliers_mean=function(sample){
  sample = scan('sample2.txt')
  m = mean(sample)
  s = sd(sample)
  new_sample = vector()
  j = 0
  for(i in 1:length(sample))
     if(sample[i] <m - 2*s || sample[i] > m + 2*s) {
       j = j + 1
       new_sample[j]=sample[i]
     }
  new_sample
}

outpliers_iqr=function(sample){
  sample = scan('sample2.txt')
  q3=as.vector(quantile(sample))[4]
  q1=as.vector(quantile(sample))[2]
  iqr=q3-q1
  low=q1-1.5*iqr
  high=q3+1.5*iqr
  new_sample = vector()
  j = 0
  for(i in 1:length(sample))
    if(sample[i] <low || sample[i] > high) {
      j = j + 1
      new_sample[j]=sample[i]
    }
  new_sample
}

sumar=function(sample){
  summary(sample)
}

