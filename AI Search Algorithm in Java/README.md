# Artificial Intelligence Search Algorithm in Java ☕
In this project we will look at different type of artificial intelligence search strategy to solve a toy puzzle game. This include uninformed search strategy (i.e. which are not using any heuristic function) like breadth first search (BFS), depth first search (DFS) or iterative deepening search (IDS) and informed search strategy like greedy search (GDS), steepest ascent hill-climbing (HCS) and A* search algorithm. 

## Table of Content
- [Puzzle to Solve: Three Digits Puzzle](#puzzle-to-solve-three-digits-puzzle)
- [Uninformed Search Algorithms](#uninformed-search-algorithms)
  - [Breadth First Search](#breadth-first-search-bfs)
  - [Depth First Search](#depth-first-search-dfs)
  - [Iterative Deepening Search](#iterative-deepening-search-ids)
- [Informed Search Algorithms](#informed-search-algorithms)
  - [Greedy Search](#greedy-search-gds)
  - [Steepest Ascent Hill Climbing](#steepest-ascent-hill-climbing-hcs)
  - [A*](#a-search)
- [References](#references)

## Puzzle to Solve: Three Digits Puzzle
The toy game we will be using in this mini-project is the three digits puzzle. It is defined as follow:
Given two 3-digit numbers called _S (start)_ and _G (goal)_ and also a set of 3-digit numbers called
forbidden. We want to get from S to G in the smallest number of moves. A move is a transformation of one number into another number by adding or subtracting 1 to one of its digits. For example, a move can take you from 123 to 124 by adding 1 to the last digit or from 953 to 853 by subtracting 1 from the first digit. 

Moves must satisfy the following constraints:
1. You cannot add to the digit 9 or subtract from the digit 0;
2. You cannot make a move that transforms the current number into one of the forbidden numbers;
3. You cannot change the same digit twice in two successive moves

This puzzle was taken from the course Introduction to Artificial Intelligence - COMP3308 **[1]** from the [Univeristy of Sidney](https://www.sydney.edu.au/)

To solve this puzzle we make use of states graph and search trees. 

States graph are a mathematical representation of the world the agent is in. The nodes in the graph are properties of the world the agent care about bundled inside a state construct. In that graph each state can only occurs once and the link from one state to the other is achieved by the agent making an action from one state to another.
See below an example of a state graph for a small game of pacman: 

**IMAGE**

Search trees are similar in the sense that the nodes are state, but they are different in the sense that they codify the plan to go from one node to a goal node. The tree is constructed as the agent interact with the world to fullfill its purpose and different algorithm will make use of this search tree differently.
See below an example of a search tree for a small game of pacman:

**IMAGE**

For more information I would highly suggest the slides from the CS188 Intro to AI [2] from [UC Berkeley](https://www.berkeley.edu/)

## Uninformed Search Algorithms

### Breadth First Search (BFS)

### Depth First Search (DFS)

### Iterative Deepening Search (IDS)

## Informed Search Algorithms

### Greedy Search (GDS)

### Steepest Ascent Hill Climbing (HCS)

### A* Search

## References
1. [University of Sidney COMP3308 curiculuum](https://www.sydney.edu.au/courses/units-of-study/2020/comp/comp3308.html)
2. [UC Berkely CS188 Intro to AI slides](http://ai.berkeley.edu/lecture_slides.html)
