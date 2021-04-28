public interface DiningServer {
    /*
    Called by a philosopher who wants to eat
     */
    public default void takeForks(int philosopherNumber){

    }

     /*
    Called by a philosopher who is done eating
     */
    default void returnForks(int philospherNumber){

    }
}
