
import java.io.FileNotFoundException;

import java.io.IOException;

/** 
 * - This program represents a maze solver using a priority queue
 * - It initializes a maze, enqueues the starting hexagon, and solves the maze
 * - The main method initializes various counters and flags 
 * 
 * @author Melissa Tran
 */

public class MazeSolverToo {


	//This is the main method which is used to run the program 
	public static void main(String[] args) {
		try {
			// if the length of the file is less than 1, then there was no file
			// if none provided, therefore must give an IllegalArgumentException
			if (args.length < 1) {
				throw new IllegalArgumentException("No maze was provided");
			}

			
			/**
			 * Initialize the maze and setup variables
			 * maze: Represents the maze file that was passed through
			 * startingHexagon: Represents the start tile in the maze
			 * linkedPriorityQueue: Represents the linked priority queue to store the hexagon tiles
			 * totalStepCounter: The total number of steps to dequeue and enqueue the tiles
			 * hexagonInQueue: The number of hexagons currently in the queue
			 * isThereEnd: Flag to check if there's an end
			 */
			Maze maze = new Maze(args[0]);
			maze.setTimeDelay(50);
			Hexagon startingHexagon = maze.getStart();
			startingHexagon.setStarted(); 
			maze.repaint();
			LinkedPriorityQueue<Hexagon> linkedPriorityQueue = new LinkedPriorityQueue<Hexagon>();
			linkedPriorityQueue.enqueue(startingHexagon);
			int totalStepCounter = 0;
			int hexagonInQueue = linkedPriorityQueue.size(); 
			boolean isThereEnd = false;
			
			
			/**
			 * Explore the maze to find the end tile
			 * checkHex: Represents the hexagon that is recently dequeued
			 * totalStepCounter: Tracks the total number of steps
			 * hexagonInQueue: Tracks the number of hexagons in the queue
			 * isThereEnd: Flag to indicate if there's an end tile
			 */

			// Explore the maze until the end tile is found
			
			while (!linkedPriorityQueue.isEmpty()) {
				Hexagon checkHex = linkedPriorityQueue.dequeue();
				checkHex.setCurrent();
				totalStepCounter ++;
				hexagonInQueue --;
				
				if(checkHex.isStart()){
					checkHex.setStarted();
					maze.repaint();
				}
				
				//now check if the hexagon is the end tile 
				if (checkHex.isEnd()) {
					checkHex.setCurrent();
					//change the color to yellow
					maze.repaint();
					checkHex.setFinished();
					//setFinished, will change the tile color to a darker yellow 
					isThereEnd= true;
					break;	
				}
				
				
				//now check if the hexagon is not the end tile
				if (!checkHex.isEnd()) {
					for (int side = 0; side <= 5; side++) {
						Hexagon neighbour = checkHex.getNeighbour(side);
						if (neighbour == null || neighbour.isWall())
							continue;
						if (neighbour.isDequeued()) {
							continue;
						}
						if (!neighbour.isDequeued() && !neighbour.isEnqueued()
								&& !neighbour.isWall()) {
							neighbour.setSteps(checkHex.getSteps() +1);
							//checkHex, get the hexagon you have dequeued, get the steps it takes for THAT hexagon to reach the exit, and set that number as the distance for the neighbouring hexagon
							//you add one because you must include the neighbouring hexagon in the step too
							//this now gives you the distance, of all neighbouring hexagons to the end
							// f(x)= g(x) + h(x);
							
							double priority = neighbour.distanceToEnd(maze)+ neighbour.getSteps();
							//priority is based on the distance from this hexagon to the end of the maze, PLUS the steps it takes for the neighbour to get there
							linkedPriorityQueue.enqueue(neighbour, priority);
							neighbour.setEnqueued();
							maze.repaint();
							totalStepCounter++;
							hexagonInQueue++;
							}
						}
					
					
				/**
				 * you know that after you dequeue the first hexagon, that the rest of the tiles are not the starting hexagons
				 * therefore since this statement is true, after it finds its neighbouring hexagon and dequques another hexagon and sets to current, then that hexagon becomes setdequeued to show we are done looking at it
				 * if it really was an end tile or a start tile, then it wouldn't be going through this if statement 
				 */
				if(!checkHex.isStart())
				checkHex.setDequeued();
					}
				}
				maze.repaint();
				
				
				
			//repaint just to refresh the colors for the window
			//if the boolean is true, it means that checkHex was an end tile, and we have found the end
			if(isThereEnd==true){
				System.out.println("The end was found \n");
				System.out.println("Number of steps to get to finish: "+ (maze.getEnd().getSteps()+1) + "\n");
				//end tiles stores the length of the shortest path
				System.out.println("Hexagons in priority queue: " + hexagonInQueue + "\n");
				System.out.println("Total number of steps taken: " + totalStepCounter + "\n");
				
			}else{
				//the boolean remains false, therefore it never went into the the "if checkHex isEnd" statement, and therefore exit was never found
				System.out.println("The end was not found");
				System.out.println("Number of steps to get to finish: " + "\n");
				System.out.println("Hexagons in priority queue: " + hexagonInQueue + "\n");
				System.out.println("Total number of steps taken:" + totalStepCounter + "\n");
				}
			
			
			
		//exceptions to be handled
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			;
		} catch(UnknownMazeCharacterException e){
			System.out.println(e.getMessage());
			
		} catch (InvalidNeighbourIndexException e){
			System.out.println(e.getMessage());
			
		} catch (EmptyCollectionException e){
			System.out.println(e.getMessage());
		}		
			
		}					
	}