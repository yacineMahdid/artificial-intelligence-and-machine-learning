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

![States Graph](https://github.com/yacineMahdid/artificial-intelligence-from-scratch/blob/master/AI%20Search%20Algorithm%20in%20Java/.figures/pacman_states_graph.png)

Search trees are similar in the sense that the nodes are state, but they are different in the sense that they codify the plan to go from one node to a goal node. The tree is constructed as the agent interact with the world to fullfill its purpose and different algorithm will make use of this search tree differently.
See below an example of a search tree for a small game of pacman:

![States Tree](https://github.com/yacineMahdid/artificial-intelligence-from-scratch/blob/master/AI%20Search%20Algorithm%20in%20Java/.figures/pacman_states_tree.png)

For more information I would highly suggest the slides from the CS188 Intro to AI **[2]** from [UC Berkeley](https://www.berkeley.edu/)

## Uninformed Search Algorithms
Uninformed search algorithm are simple algorithm that build and parse a search tree to go from one start node to a goal node. They are called uninformed because they do not make use of an heuristic function that tells them how well the move they are taking is. They blindly follow their algorithm until they hit the goal node, get stuck (for some) or run out of time.

All images in this section are taken from this deck of slides **[3]** from CS188 at UC Berkeley.
### Breadth First Search (BFS)
![BFS](https://github.com/yacineMahdid/artificial-intelligence-from-scratch/blob/master/AI%20Search%20Algorithm%20in%20Java/.figures/bfs.png)

### Depth First Search (DFS)
![DFS](https://github.com/yacineMahdid/artificial-intelligence-from-scratch/blob/master/AI%20Search%20Algorithm%20in%20Java/.figures/dfs.png)

### Iterative Deepening Search (IDS)
![IDS](https://github.com/yacineMahdid/artificial-intelligence-from-scratch/blob/master/AI%20Search%20Algorithm%20in%20Java/.figures/ids.png)

## Informed Search Algorithms
Informed search algorithm are a family of algorithm that builds upon the uninformed ones. The difference between the two is that this family makes use of various way of defining how good a move will be. They can make use of heuristic functions tailored for a specific task that will tell them how well a move is compared to another one. They can also have costs associated to making a move which can be used differently by different algorithm.

All images in this section are taken from this deck of slides **[4]** from CS188 at UC Berkeley and the Steepest Ascent Hill Climbing was taken from this website **[5]**.

### Greedy Search (GDS)
![Greedy](https://github.com/yacineMahdid/artificial-intelligence-from-scratch/blob/master/AI%20Search%20Algorithm%20in%20Java/.figures/greedy.png)

### Steepest Ascent Hill Climbing (HCS)
![Steepest Ascent Hill Climbing](https://github.com/yacineMahdid/artificial-intelligence-from-scratch/blob/master/AI%20Search%20Algorithm%20in%20Java/.figures/steepest.png)

### A* Search
![A* Search](https://github.com/yacineMahdid/artificial-intelligence-from-scratch/blob/master/AI%20Search%20Algorithm%20in%20Java/.figures/a_start.png)

## References
1. [University of Sidney COMP3308 Curiculuum](https://www.sydney.edu.au/courses/units-of-study/2020/comp/comp3308.html)
2. [UC Berkely CS188 Intro to AI Slides](http://ai.berkeley.edu/lecture_slides.html)
3. [Uninformed Search Algorithm Slides](ai.berkeley.edu/slides/Lecture%202%20--%20Uninformed%20Search/SP14%20CS188%20Lecture%202%20--%20Uninformed%20Search.pptx)
4. [Informed Search Algorithm Slides](ai.berkeley.edu/slides/Lecture%203%20--%20Informed%20Search/SP14%20CS188%20Lecture%203%20--%20Informed%20Search.pptx)
5. [Steepest Ascent Hill Climbing Figure](https://www.javatpoint.com/hill-climbing-algorithm-in-ai)
