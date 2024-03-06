/**
 * - This class, LinkedPriorityQueue, is the data structure class which will store each hexagon in the maze as nodes. 
 * - This class implements PriorityQueueADT which overrides the methods in that interface
 * - This class is the only class that needs to access the PriorityNode class because it is the data structure class
 * - A linked priority queue is basically a queue, where the nodes will be enqueued based on the priority we set it to
 * - You can do certain things with the LinkedPriorityQueue using the following methods, LinkedPriorityQueue, enqueue, enqueue(overloaded), dequeue, first, isEmpty, size
 *
 * @author Melissa Tran
 */

public class LinkedPriorityQueue<T> implements PriorityQueueADT<T> {
	private PriorityNode<T> head;
	private PriorityNode<T> tail;
	int size;
	

	//constructor method - creates an empty LinkedPriorityQueue
	public LinkedPriorityQueue() {
		head = null;
		tail = null;
		size = 0;
	}

	
	/**
	 * - This method adds an element to the end of the queue
	 * - newNode represents the node we want to add to the end of the queue
	 * - if the size of the queue is zero, make head and tail point to this newNode
	 */
	public void enqueue(T element) {
		PriorityNode<T> newNode = new PriorityNode<T>(element);
		if (size == 0) {
			head = newNode;
			tail = newNode;
		}
		
		//else, there are things in the queue, so you must enqueue
		//set the next thing the newNode points to to be tail, which is null
		else {
			PriorityNode<T> pointer2 = head;
			PriorityNode<T> pointer1 = null;
			
			tail.setNext(newNode);
			tail = newNode;
				}
		size++;
	}

	
	
	/**
	 * This methods overloads the previous enqueue method and adds the element into the queue based on priority 
	 * The method takes in a variable of type T called element2(in the main method it is checkHex) and also a double called p, which represents its priority
	 * @param: element2 This represents the element of the node which we would like to add into the queue
	 * @param: priority of the element we want to add in, which will determine where it will be inserted into the queue
	 * the priority will be from smallest to greatest because we want the shortest distance to the exit tile
	 * 
	 * Explore the maze to find the end tile
	 * checkHex: Represents the hexagon that is recently dequeued
	 * totalStepCounter: Tracks the total number of steps
	 * hexagonInQueue: Tracks the number of hexagons in the queue
	 * isThereEnd: Flag to indicate if there's an end tile
	 */
	
	public void enqueue(T element2, double p) {
		PriorityNode<T> newNode = new PriorityNode<T>(element2, p);
		//creating a new PriorityNode called newNode
		if (size == 0) {
			head = newNode;
			tail = newNode;
			size++;

		}else{
			//else the queue is more than size 0
			boolean insertMiddle = false;
			PriorityNode<T> pointer2 = head;
			PriorityNode<T> pointer1 = null;
			while (pointer2 != null && pointer2.getPriority() <= p) {
				insertMiddle = true;
				pointer1 = pointer2;
				//pointer1 now equals whatever node pointer2 is at
				pointer2 = pointer2.getNext();
				//pointer2 is now pointing to whatever node is beside pointer1
			}
					
				//if the boolean is still false, it means theres only 1 node in the queue. You have two options:
				if (!insertMiddle) {
					//1. insert before first node - it is smaller
					newNode.setNext(head);
					head =newNode;
					size++;
				}else{
					//2. insert after the node with a bigger priority 
					pointer1.setNext(newNode);
					newNode.setNext(pointer2);
					size++;

				}
			}
	}
	

	//This method allows us to dequeue from the front of the queue if there are no nodes it will throw an EmptyCollectionException
	public T dequeue() throws EmptyCollectionException {
		PriorityNode<T> newNode = head;
		if(head == null){
			throw new EmptyCollectionException("priority queue");
		}
		if(head!=null){
		PriorityNode<T> next = head.getNext(); 
		head = next;
		size --;
			}
			return newNode.getElement();
		
		}


	//This method will give us the first element in the queue, a peek() version of a queue
	public T first() {
		if (size == 0)
			//if the size is zero, there is nothing to look at, so throw an empty collection exception
			throw new EmptyCollectionException("priority queue");
		//if not, return the head, which is the first node in the queue, using getElement() method 
		return head.getElement();
	}

	//This method is used to check if the queue is empty or not
	public boolean isEmpty() {
		if(size == 0){
			return true; 
		//if not, then the queue is not empty. return false. 
		}else{
			return false;
		}
	}

	//This method will return the size of the queue, using the instance variables, which kept track of the # of nodes in the queue
	public int size() {
		return this.size;
	}

}
