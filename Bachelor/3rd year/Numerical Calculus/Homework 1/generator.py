import random


def generate_matrix(file, n):
    for i in range(n):
        for j in range(n):
            file.write('0' if random.randint(0, 100) <= 90 else '1')
            if j != n - 1:
                file.write(" ")
        if i != n - 1:
            file.write('\n')


n = 500
with open("test1.txt", "w") as f:
    generate_matrix(f, n)
print("done 1")
with open("test2.txt", "w") as f:
    generate_matrix(f, n)
print("done 2")
