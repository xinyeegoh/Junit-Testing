package sqa_cw;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//JUNIT VERSION: JUNIT 5
//Please make sure the java project is added with JUnit 5 jar 
//Steps: 
//		select project -> build path -> add libraries -> junit -> "Next" -> select junit 5 -> "Finish"

class BoundedQueueTest {

	BoundedQueue boundedqueue;
	
	
	@BeforeEach
	/* 
	 * create a bounded queue with a capacity of 3
	 */
	void setUp() throws Exception {
		boundedqueue=new BoundedQueue(3);
	}
	

	
	@Test
	/*
	 * test BoundedQueue constructor 
	 * throw exception when trying to create a bounded queue with invalid capacity
	 *  
	 */
	void testBoundedQueue_IllegalArgumentException() {
				
		//test throw exception
		IllegalArgumentException exception = assertThrows(
			    IllegalArgumentException.class, 
			    () -> { new BoundedQueue(-4); } // capacity = -4
			  );
		
		//test whether the exception message thrown is correct
		String message = "BoundedQueue.constructor";
		assertEquals(message, exception.getMessage());
		
	}

	
	
	@Test
	/*
	 * test enQueue (Object o)  
	 * throw exception when trying to enqueue a null object
	 */
	void testEnQueue_NullPointerException() {
		
		//throws exception
		NullPointerException exception_nullpointer = assertThrows(
			    NullPointerException.class, 
			    () -> { boundedqueue.enQueue(null); } 
			  );
		

		//check whether the exception message thrown is correct
		String message = "BoundedQueue.enQueue";
		assertEquals(message, exception_nullpointer.getMessage());

	}

	

	/*
	 * test enQueue
	 * throw exception when trying to enqueue another element when the queue is full
	 * this also tests that enQueue is working
	 */
	@Test
	void testEnQueue_IllegalStateException() {
		
		//enqueue 3 elements so that the bounded queue is full
		boundedqueue.enQueue(1);
		boundedqueue.enQueue(2);
		boundedqueue.enQueue(3);
		
		
		//test throw exception 
		IllegalStateException exception_illegalstate = assertThrows(
				IllegalStateException.class, 
				() -> { boundedqueue.enQueue(4); } //enqueue element "4"
			);
				
		//test whether the exception message thrown is correct
		String message = "BoundedQueue.enQueue";		
		assertEquals(message, exception_illegalstate.getMessage());
				
	}
	
	@Test
	/*
	 * test deQueue
	 * always dequeue in a First in First Out (FIFO) behavior
	 * will return the front object in the queue during each dequeue
	 */
	void testDeQueue() {
		
		//initialize two sample objects
		Object o1 = new Object();
		Object o2 = new Object();
		
		//enqueue o1, then o2
		boundedqueue.enQueue(o1);
		boundedqueue.enQueue(o2);
		
		//dequeue o1, then o2
		assertEquals(o1,boundedqueue.deQueue(),"FIFO");
		assertEquals(o2,boundedqueue.deQueue(),"FIFO");

	}
	
	
	@Test
	/*
	 * test deQueue
	 * throw exception when trying to dequeue an empty queue
	 */
	void testDeQueue_IllegalStateException() {
		
		//test throw exception
		IllegalStateException exception_illegalstate = assertThrows(
				IllegalStateException.class, 
				() -> { new BoundedQueue(2).deQueue(); } //create a new BoundedQueue object and call deQueue
			);
				
		//test whether the exception message thrown is correct
		String message = "BoundedQueue.deQueue";
		assertEquals(message, exception_illegalstate.getMessage());
		
	}
	
	
	@Test
	/*
	 * test queue -> if is full, return true
	 */
	void testIsFull_true() {
		
		//enqueue the queue to full
		boundedqueue.enQueue(1);
		boundedqueue.enQueue(2);
		boundedqueue.enQueue(3);
		assertTrue(boundedqueue.isFull(),"Queue is full");
		
		
		//dequeue one element and enqueue a new one, so the queue is still full
		boundedqueue.deQueue();
		boundedqueue.enQueue(6);
		assertTrue(boundedqueue.isFull(),"Queue is full");
		
		
	}
	
	@Test
	/*
	 * test queue -> if not full, return false
	 */
	void testIsFull_false() {
		
		//test on original bounded queue
		assertFalse(boundedqueue.isFull(),"Queue is not full");
		
		//enqueue one element to the queue and test
		boundedqueue.enQueue(1);
		assertFalse(boundedqueue.isFull(),"Queue is not full");
		
		//test on new bounded queue
		assertFalse(new BoundedQueue(4).isFull(),"Queue is not full");
				
	}
	
	
	@Test
	/*
	 * test queue -> if is empty, return true 
	 */
	void testIsEmpty_true() {
		
		
		//test on original bounded queue
		assertTrue(boundedqueue.isEmpty(),"Queue is empty");
		
		//enqueue one element then dequeued it, so the queue is still empty
		boundedqueue.enQueue(1);
		boundedqueue.deQueue();
		assertTrue(boundedqueue.isEmpty(),"Queue is empty");
		
		//test on new queue
		assertTrue(new BoundedQueue(8).isEmpty(),"Queue is empty");
		
	}
	
	
	@Test
	/*
	 * test queue -> if not empty, return false
	 */
	void testIsEmpty_false() {
		
		//enqueue one element and test
		boundedqueue.enQueue(2);
		assertFalse(boundedqueue.isEmpty(),"Queue is not empty");	
		
	}
		

	
	@Test
	/*
	 * test toString()
	 * returns the content of the queue in a string format
	 * Example: [xxx, xxx, xxx, ...]
	 */
	void testToString() {
		
		boundedqueue.enQueue(1);
		boundedqueue.enQueue(2);
		boundedqueue.enQueue(3);
		
		assertEquals("[1, 2, 3]",boundedqueue.toString());
		
		boundedqueue.deQueue();
		boundedqueue.deQueue();
		
		assertEquals("[3]",boundedqueue.toString());
		
	}
	

	@Test
	/*
	 * test enqueue and dequeue are working properly in execution
	 * even when mixed in order
	 * this also further tests toString()
	 */
	void testEnQueue_DeQueue() {
		
		boundedqueue.enQueue(2);
		boundedqueue.enQueue(7);
		boundedqueue.enQueue(9);
		boundedqueue.deQueue(); //dequeue 1 element
		boundedqueue.enQueue(4);
		
		assertEquals("[7, 9, 4]",boundedqueue.toString());
		
		boundedqueue.deQueue(); //dequeue 1 element
		boundedqueue.deQueue(); //dequeue 1 element
		boundedqueue.enQueue(5);
		
		assertEquals("[4, 5]", boundedqueue.toString());
		
	}
	

}
