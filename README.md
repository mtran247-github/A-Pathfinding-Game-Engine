# A* Pathfinding Game Engine

This project is an implementation of an A* algorithm-based pathfinding game engine, designed to efficiently solve mazes. The A* algorithm, an extension of Dijkstra's algorithm, intelligently navigates through a maze using priority nodes, resulting in optimal pathfinding.

## Setup Instructions

1. **Download the Code**: Clone the repository to your local machine.
2. **Compile the Code**: Compile the Java files using your preferred Java compiler.
3. **Run the Program**: Execute the compiled `MazeSolverToo` class with the following command in the terminal: `java MazeSolverToo maze1.txt`.
   - On Eclipse: Run"->"Run Configurations
   - Make sure"Java Application->MazeSolverToo" is the active selection on the left-hand side
   - Select Arguments tab
   - Enter the filename and location in the "Program arguments" text box Ex: /Users/SomeName/Desktop/A-Pathfinding-Game-Engine/maze3.txt".
   - Replace `maze1.txt` with the filename of the maze you want to solve.
5. **View Results**: The program will display the maze with colored tiles representing the progress of the pathfinding algorithm.
6. **Interact with the Maze**: Observe the steps taken, the number of tiles remaining in the queue, and the total number of steps. 

## Functional Specifics

This project leverages Java classes such as `Maze`, `Hexagon`, `PriorityNode`, and `LinkedPriorityQueue` to implement the A* algorithm. The provided `Maze` class represents the maze structure, while `Hexagon` encapsulates individual tiles. `PriorityNode` facilitates priority-based queue operations, and `LinkedPriorityQueue` is used for managing nodes.

## High Level Algorithm

1. Initialize the maze object and retrieve the start hexagon.
2. Enqueue the start hexagon and proceed with the following steps until the queue is empty:
   - Dequeue a hexagon.
   - Check if it's the end tile.
   - Enqueue neighboring hexagons based on the priority function.
   - Update the maze window with the current progress.
3. Upon completing the search, print relevant statistics including the end status, steps taken, remaining tiles in the queue, and total steps.

