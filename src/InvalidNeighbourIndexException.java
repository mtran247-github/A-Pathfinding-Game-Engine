/**
 * - Thrown when access is attempted outside valid hexagon neighbor index range
 * - Since it is a hexagon, can only have 6 neighbors (0-5 inclusive)
 *
 * @author Melissa Tran
 */
public class InvalidNeighbourIndexException extends ArrayIndexOutOfBoundsException{

	public InvalidNeighbourIndexException(int i){
		super("Invalid index for hexagon neighbor:" + i + "  Must be 0-5 inclusive");
	}
}
