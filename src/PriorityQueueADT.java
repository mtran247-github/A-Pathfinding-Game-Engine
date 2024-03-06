/**
 * - QueueADT defines the interface to a queue collection
 *
 * @author Melissa Tran
 */

public interface PriorityQueueADT<T>
{

   public void enqueue (T element);

   public void enqueue (T element, double p);
   

   public T dequeue();

   public T first();
   

   public boolean isEmpty();


   public int size();
   
   
   public String toString();
}
