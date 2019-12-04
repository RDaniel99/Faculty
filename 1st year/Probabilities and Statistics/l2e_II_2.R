tablou = read.csv('life_expect.csv', header = T, sep = ',')
female = tablou[['female']]
male=tablou[['male']]
mean(male)
median(male)
mean(female)
median(female)
