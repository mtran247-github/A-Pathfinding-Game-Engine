
import java.io.FileNotFoundException;

import java.io.IOException;

/** 
 *This MazeSolver solves the maze based on priority, implementing A*
 *
 *This MazeSolver uses a LinkedPriorityQueue to enqueue hexagon tiles as PriorityNodes, in order to search for the exit, and will stop if it does or does not finds the exit 
 *
 * @author Melissa Tran
 */

public class MazeSolverToo {

	  /**
	   * This is the main method which is used to run the program 
	   * 
	   * @parameter String[]args is where the maze file is passed through
	   */
	
	public static void main(String[] args) {

		
		try {
			// if the length of the file is less than 1, then there was no file
			// if none provided, therefore must give an IllegalArgumentException
			if (args.length < 1) {
				//throws an illegal argument exception , which means it cannot take this input 
				throw new IllegalArgumentException("No maze was provided");
			}

			Maze maze = new Maze(args[0]);
			//maze represents the maze file that was passed through
			maze.setTimeDelay(50);
			Hexagon startingHexagon = maze.getStart();
			//startingHexagon represents the start tile in the maze 
			startingHexagon.setStarted();
			//will change the start tile to a light pink color 
			maze.repaint();
			//this will refresh the window so that the colour changes 
			LinkedPriorityQueue<Hexagon> linkedPriorityQueue = new LinkedPriorityQueue<Hexagon>();
			//linkedPriorityQueue represents the linked priority queue which we will store the hexagon tiles in 
			linkedPriorityQueue.enqueue(startingHexagon);
			//we will now enqueue the start tile into the priority queue
			int totalStepCounter = 0;
			//totalStepCounter = the total number of steps to dequeue and enqeue the tiles 
			int hexagonInQueue = linkedPriorityQueue.size();
			//hexagonInQueue = the number of hexagons currently in the queue 
			boolean isThereEnd = false;
			//need a boolean 
			
			while (!linkedPriorityQueue.isEmpty()) {
				Hexagon checkHex = linkedPriorityQueue.dequeue();
				//checkHex = represents the hexagon that is recently dequeued
				checkHex.setCurrent();
				//setCurrent will turn the tile yellow, which shows we are determining what kind of tile this hexagon is 
				totalStepCounter ++;
				//increase stepCounter because you dequeued
				hexagonInQueue --;
				//decrease stepCounter because you dequeued, therefore, less tiles in the stack
				
				//now check if checkHex is the start tile
				if(checkHex.isStart()){
					//if it is the start:
					checkHex.setStarted();
					//setStarted, will change this tile colour to a pale red
					maze.repaint();
				}
				//now check if the hexagon is the end tile 
				if (checkHex.isEnd()) {
					//if the hexagon is the end tile:
					checkHex.setCurrent();
					//change the color to yellow
					maze.repaint();
					//repaint so that the window refreshes
					checkHex.setFinished();
					//setFinished, will change the tile color to a darker yellow 
					isThereEnd= true;
					//if this is the end tile, we can break out of the loop
					break;	
				}
				//now check if the hexagon is not the end tile
				if (!checkHex.isEnd()) {
					//check all 5 sides of the hexagon's neighbours
					for (int side = 0; side <= 5; side++) {
						Hexagon neighbour = checkHex.getNeighbour(side);
						//neighbour = neighbour of dequeued hex
						if (neighbour == null || neighbour.isWall())
							//if the neighbour is null or is a wall, ignore it, look at the next neighbour 
							continue;
						if (neighbour.isDequeued()) {
							//if the neighbour is dequeued, then ignore it, look at the next neighbour 
							continue;
						}
						if (!neighbour.isDequeued() && !neighbour.isEnqueued()
								&& !neighbour.isWall()) {
							//if it is not dequeued and not enqueued, and is not a wall, then enqueue it into the priority queue
							//that way we are not enqueueing something we've looked at, something that is already on the list, or a wall
							//we enqueue a hexagon that does not have any of these conditions
							neighbour.setSteps(checkHex.getSteps() +1);
							//checkHex, get the hexagon you have dequeued, get the steps it takes for THAT hexagon to reach the exit, and set that number as the distance for the neighbouring hexagon
							//you add one because you must include the neighbouring hexagon in the step too
							//this now gives you the distance, of all neighbouring hexagons to the end
							// f(x)= g(x) + h(x);
							double priority = neighbour.distanceToEnd(maze)+ neighbour.getSteps();
							//priority is based on the distance from this hexagon to the end of the maze, PLUS the steps it takes for the neighbour to get there
							linkedPriorityQueue.enqueue(neighbour, priority);
							//enqueue the neighbouring hexagon into the linked list based on this priority 
							neighbour.setEnqueued();
							//change the color so that it will show blue
							maze.repaint();
							//refresh the window so that the color changes
							totalStepCounter++;
							//increment step counter because you are enqueueing 
							hexagonInQueue++;
							//increment hexagonInQueue because number of hexagons increased in the Queue
							}
						}
				if(!checkHex.isStart())
				//you know that after you dequeue the first hexagon, that the rest of the tiles are not the starting hexagons
				//therefore since this statement is true, after it finds its neighbouring hexagon and dequques another hexagon and sets to current, then that hexagon becomes setdequeued to show we are done looking at it
				//if it really was an end tile or a start tile, then it wouldn't be going through this if statement 
				checkHex.setDequeued();
					}
			}
				maze.repaint();
				//repaint just to refresh the colours for the window
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
		//exceptions to be handeled
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