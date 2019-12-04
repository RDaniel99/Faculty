densitate=function(n,p){
  x=seq(0,n,1)
  y=(dbinom(x,n,p))
  barplot(y,space=0,main='barplot',sub='subtitlu',xlab='axa x',ylab='axa y')
}

densitate(25,0.3)
densitate(50,0.7)
densitate(35,0.5)
