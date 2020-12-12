
public class DeadLock {
    private final static Object fistLock = new Object();
    public final static Object secondLock = new Object();

    public static void main(String[] args) {
        new Thread(() -> testForDeadLock(fistLock, secondLock)).start();
        new Thread(() -> testForDeadLock(secondLock, fistLock)).start();
    }

    public static void testForDeadLock(Object fistLock, Object secondLock) {
        synchronized (fistLock) {
            System.out.println(getCurrentThread() + ": Holding lock-" + fistLock);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getCurrentThread() + ": Waiting for lock-" + secondLock);

            synchronized (secondLock) {
                System.out.println(getCurrentThread() + ": Holding both.");
            }
        }
    }

    private static String getCurrentThread() {
        return Thread.currentThread().toString();
    }

}
