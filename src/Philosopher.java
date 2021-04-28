public class Philosopher extends Thread{

    int number;
    DiningServer diningServer;
    DiningPhilosopher.State state;

    public Philosopher(int number, DiningServer diningServer){
        this.number = number;
        this.diningServer = diningServer;
        state = DiningPhilosopher.State.THINKING;
        System.out.println("Philosopher " + number + " is THINKING");
    }

    public void run(){

        try {
            //Runs endlessly and has to be stopped by the user
            while (true){
            diningServer.takeForks(number);
            System.out.println("Philosopher " + number + " is EATING");
            Thread.sleep(5000);
            diningServer.returnForks(number);
            System.out.println("Philosopher " + number + " is THINKING");
            Thread.sleep(5000);

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
