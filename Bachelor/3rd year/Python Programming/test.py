def problema1(numar,cifra):
    nr=0
    if numar==0 and cifra==0:
        return 1
    while numar:
        if numar%10==cifra:
            nr+=1
        numar//=10
    return nr

def problema2(*args,**kwargs):
    nr=0
    for arg in args:
        if arg in kwargs.values():
            nr+=1
    return nr

def problema3(matrice):
    for i in range(len(matrice)):
        for j in range(i):
            matrice[i][j]=0
    return matrice

def problema4(notes,moves,start):
    ans=[]
    pos=start
    while pos < 0:
        pos += len(notes)
    if (pos > len(notes)):
        pos %= len(notes)
    ans.append(notes[pos])
    for i in moves:
        pos+=i
        while pos<0:
            pos+=len(notes)
        if (pos>=len(notes)):
            pos%=len(notes)
        ans.append(notes[pos])
    return ans

def problema5(cod):
    l=cod.split("\n")
    x=0
    for instr in l:
        op=instr.split(" ")[1]
        nr=(int)(instr.split(" ")[2])
        if op=="egal":
            x=nr
        if op=="plus":
            x+=nr
        if op=="minus":
            x-=nr
        if op=="impartit":
            x//=nr
        if op=="inmultit":
            x*=nr
    return x