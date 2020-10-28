#1.
a=scan()
b=scan()
fun = function(x){x*x}
plot(fun(a:b),main=paste("f(x)=x^2, x in [",a,",",b,"]", sep=""), type='l',col='red', lwd=2)
#2.
p=c(1:9/10)
success = 0:20
for (prob in p)
{
    pdf(paste('binom_',prob,'.pdf',sep=''))
    plot(success, dbinom(success, size=20, prob=prob), type='h', main=paste("Binomial distribution for n=20 and p=",prob,sep=""))
    dev.off()
}
#3.
dnorm1=dnorm(c(-5:5),sd=0.5)
dnorm2=dnorm(c(-5:5),sd=1)
dnorm3=dnorm(c(-5:5),sd=2)
matplot(x=c(-5:5),y=cbind(dnorm1, dnorm2, dnorm3),type='l',col=c('red','blue','dark green'))
#4.
#a.
CLT <- function(n){
    v=c()
    for (i in 1:1000)
    {
        vi=runif(n,0,20)
        v=c(v,mean(vi))
    }
    v
}
#b.
ns=c(1,5,10,100)
for (n in ns)
{
    pdf(paste('histo_norm_',n,'.pdf',sep=''))
    hist(CLT(n), breaks=50, main=paste("Histogram for n=",n,sep=""))
    dev.off()
}
#c.
CLT2 <- function(n){
    v=c()
    for (i in 1:1000)
    {
        vi=rbinom(n,20,0.1)
        v=c(v,mean(vi))
    }
    v
}
for (n in ns)
{
    pdf(paste('histo_binom_',n,'.pdf',sep=''))
    hist(CLT2(n), main=paste("Histogram for n=",n,sep=""))
    dev.off()
}