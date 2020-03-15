import numpy as np
import math
import time


def citire():
    with open('test1.txt', 'r') as f:
        a = [[bool(int(num)) for num in line.split(' ')] for line in f]
    with open('test2.txt', 'r') as f:
        b = [[bool(int(num)) for num in line.split(' ')] for line in f]
    return a, b


def num(vector):
    p2 = 1
    number = 0
    for v in vector:
        number += p2 if v is True else 0
        p2 *= 2
    return number


def addZeroes(matrix):
    m = int(math.log2(len(matrix)))
    for l in matrix:
        while len(l) % m != 0:
            l.append(False)
    while len(matrix) % m != 0:
        matrix.append([0 for i in range(len(matrix[0]))])
    return matrix


def aduna(vector1, vector2):
    vector3 = list()
    vector3.extend(vector1)
    index = 0
    for elem in vector2:
        vector3[index] = True if vector3[index] is True or elem is True else False
        index += 1
    return vector3


# problema 1
u = lastu = 1
while 1 + u > 1:
    lastu = u
    u /= 10
print(lastu)
# problema 2
x = 1
y = z = lastu
print((x + y) + z)
print(x + (y + z))
print()
x = 100
y = z = 0.1
print((x * y) * z)
print(x * (y * z))
print()
# problema 3
a, b = citire()
len_a_b = len(a)
start_time = time.time()
result = []
for l in range(len(a)):
    result.append([False for _ in range(len(a))])
for i in range(len(a)):
    for j in range(len(b[0])):
        for k in range(len(b)):
            if a[i][k] == True and b[k][j] == True:
                result[i][j] = True
            else:
                result[i][j] = True if result[i][j] is True else False
print("--- %s seconds ---" % (time.time() - start_time))
with open("classic.out", "w") as f:
    for r in result:
        for value in r:
            f.write(str(value) + " ")
        f.write('\n')
a = addZeroes(a)
b = addZeroes(b)
for i in range(len(a)):
    a[i].insert(0, False)
for i in range(len(b)):
    b[i].insert(0, False)
n = len(a)
a.insert(0, [False for i in range(len(a[0]))])
b.insert(0, [False for i in range(len(b[0]))])
m = int(math.log2(n))
p = n // m
sum_linii_B = []
c = []
for i in range(n + 1):
    sum_linii_B.append([0 for _ in range(n + 1)])
    c.append([0 for _ in range(n + 1)])
start_time = time.time()
for i in range(1, p + 1):
    sum_linii_B[0] = [0 for _ in range(n + 1)]
    for j in range(1, (1 << m)):
        k = int(math.log2(j))
        sum_linii_B[j] = aduna(sum_linii_B[j - (1 << k)], b[m * (i - 1) + 1 + k])
    for r in range(1, n + 1):
        vector = []
        for k in range(m * (i - 1) + 1, m * i + 1):
            vector.append(a[r][k])
        sum = sum_linii_B[num(vector)]
        index = 0
        c[r] = aduna(c[r], sum)
for l in c:
    l.pop(0)
print("--- %s seconds ---" % (time.time() - start_time))
with open("optimized.out", "w") as f:
    for i in range(1, len_a_b + 1):
        for j in range(0, len_a_b):
            f.write(str(c[i][j]) + " ")
        f.write('\n')
