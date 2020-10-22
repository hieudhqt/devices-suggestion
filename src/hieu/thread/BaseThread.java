package hieu.thread;

public class BaseThread extends Thread {

//    private static boolean suspended = false;
    private static BaseThread instance;
    private final static Object LOCK = new Object();

    protected BaseThread() {

    }

}
