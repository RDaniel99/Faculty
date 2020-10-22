import numpy as np
import random
import math
import matplotlib.pyplot as plt
def generate_points(count, x_interval, y_interval, sign):
    x = np.random.uniform(*x_interval, (count, 1))
    y = np.random.uniform(*y_interval, (count, 1))
    signs = np.ones((count, 1)) * sign
    return np.concatenate((x, y, signs), axis=1)
def hill_climb(points, line):
    no_iterations = 100
    current_fitness = fitness(points, line)
    for _ in range(no_iterations):
        print(f"Current fitness: {current_fitness}")
        # generate neighbours
        neighbours = generate_neighbours(line, no_neighbours)
        # calculate fitness for each neighbour
        fitnesses = np.apply_along_axis(
            lambda neighbour: fitness(points, neighbour), 1, neighbours)
        # select the neighbour with the highest fitness
        best_neighbour_idx = np.argmax(fitnesses)
        current_fitness = fitnesses[best_neighbour_idx]
        line = neighbours[best_neighbour_idx]
        plot_process(points, line)
        if current_fitness == 1/epsilon:
            print("Found a perfect line, stopping hill climb...")
            break
    return line
def plot_process(points, line):
    plt.clf()
    plt.scatter(points[:len(points)//2, 0],
                points[:len(points)//2, 1], c="blue")
    plt.scatter(points[len(points)//2:, 0],
                points[len(points)//2:, 1], c="orange")
    # plot the line
    x = np.linspace(min(points[:, 0]), max(points[:, 1]), 2000)
    x = np.reshape(x, (len(x), 1))
    # ax + by + c = 0 ==> y = (-c - ax)/b
    y = (-line[2] - line[0]*x)/line[1]
    y = np.reshape(y, (len(y), 1))
    # pack points
    # this is logic to show only that portion of the line
    # which is between [y_min and y_max]
    # so I keep the same "level" of zoom between the points and the line
    line = np.concatenate((x, y), axis=1)
    good_line_points = np.array([p[1] >= 0 and p[1] <= y_max for p in line])
    if np.sum(good_line_points) >= 2:
        # if I have at least 2 points that would fit in my "view"
        # then build the line only with those points
        line = line[good_line_points]
    plt.scatter(line[:, 0], line[:, 1], c="red", s=1)
    plt.draw()
    plt.pause(0.00001)  # a pause for the plot to update
def generate_neighbours(line, no_neighbours):
    margin_of_error = 1e-1
    a = np.random.uniform(line[0] - margin_of_error,
                          line[0] + margin_of_error, (no_neighbours, 1))
    b = np.random.uniform(line[1] - margin_of_error,
                          line[1] + margin_of_error, (no_neighbours, 1))
    c = np.random.uniform(line[2] - margin_of_error,
                          line[2] + margin_of_error, (no_neighbours, 1))
    return np.concatenate((a, b, c), axis=1)
def fitness(points, line):
    # calculate an error sum and return 1/(error + epsilon) as the fitness
    # if a point is on the right side of the line, don't add anything to the error
    # if a point isn't on the right side, add the distance to the line as an error
    error = 0
    line_values = np.dot(points[:,:2], line[:2]) + line[2]
    first_half = line_values[:point_count//2]
    err_left = np.where(first_half < 0, abs(first_half), 0)
    second_half = line_values[point_count//2:]
    err_right = np.where(second_half > 0, second_half, 0)
    error = np.sum(err_left) + np.sum(err_right)
    return 1/(error + epsilon)
epsilon = 1e-10
no_neighbours = 50
point_count = 100
x_min_left = 0
x_max_left = 100
x_min_right = x_max_left + 10
x_max_right = x_max_left * 2
y_min = 0
y_max = 200
if __name__ == "__main__":
    points = np.concatenate(
        (
            generate_points(point_count // 2,
                            [x_min_left, x_max_left], [y_min, y_max], 1),
            generate_points(point_count // 2,
                            [x_min_right, x_max_right], [y_min, y_max], -1),
        ),
        axis=0
    )
    # print(points)
    line = np.random.rand(3)
    # print(line)
    # c must be between [0, 100]
    line[2] *= 100
    # print(line)
    # run hill climb
    line = hill_climb(points, line)
    print(f"Best line found is: {line}")
    plt.show()