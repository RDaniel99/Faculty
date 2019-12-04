import random

road = list()
visited = set()


def initialize(b, nrt_m, nrt_c):
    return [b, nrt_m, nrt_c, 0, 0, 1]


def isFinal(state):
    if state[1] == 0 and state[2] == 0 and state[5] == -1:
        return True
    return False


def transition(state, nrm, nrc):
    return [state[0], state[1] - nrm * state[5], state[2] - nrc * state[5], state[3] + nrm * state[5], state[4] + nrc * state[5], -state[5]]


def validation(state, nrm, nrc):
    cs = [state[0], state[1] - nrm * state[5], state[2] - nrc * state[5], state[3] + nrm * state[5], state[4] + nrc * state[5], -state[5]]
    if cs[1] < 0 or cs[2] < 0 or cs[3] < 0 or cs[4] < 0:
        return False
    if cs[1] == 0 and cs[2] != 0:
        return False
    if cs[3] == 0 and cs[4] != 0:
        return False
    if cs[1] < cs[2]:
        return False
    if cs[3] < cs[4]:
        return False
    if nrm + nrc > cs[0]:
        return False
    return True


def strategy_BKT(state):
    if isFinal(state):
        for step in road:
            print(step)
        exit(0)
    for nrm in range(1, state[1] + 1 if state[5] == 1 else state[3] + 1):
        for nrc in range(1, state[2] + 1 if state[5] == 1 else state[4] + 1):
            news = transition(state, nrm, nrc)
            newsSet = set()
            newsSet.add(tuple(news))
            if validation(state, nrm, nrc) and len(visited.intersection(newsSet)) == 0:
                olds = state
                state = transition(state, nrm, nrc)
                visited.add(tuple(state))
                road.append(state)
                strategy_BKT(state)
                visited.discard(tuple(state))
                road.pop()
                state = olds
        nrc = 1


def strategy_random(state):
    inits = state
    road.append(state)
    while not isFinal(state):
        visited.add(tuple(state))
        nrm = random.randint(1, state[1] if state[5] == 1 else state[3])
        nrc = random.randint(1, state[2] if state[5] == 1 else state[4])
        news = transition(state, nrm, nrc)
        newsSet = set()
        newsSet.add(tuple(news))
        if validation(state, nrm, nrc) and len(visited.intersection(newsSet)) == 0:
            state = news
            road.append(state)
        if len(visited) >= 100:
            visited.clear()
            state = inits
            road.clear()


def main1():
    visited.clear()
    road.clear()
    strategy_random(s)
    for state in road:
        print(state)
    print("\n\n")


def main2():
    visited.clear()
    road.clear()
    road.append(s)
    visited.add(tuple(s))
    strategy_BKT(s)


s = initialize(8, 15, 10)
main1()
main2()
