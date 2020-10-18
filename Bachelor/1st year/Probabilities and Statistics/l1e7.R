main=function(){
  headers=read.table("D:/ady_info/PS/test.txt",header = T )
  x=headers[['AA']]
  y=headers[['BB']]
  plot(x, y, type = "l", main="grafic", sub = 'subtilul', xlab ='axa x', ylab = 'axa y')
  return (headers)
}

main()
