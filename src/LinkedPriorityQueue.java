/**
 * This class, LinkedPriorityQueue, is the data structure class which will store each hexagon in the maze as nodes. 
 * 
 * This class implements PriorityQueueADT which overrides the methods in that interface
 * 
 * This class is the only class that needs to access the PriorityNode class because it is the data structure class
 * 
 * A linked priority queue is basically a regular queue, although the nodes will be enqueued based on the priority we set it to
 * 
 * You can do certain things with the LinkedPriorityQueue using the following methods, LinkedPriorityQueue, enqueue, enqueue(overloaded), dequeue, first, isEmpty, size
 *
 * @author Melissa Tran
 */

public class LinkedPriorityQueue<T> implements PriorityQueueADT<T> {
	/**
	 * head - represents a pointer to the head node(first) in the LinkedPriorityQueue
	 */
	private PriorityNode<T> head;
	/**
	 *tail - represents a pointer to the tail node(last) in the LinkedPriorityQueue
	 */
	private PriorityNode<T> tail;
	/**
	 * size - int value which represents the size of the LinkedPriorityQueue
	 */
	int size;
	

	/**
	 * constructor method - creates an empty LinkedPriorityQueue
	 *
	 */
	public LinkedPriorityQueue() {

		head = null;
		tail = null;
		size = 0;
	}

	
	/**
	 * This method adds an element to the end of the queue
	 * 
	 * @param: element is the element of the node which you want to add in 
	 */
	@Override
	public void enqueue(T element) {
		PriorityNode<T> newNode = new PriorityNode<T>(element);
		//newNode represents the node we want to add to the end of the queue
		//if the size of the queue is zero, make head and tail point to this newNode
		if (size == 0) {
			head = newNode;
			tail = newNode;
		}
		//else, there are things in the queue, so you must enqueue behind the
		else {
			PriorityNode<T> pointer2 = head;
			PriorityNode<T> pointer1 = null;
			
			tail.setNext(newNode);
			//set the next thing the newNode points to to be tail, which is null
			tail = newNode;
				}
		size++;
	}

	/**
	 * This methods overloads the previous enqueue method and adds the element into the queue based on priority 
	 * the method takes in a variable of type T called element2(in the main method it is checkHex) and also a double called p, which represents its priority
	 * 
	 * @param: element2 This represents the element of the node which we would like to add into the queue
	 * @param: priority of the element we want to add in, which will determine where it will be inserted into the queue
	 * the priority will be from smallest to greatest because we want the shortest distance to the exit tile
	 */
	@Override
	public void enqueue(T element2, double p) {
		PriorityNode<T> newNode = new PriorityNode<T>(element2, p);
		//creating a new PriorityNode called newNode
		//if the queue is size 0
		if (size == 0) {
			head = newNode;
			tail = newNode;
			size++;
			//point the head and tail to this newNode and increment the size
		} else {
			//else the queue is more than size 0
			boolean insertMiddle = false;
			//if the queue is more than 0, you have to either 1.insert in the middle, 2.before the first node, or 3.after the first node
			//must have a boolean to know if we must insert the node in the middle or not 
			PriorityNode<T> pointer2 = head;
			//head points to the first node, but here we make a copy of the head reference called pointer2
			PriorityNode<T> pointer1 = null;
			//pointer1 will point to null because it has to go before pointer2
			while (pointer2 != null && pointer2.getPriority() <= p) {
				//we will insert the node if pointer2 does not equal null and the priority in the queue is less than or equal to the priority we are adding in
				insertMiddle = true;
				pointer1 = pointer2;
				//pointer1 now equals whatever node pointer2 is at
				pointer2 = pointer2.getNext();
				//pointer2 is now pointing to whatever node is beside pointer1
			}
			//if the boolean is still false, it means theres only 1 node in the queue. You have two options:
				if (!insertMiddle) {
				//means that all the priority in the nodes is more than priority of the node we are adding in, therefore this priority goes in the front
					//1. insert before first node - it is smaller
					newNode.setNext(head);
					//set the next thing head points to, to be this newNode
					head =newNode;
					//the head is now newNode
					size++;
					//increment the size of queue, because we just added a new node
			} 	else {
					//means that it did go into the while loop and boolean is true 
					//means that pointer2 ==null, but we didn't find a priority in the queue which was less than or equal to the priority we are adding in, therefore we add this priority to the end
					//2. insert after the node with a bigger priority 
					pointer1.setNext(newNode);
					//next thing newNode will point to be whatever pointer 1 is pointing to 
					newNode.setNext(pointer2);
					//set the next thing pointer2(head) point to, to be the newNode
					size++;
					//increment the size of queue because we just added a new node
				}
			}
	}
	
	/**
	 * This method allows us to dequeue from the front of the queue, if there are no nodes, it will throw an EmptyCollectionException
	 * 
	 * @return: newNode.getElement - return the node which was removed successfully from the list.
	 */
	@Override
	public T dequeue() throws EmptyCollectionException {
		PriorityNode<T> newNode = head;
		//newNode is the first element in the queue
		if(head == null){
			throw new EmptyCollectionException("priority queue");
			//if the head is null, means the queue is empty, throw an empty collection exception
		}
		if(head!=null){
		//if head is not null, means there is something in it
		PriorityNode<T> next = head.getNext(); 
		//next is the node after the first node, so the second node
		head = next;
		//head now points to whatever next is pointing to 
		size --;
		//decrease the size, because we just removed the node
		
			}
			return newNode.getElement();
			//return the node which we just removed using the getElement method
		
		}

	/**
	 * This method will give us the first element in the queue, a peek() version of a queue
	 * 
	 * @return: first element in the LinkedPriorityQueue
	 */
	@Override
	public T first() {
		if (size == 0)
			//if the size is zero, there is nothing to look at, so throw an empty collection exception
			throw new EmptyCollectionException("priority queue");
		//if not, return the head, which is the first node in the queue, using getElement() method 
		return head.getElement();
	}

	/**
	 * This method is used to check if the queue is empty or not
	 * 
	 * @return: boolean true if the LinkedPriorityQueue is empty, false if the LinkedPriorityQueue is not 
	 */
	@Override
	public boolean isEmpty() {
		if(size == 0){
			//if the size variable is equal to 0, return true. the queue is empty. 
			return true; 
			//if not, then the queue is not empty. return false. 
		}else{
			return false;
		}
	}

	/**
	 * This method will return the size of the queue, using the instance variables, which kept track of the # of nodes in the queue
	 * 
	 * @return size: size of the LinkedPriorityQueue
	 */
	@Override
	public int size() {
		return this.size;
	}

}
