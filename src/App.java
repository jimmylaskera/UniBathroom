import java.util.Random;

public class App {
	/**
	 * Main method
	 * @param args Command line arguments: capacity of the bathroom, number of men, and number of women.
     * It will use fixed values, otherwise.
	 */
	public static void main(String[] args) {
		int capacity = (args.length == 3) ? Integer.valueOf(args[0]) : 10;
        int num_men = (args.length == 3) ? Integer.valueOf(args[1]) : 5;
        int num_women = (args.length == 3) ? Integer.valueOf(args[2]) : 5;
        
        Bathroom room = new Bathroom(capacity);
        Person[] men = new Person[num_men];
		Person[] women = new Person[num_women];

        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());
		for (int i = 0; i < num_men; i++) men[i] = new Person(("Male " + i), 'M', rnd.nextLong(10000), room);
        for (int i = 0; i < num_women; i++) women[i] = new Person(("Female " + i), 'F', rnd.nextLong(10000), room);
		
        int m = 0;
        int f = 0;

        while (m < num_men || f < num_women) {
            if (m < num_men) {
                men[m].start();
                m++;
            }
            if (f < num_women) {
                women[f].start();
                f++;
            }
        }
		
		try {
			for (int i = 0; i < (num_men + num_women); i++) {
				if (i < num_men) men[i].join();
				else women[i-num_men].join();
			}
            System.out.println("There is no more people today.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
