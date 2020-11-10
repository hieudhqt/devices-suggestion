package hieu.thread;

public class BaseThread extends Thread {

    private final static Object LOCK = new Object();
    private static boolean suspended = false;
    private static BaseThread instance;

    protected BaseThread() {

    }

    public static BaseThread getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new BaseThread();
            }
        }
        return instance;
    }

    public static boolean isSuspended() {
        return suspended;
    }

    public static void setSuspended(boolean suspended) {
        BaseThread.suspended = suspended;
    }

    public void suspendThread() {
        setSuspended(true);
        System.out.println("Thread " + instance.getId() + " suspended");
    }

    public synchronized void resumeThread() {
        setSuspended(false);
        notifyAll();
        System.out.println("Thread " + instance.getId() + " resume");
    }
}
