
/**
 * Array Heap implimentation of a priority queue
 * @author Daria Przhebelska, 300106264
 * Adapted from the solution of Lab 4
 */
public class PriorityQueue {

	//--------------------------------- Nested class ----------------------------------//

	/**
	 * Nested class which stores the employer ranking for a student
	 */
	public class EmployerRanking {
		
		//instance variables
		private int ranking;
		private int student;
	
		//constructor
		protected EmployerRanking(int ranking, int student) {
			this.ranking = ranking;
			this.student = student;
		}	
	}  

	//------------------------------ Instance variables -------------------------------//

	private EmployerRanking[] storage; //The Heap itself in array form
	private int tail; //Index of last element in the heap

	/**
	 * HeapPriorityQueue constructor with the size as input
	 * @param size - size of the queue
	 */
	public PriorityQueue(int size) {
		storage = new EmployerRanking[size];
		tail = -1;
	}

	//---------------------------- Priority Queue Methods -----------------------------//

	/**
	 * Returns the number of items in the priority queue.
	 * O(1)
	 * @return number of items
	 */
	public int size() {
		return tail + 1;
	} /* size */


	/**
	 * Tests whether the priority queue is empty.
	 * O(1)
	 * @return true if the priority queue is empty, false otherwise
	 */
	public boolean isEmpty() {
		return tail < 0;
	} /* isEmpty */


	/**
	 * Inserts a key-value pair and returns the EmployerRanking created.
	 * O(log(n))
	 * @param ranking the ranking of the new EmployerRanking
	 * @param student the associated student of the new EmployerRanking
	 * @return the EmployerRanking storing the new key-value pair
	 * @throws IllegalArgumentException if the heap is full
	 */
	public EmployerRanking insert(int ranking, int student){
		EmployerRanking e = new EmployerRanking (ranking, student);
		storage [++tail] = e;
		upHeap (tail);
		return e;
	} /* insert */


	/**
	 * Returns (but does not remove) an EmployerRanking with minimal ranking.
	 * O(1)
	 * @return EmployerRanking having a minimal ranking (or null if empty)
	 */
	public int min() {
		return storage [0].student;
	} /* min */


	/**
	 * Removes and returns an EmployerRanking with minimal ranking.
	 * O(log(n))
	 * @return the removed EmployerRanking (or null if empty)
	 */
	public int removeMin() {
		EmployerRanking tmp = storage[0];

		if(tail == 0) {
			tail = -1;
			storage[0] = null;
			return tmp.student;
		}

		storage[0] = storage [tail];
		storage[tail--] = null;

		downHeap(0);

		return tmp.student;
	} /* removeMin */

	//---------------------------- Heap Operation Methods -----------------------------//

	/**
	 * Algorithm to place element after insertion at the tail.
	 * O(log(n))
	 */
	private void upHeap(int location) {
		if(location == 0) {return;}

		int parent = parent(location);

		if(storage[parent].ranking > (storage[location].ranking)) {
			swap (location, parent);
			upHeap (parent);
		}
	} /* upHeap */


	/**
	 * Algorithm to place element after removal of root and tail element placed at root.
	 * O(log(n))
	 */
	private void downHeap(int location) {
		int left = (location * 2) + 1;
		int right = (location * 2) + 2;

		//Both children null or out of bound
		if(left > tail) {return;}

		//left in right out;
		if(left == tail) {
			if (storage[location].ranking > (storage[left].ranking))
				swap (location, left);
			return;
		}

		int toSwap = (storage[left].ranking < (storage[right].ranking)) ?
		                left : right;

		if (storage[location].ranking > (storage[toSwap].ranking)) {
			swap (location, toSwap);
			downHeap (toSwap);
		}
	} /* downHeap */

	/**
	 * Find parent of a given location,
	 * Parent of the root is the root
	 * O(1)
	 */
	private int parent(int location) {
		return (location - 1) / 2;
	} /* parent */


	/**
	 * Inplace swap of 2 elements, assumes locations are in array
	 * O(1)
	 */
	private void swap(int location1, int location2) {
		EmployerRanking temp = storage [location1];
		storage [location1] = storage [location2];
		storage [location2] = temp;
	} /* swap */


}
