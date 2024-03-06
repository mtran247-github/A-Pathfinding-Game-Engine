import java.awt.Color;

/**
 * - This class represents a pointed-top Hexagon tile used to make up a Maze object.
 * - Each tile has a type.  It can be a Wall, Start, Enqueued, Dequeued, backtracker, and a current hex.
 * - Each Hex has a stepsToMe value which tracks how many steps it took to get from the start to this hex. The default value is -1.
 * - Hexagon tiles know about their neighbors (if set using setNeighbor method).
 * - The neighbors of a tile are accessed by an index 0-5 inclusive.
 * 
 * - The hexagons are pointed-top in orientation, the 0 index is the upper-right side
 * - Indexes for the sides progress incrementally clockwise from the 0 index, to 5 on the upper-left side
 * 
 * Eg.
 *    5 /  \ 0
 *    4 |  | 1
 *    3 \  / 2
 *
 * @author Melissa Tran
 */
public class Hexagon extends HexComponent
{
	// constants
	private static final Color WALL_COLOR = Color.BLACK;
	private static final Color START_COLOR = Color.GREEN;
	private static final Color END_COLOR = Color.RED;
	private static final Color UNVISITED_COLOR = Color.CYAN;
	private static final Color ENQUEUED_COLOR = Color.BLUE;
	private static final Color END_PROCESSED_COLOR = Color.ORANGE;
	private static final Color START_PROCESSED_COLOR = Color.PINK;
	private static final Color DEQUEUED_COLOR = Color.LIGHT_GRAY;
	private static final Color BACKTRACK_COLOR = Color.MAGENTA;
	private static final Color CURRENT_COLOR = Color.YELLOW;

	//enum to represent available hexagon types
	public static enum HexType{WALL, START, END, UNVISITED, ENQUEUED, END_PROCESSED, START_PROCESSED, DEQUEUED, BACKTRACK, CURRENT};
	
	
	// Attributes	
	private HexType type;    // Stores the type of Hexagon this currently is  
	private boolean isStart; 
	private boolean isEnd;   
	private Hexagon[] neighbors; // Stores the hexagons which surround this one on each of 6 sides
	private double stepsToMe;	// Number of steps it takes to get to this hexagon

	

	//Create a Hexagon tile of the specified type 
	public Hexagon(HexType t){
		this.type = t;
		this.isStart = t==HexType.START;
		this.isEnd = t==HexType.END;
		
		//set the initial color based on the initial type
		this.setColor(this.type);
		this.neighbors = new Hexagon[6];
		this.stepsToMe = -1;	
	}

	/**
	 * neighbor The new Hexagon neighbor
	 * i The index specifying which side this neighbor is on (0-5 inclusive)
	 * @InvalidNeighborIndexException When an index is specified that is not 0-5 inclusive.
	 */
	public void setNeighbour(Hexagon neighbor, int i) throws InvalidNeighbourIndexException{
		if (0<=i && i <=5)
			this.neighbors[i] = neighbor;
		else 
			throw new InvalidNeighbourIndexException(i);
	}
	
	/**
	 * Returns the neighbor for this hexagon using the neighbor index
	 * The index for the neighbor indicates which side of the hexagon
	 * the neighbor to get is on.  0-5 inclusive.
	 */
	public Hexagon getNeighbour(int i) throws InvalidNeighbourIndexException{
		if (0<=i && i <=5)
			return this.neighbors[i];
		else 
			throw new InvalidNeighbourIndexException(i);
	}
	
	/**
	 * Returns the predicted distance from this hex to the end of the maze. The predicted distance is simply the Euclidean distance. 
	 * If there is no end to the maze then a distance of 0 is returned
	 */
	public double distanceToEnd(Maze m)
	{
		try
		{
			return Math.sqrt(Math.pow(((double)this.getLocation().x/25.0 - (double)m.getEnd().getLocation().x/25.0), 2) + Math.pow(((double)this.getLocation().y/25.0 - (double)m.getEnd().getLocation().y/25.0), 2));
		}
		catch(NullPointerException e)
		{
			return 0.0;
		}
	}


	//Checks if the current hexagon is a Wall tile.
	public boolean isWall(){
		return type == HexType.WALL;
	}

	//Checks if the current hexagon is currently in the queue
	public boolean isEnqueued()
	{
		return type == HexType.ENQUEUED || type == HexType.START_PROCESSED;
	}
	

	//This method checks if the current hexagon was dequeued from the stack.
	public boolean isDequeued()
	{
		return type == HexType.DEQUEUED;
	}
	
	// This method checks if the current hexagon is a Start tile.
	public boolean isStart(){
		return this.isStart;
	}

	//This method checks if the current hexagon is an End tile.
	public boolean isEnd(){
		return this.isEnd;
	}


	//Sets the tile to be a enqueued tile and updates the tile's colour
	public void setEnqueued(){
		this.type = HexType.ENQUEUED;
		this.setColor(this.type);
	}
	

	//This method sets the tile to be a dequeued tile and updates the tile's colour
	public void setDequeued(){
		this.type = HexType.DEQUEUED;
		this.setColor(this.type);
	}
	

	 //This method sets the tile to be a processed finish tile and updates the tile's colour
	public void setFinished(){
		this.type = HexType.END_PROCESSED;
		this.setColor(this.type);
	}
	

	 //This method sets the tile to be a processed start tile and updates the tile's colour
	public void setStarted(){
		this.type = HexType.START_PROCESSED;
		this.setColor(this.type);
	}


	 //This method sets the tile to be the current hex being looked at and updates the tile's colour
	public void setCurrent(){
		this.type = HexType.CURRENT;
		this.setColor(this.type);
	}
	
	//This method sets the tile to be in the backtracking path and updates the tile's colour
	public void setBacktrack(){
		this.type = HexType.BACKTRACK;
		this.setColor(this.type);
	}
	

	//This method gets the number of steps it took to get to this hex
	public double getSteps(){
		return stepsToMe;
	}
	

	//This method sets the number of steps it takes to get to this hex
	public void setSteps(double s){
		stepsToMe = s;
	}
	

	//Helper method to set the current tile color based on the type of tile.
	private void setColor(HexType t){
		switch(t){
		case WALL:
			this.setBackground(WALL_COLOR);
			break;
		case START:
			this.setBackground(START_COLOR);
			break;
		case END:
			this.setBackground(END_COLOR);
			break;
		case UNVISITED:
			this.setBackground(UNVISITED_COLOR);
			break;
		case ENQUEUED:
			this.setBackground(ENQUEUED_COLOR);
			break;
		case END_PROCESSED:
			this.setBackground(END_PROCESSED_COLOR);
			break;
		case START_PROCESSED:
			this.setBackground(START_PROCESSED_COLOR);
			break;
		case DEQUEUED:
			this.setBackground(DEQUEUED_COLOR);
			break;
		case BACKTRACK:
			this.setBackground(BACKTRACK_COLOR);
			break;
		case CURRENT:
			this.setBackground(CURRENT_COLOR);
			break;
		default:
			this.setBackground(WALL_COLOR);
			break;
		}
		this.setForeground(Color.BLACK);
	}

}