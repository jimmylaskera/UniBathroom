import java.util.Random;

/**
 * Class that represents people that will use the bathroom.
 * Each person is a instance of a thread.
 */
public class Person extends Thread {
    /** Sex of this person: Man or woman */
    private char sex;

    /** Time spent using the bathroom */
    private long duration;

    /** Bathroom that will be used by everyone */
    private Bathroom room;

    /**
     * Parameterized constructor
     * @param name Thread name
     * @param sx Person's sex
     * @param durtime Time using bathroom
     * @param rm Bathroom instance
     */
    public Person (String name, char sx, long durtime, Bathroom rm) {
        super(name);
        sex = sx;
        duration = durtime;
        room = rm;
    }

    /**
     * Getter for 'sex' attribute
     * @return sex of this person
     */
    public char getSex() {
        return sex;
    }

    
    /**
     * Setter for 'sex' attribute
     * @param sex new sex of this person
     */
    public void setSex(char sex) {
        this.sex = sex;
    }

    /**
     * Getter for 'duration' attribute
     * @return time in the bathroom
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Setter for 'duration' attribute
     * @param duration the time this person will spend in the bathroom
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    /* (non-Javadoc)
     * @see java.lang.Thread#run()
     * The person will try to enter the bathroom, then spends some time inside and exits.
     */
    @Override
	public void run() {
        Random rng = new Random(System.currentTimeMillis());
        try {
            Person.sleep(rng.nextLong(3000));
        } catch (InterruptedException e) {
            e.getMessage();
        }
		room.enter(this);
        room.exit(this);
	}
}
