#1
df <- read.table("http://profs.info.uaic.ro/~cgatu/csia/res/alcool.dat", header=T)
#1.1
plot_object <- data.frame(df[2],df[3])
plot(plot_object, type="p")
#1.2
print(cor(df[2],df[3]))

#2
df <- read.table("http://profs.info.uaic.ro/~cgatu/csia/res/iq.dat", header=T)
#2.1
plot_object <- data.frame(df[2],df[3])
print(plot_object)
plot(plot_object, type="p")
#2.2
simpleRegression <- lm(Nota ~ IQ, data=plot_object)
print(simpleRegression)
print(predict(simpleRegression, data.frame(IQ=c(115,130))))

#3
generator <- function(m,a,b,xmin,xmax,sigma){
    errors <- rnorm(m, mean=0, sd=sigma)
    x <- runif(m,min=xmin,max=xmax)
    y <- a+b*x+errors
    ab <- estimator(x,y)
    a_p <- ab[1]
    b_p <- ab[2]
    pdf(paste('m_',m,'_a_',a,'_b_',b,'_xmin_',xmin,'_xmax_',xmax,'_sigma_',sigma,'.pdf',sep=''))
    plot(x, a+b*x,
        main="Graphs for the lines",
        ylab="",
        type="l",
        col="blue")
    lines(x,a_p+b_p*x, col="red")
    legend("topleft",
        c("y=a+b*x","y_p=a_p*x+b_p"),
        fill=c("blue","red")
    )
    dev.off()
}

#4
estimator <- function(x,y){
    dif_x <- x-mean(x)
    dif_y <- y-mean(y)
    b_p <- sum(dif_x*dif_y)/sum(dif_x*dif_x)
    a_p <- mean(y)-b_p*mean(x)
    print(paste("a=",a_p))
    print(paste("b=",b_p))
    df <- data.frame(x=x,y=y)
    model <- lm(x ~ y,data=df)
    print(confint(model, 'y', level=0.95))
    c(a_p,b_p)
}

generator(100,10,0.8,-200,200,1.5)
generator(10,10,0.8,-5,5,1)
generator(10000,10,0.8,-5,5,1)
generator(10,10,0.8,5,5.2,1)
generator(10000,10,0.8,5,5.2,1)
generator(10,10,0.8,5,5.2,0.01)
