public class DeadLock {
    private final static Object fistLock = new Object();
    public final static Object secondLock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (fistLock) {
                System.out.println("Thread №1: Holding first lock.");

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread №1: Waiting for second lock.");

                synchronized (secondLock) {
                    System.out.println("Thread №1: Holding both.");
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (secondLock) {
                System.out.println("Thread №2: Holding second lock.");

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread №2: Waiting for first lock.");

                synchronized (fistLock) {
                    System.out.println("Thread №2: Holding both.");
                }
            }
        }).start();
    }
}
