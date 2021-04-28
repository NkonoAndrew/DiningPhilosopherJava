import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implements the shared functions of the Philosophers
 */
public class DiningPhilosopher implements DiningServer{

    enum State {
      THINKING, EATING, HUNGRY
    }

    private Lock resourceLock;
    private Condition[] conditionVariable;

    public static State[] states = new State[5];

    DiningPhilosopher(){
        resourceLock = new ReentrantLock();
        conditionVariable = new Condition[5];
        for (int i = 0; i < 5; i++) {
            conditionVariable[i] = resourceLock.newCondition();
        }
    }

    /**
     * Picking up the forks
     * @param i current philosopher
     */
    public void takeForks(int i) {
        resourceLock.lock();
        try {
            //to pick a fork, a philosopher has to be anrgy
            states[i] = State.HUNGRY;
            System.out.println("Philosopher " + i + " is HUNGRY!");

            //when hungry, we have to check the neighbors to determine if they are using the forks
            test(i);

            //while the current person is not eating, we wait until the lock is unlocked 
            while (states[i] != State.EATING){
                conditionVariable[i].await();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            resourceLock.unlock();
        }
    }

    /**
     * Philospher eaten has to put down the forks
     * @param i
     */
    public void returnForks(int i) {
        resourceLock.lock();
        try {
            states[i] = State.HUNGRY;

            //check left and right neighbors
            test((i + 4) % 5);
            test((i + 1) % 5);
            conditionVariable[i].signalAll();

        } finally {
            resourceLock.unlock();
        }
    }

    /**
     * Checks to see if the neighbors have the forks and are eating
     * @param i
     */
    private void test(int i) {
        //if the neighbors to the left and the right are not eating, the current philosopher will start eating
        //and let the others know
        if( (states[(i + 4) % 5] != State.EATING) &&
                (states[i] == State.HUNGRY) &&
                (states[(i + 1) % 5] != State.EATING) ) {
            states[i] = State.EATING;
            conditionVariable[i].signalAll();
        }
    }




}
