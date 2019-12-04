tablou = read.csv('life_expect.csv', header = T, sep = ',')
female = tablou[['female']]
male=tablou[['male']]
hist(male,breaks=7,right=T,col="blue")
hist(female,breaks=7,right=T,col="blue")
