import java.util.LinkedList;
import java.util.Queue;

/**
 * A bathroom shared by a group of people, represented by concurrent threads.
 * The methods for entering and exiting from the bathroom run in mutual exclusion.
 */
public class Bathroom {
	/** Bathroom's capacity */
	private int capacity;

	/** Buffer representing the bathroom */
	private Queue<String> buffer;

	/** Sex of the people currently in the bathroom. N if there is none */
	private char currentSex = 'N';

	/**
	 * Parameterized constructor
 	 * @param capacity Buffer's capacity
	 */
	public Bathroom(int capacity) {
		this.capacity = capacity;
		buffer = new LinkedList<String>();
	}


	/**
	 * The current person tries to enter the bathroom.
	 * If it's full, or people of other sex is currently using it, the thread is suspended.
	 * Otherwise, the person gets to enter. It will notify one of the people waiting, then.
	 * @param person The person entering the bathroom.
	 */
	public synchronized void enter(Person person) {
		while (buffer.size() == capacity || (currentSex != 'N' && person.getSex() != this.currentSex)) {
			if (buffer.size() == capacity) System.out.print("The bathroom is full. ");
			else if (currentSex != 'N' && person.getSex() != this.currentSex) System.out.print("People of the other sex are using the bathroom now. ");
			System.out.println(person.getName() + " will have to wait.\n");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if (currentSex == 'N') currentSex = person.getSex();
		buffer.add(person.getName());
		System.out.println(person.getName() + " entered the bathroom.");
		System.out.println("People currently in: ");
		for (String i: buffer) System.out.println(i);
		notify();
	}

	/**
	 * The person will exit the bathroom, notifying one of the other people waiting to enter.
	 * If the bathroom empties, people of any sex can now enter.
	 * @param person The person exiting the bathroom.
 	 */
	public synchronized void exit(Person person) {
		try {
			Person.sleep(person.getDuration());
		} catch (InterruptedException e) {
			e.getMessage();
		}
		
		buffer.remove(person.getName());
		System.out.println(person.getName() + " went out the bathroom.");
		if (buffer.size() > 0) {
			System.out.println("People currently in: ");
			for (String i: buffer) System.out.println(i);
		} else {
			System.out.println("It is now empty.");
			currentSex = 'N';
		}
		notify();
	}
}
