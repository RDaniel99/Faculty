eps=0.000001

class SparseMatrix:
    def __init__(self, n):
        self.matrix = dict()
        self.dim=n
    def add(self,position,value):
        if position not in self.matrix:
            self.matrix[position]=0
        self.matrix[position]+=value
    def finish(self):
        self.list=[]
        for key, value in self.matrix.items():
            self.list.append((key,value))
        self.list.sort()
        #print(self.list)
        #print(self.startIndex)
    def plus(self, anotherSparseMatrix):
        result = SparseMatrix(self.dim)
        it1=0
        it2=0
        while it1<len(self.list) and it2<len(anotherSparseMatrix.list):
            poz1=self.list[it1][0]
            v1=self.list[it1][1]
            poz2=anotherSparseMatrix.list[it2][0]
            v2=anotherSparseMatrix.list[it2][1]
            if poz1<poz2:
                result.add(poz1,v1)
                it1+=1
            elif poz1>poz2:
                result.add(poz2,v2)
                it2+=1
            else:
                result.add(poz1,v1+v2)
                it1+=1
                it2+=1
        while it1<len(self.list):
            poz=self.list[it1][0]
            val=self.list[it1][1]
            result.add(poz,val)
            it1+=1
        while it2<len(anotherSparseMatrix.list):
            poz=anotherSparseMatrix.list[it2][0]
            val=anotherSparseMatrix.list[it2][1]
            result.add(poz,val)
            it2+=1
        result.finish()
        return result
    def transpose(self):
        transpose=SparseMatrix(self.dim)
        for elem in self.list:
            transpose.add((elem[0][1],elem[0][0]),elem[1])
        transpose.finish()
        return transpose
    def times(self, anotherSparseMatrix):
        anotherSparseMatrix = anotherSparseMatrix.transpose()
        result = SparseMatrix(self.dim)
        it1=0
        while it1<len(self.list):
            it2=0
            elem1=self.list[it1]
            r=elem1[0][0]
            while it2<len(anotherSparseMatrix.list):
                elem2=anotherSparseMatrix.list[it2]
                c=elem2[0][0]
                temp1=it1
                temp2=it2
                s=0
                while temp1<len(self.list) and self.list[temp1][0][0]==r and temp2<len(anotherSparseMatrix.list) and anotherSparseMatrix.list[temp2][0][0]==c:
                    if self.list[temp1][0][1] < anotherSparseMatrix.list[temp2][0][1]:
                        temp1+=1
                    elif self.list[temp1][0][1] > anotherSparseMatrix.list[temp2][0][1]:
                        temp2+=1
                    else:
                        s+=self.list[temp1][1]*anotherSparseMatrix.list[temp2][1]
                        temp1+=1
                        temp2+=1
                if s!=0:
                    result.add((r,c),s)
                while it2<len(anotherSparseMatrix.list) and anotherSparseMatrix.list[it2][0][0]==c:
                    it2+=1
            while it1<len(self.list) and self.list[it1][0][0]==r:
                it1+=1
        result.finish()
        return result


def read(input_file):
    input_file = open(input_file,'r')
    n = int(input_file.readline())
    matrix = SparseMatrix(n)
    for l in input_file.readlines():
        if len(l.split(", "))==1:
            continue
        val,line,column=float(l.split(", ")[0]),int(l.split(", ")[1]),int(l.split(", ")[2])
        matrix.add((line,column),val)
    return matrix

def equals(list1, list2):
    if len(list1)!=len(list2):
        return False
    for i in range(len(list1)):
        if abs(list1[i][1]-list2[i][1]) > eps:
            return False
    return True

matrixA = read("/home/adriangotca/Faculty/3rd year/Numerical Calculus/Homework 3/a.txt")
matrixB = read("/home/adriangotca/Faculty/3rd year/Numerical Calculus/Homework 3/b.txt")
matrixAplusBresult = read("/home/adriangotca/Faculty/3rd year/Numerical Calculus/Homework 3/aplusb.txt")
matrixAtimesBresult = read("/home/adriangotca/Faculty/3rd year/Numerical Calculus/Homework 3/aorib.txt")
matrixA.finish()
matrixB.finish()
matrixAplusBresult.finish()
matrixAtimesBresult.finish()
matrixAplusBcalculated = matrixA.plus(matrixB)
print("Addition:" + str(equals(matrixAplusBcalculated.list, matrixAplusBresult.list)))
matrixAtimesBcalculated = matrixA.times(matrixB)
print("Multiplication:" + str(equals(matrixAtimesBresult.list, matrixAtimesBcalculated.list)))