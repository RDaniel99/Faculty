#2.
x=c(1, 8, 2, 6, 3, 8, 8, 5, 5, 5)
print(paste("a: ",sum(x)/10))
print(paste("b: ",log2(x)))
print(paste("c: ",max(x)-min(x)))
y = (x-5.1)/2.514403 
print(paste("d: ",toString(y))) 
print(paste("e: ",toString(c(mean(y),sd(y)))))

#3.
factura = c(46, 33, 39, 37, 46, 30, 48, 32, 49, 35, 30, 48)
print(paste("sum=",sum(factura)))
print(paste("max=",max(factura)))
print(paste("min=",min(factura)))
print(paste("more than 40 in ",length(which(factura>40)), "months, which is", length(which(factura>40))/length(factura)*100, "% of the year"))

#4.
vec = scan()
print(paste("max=",max(vec),"min=",min(vec),"mean=",mean(vec),"median=",median(vec),"sd=",sd(vec)))
print(sort(vec))
print(scale(vec))