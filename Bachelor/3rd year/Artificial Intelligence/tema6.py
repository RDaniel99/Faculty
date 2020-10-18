import random
from copy import deepcopy

# learning rate
ALPHA = 0.03
# discount factor
GAMMA = 0.95
# number of epochs for training
EPOCHS = 1000
# maximum number of steps per epoch
MAXIMUM_STEPS = 100

'''
A - position of the agent
* - destination
X - wall
0 - empty cell
'''
INITIAL_MAZE = [['X', '0', '0', 'A'],
                ['0', '0', '0', 'X'],
                ['0', '0', '0', 'X'],
                ['*', '0', 'X', 'X']]

'''
Possible moves
L - left
U - up
R - right
D - down
'''
MOVES = ['L', 'U', 'R', 'D']


class Maze:
    def __init__(self, q_table=None):
        self.maze = deepcopy(INITIAL_MAZE)
        self.final_state = self.get_position('*')  # position of the destination
        self.Q = q_table

    def get_position(self, symbol):
        for i in range(0, len(self.maze)):
            for j in range(0, len(self.maze[i])):
                if self.maze[i][j] == symbol:
                    return i, j

    def select_move(self, epsilon):
        """
        Selects the next move.
        It may be the one having the greatest Q value or a random one (allowing exploration of new paths)
        :param epsilon: exploration factor [0, 1]
        for values close to 1 it's more likely to choose a random action
        for values close to 0 it's more likely to choose the best move given by the Q table
        :return: next move (one of L, U, R, D)
        """
        if random.random() > 1 - epsilon:
            return random.choice(self.get_possible_moves())
        else:
            return self.get_best_q()

    def get_best_q(self):
        maximum = -float("inf")
        p = self.get_position('A')  # position of the agent
        best_moves = []
        for m in self.get_possible_moves():
            if self.Q[p, m] == maximum:
                best_moves.append(m)
            if self.Q[p, m] > maximum:
                maximum = self.Q[p, m]
                best_moves = [m]
        return random.choice(best_moves)  # one of the best Q's for current state

    def get_reward(self):
        """
        Compute the reward for the current state
        :return: 1 if the agent has reached the final state, -1 otherwise
        """
        return -1 if self.get_position('A') != self.final_state else 1

    def get_possible_moves(self):
        """
        :return: list containing all the possible moves from the current state
        """
        p = self.get_position('A')
        moves = deepcopy(MOVES)

        # remove invalid moves
        if p[1] == 0 or self.maze[p[0]][p[1] - 1] == 'X':
            moves.remove('L')

        if p[0] == 0 or self.maze[p[0] - 1][p[1]] == 'X':
            moves.remove('U')

        if p[1] == len(self.maze[p[0]]) - 1 or self.maze[p[0]][p[1] + 1] == 'X':
            moves.remove('R')

        if p[0] == len(self.maze) - 1 or self.maze[p[0] + 1][p[1]] == 'X':
            moves.remove('D')

        return moves

    def update_maze(self, move):
        p = self.get_position('A')
        self.maze[p[0]][p[1]] = '0'  # old position of the agent

        # new position based on the move
        if move == 'U':
            self.maze[p[0] - 1][p[1]] = 'A'

        if move == 'D':
            self.maze[p[0] + 1][p[1]] = 'A'

        if move == 'L':
            self.maze[p[0]][p[1] - 1] = 'A'

        if move == 'R':
            self.maze[p[0]][p[1] + 1] = 'A'

    def training(self):
        """
        Performs training in order to find optimal values for the Q table
        """
        self.Q = {}
        for i in range(0, len(INITIAL_MAZE)):
            for j in range(0, len(INITIAL_MAZE[i])):
                for k in MOVES:
                    self.Q[(i, j), k] = 0

        epsilon = 1  # allow more exploration in the beginning of the training

        for _ in range(EPOCHS):
            self.maze = deepcopy(INITIAL_MAZE)
            s = self.get_position('A')
            steps = 0
            while (s != self.final_state) and steps < MAXIMUM_STEPS:
                steps += 1
                next_move = self.select_move(epsilon)
                self.update_maze(next_move)

                r = self.get_reward()
                new_p = self.get_position('A')  # new position of the agent
                best_q = self.get_best_q()

                # update Q table using the TD learning rule
                self.Q[s, next_move] += ALPHA * (r + GAMMA * self.Q[new_p, best_q] - self.Q[s, next_move])

                s = self.get_position('A')
                epsilon -= (epsilon * 0.001)  # decay the exploration factor

    def test(self):
        print("TEST")
        self.maze = deepcopy(INITIAL_MAZE)
        self.print_maze()
        s = self.get_position('A')
        steps = 0
        while s != self.final_state:
            steps += 1
            self.update_maze(self.select_move(epsilon=0))
            s = self.get_position('A')
            self.print_maze()
        print("Agent reached destination in %d steps" % steps)

    def print_maze(self):
        for element in self.maze:
            print(element)
        print()


if __name__ == "__main__":
    maze = Maze()
    maze.training()
    maze.test()
