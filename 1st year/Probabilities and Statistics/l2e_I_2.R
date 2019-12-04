tablou = read.csv('unemploy2012 (1).csv', header = T, sep = ';')
rate = tablou[['rate']]
interval=c(0,4,6,8,10,12,14,300)
hist(rate,breaks=interval,right=T,freq=F,include.lowest = F,col="blue")
