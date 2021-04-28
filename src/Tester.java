/**
 * This program executes indefinitely until the user makes it stop
 */
public class Tester {
    public static void main(String[] args) {
        //Every object has a single lock associated with it
        DiningServer diningServer = new DiningPhilosopher();


        for (int i = 0; i < 5 ; i++) {
            new Philosopher(i, diningServer).start();
        }

    }
}
