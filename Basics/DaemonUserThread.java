package com.sadds.Basics;

public class DaemonUserThread {

    public static void main(String[] args) {
        Thread bgThread = new Thread(new DaemonHelper());
        Thread userThread = new Thread(new UserThreadHelper());

        bgThread.setDaemon(true);   // to set any thread as Daemon.

        bgThread.start();
        userThread.start();

        /*
          > Daemon Thread is running in the background, like garbage collection thread
            The main priority is given to user thread. So when user thread went into the
            sleep of 5 seconds then, Daemon thread starts running. And the moment user thread
            is active again it takes the cpu back.
          > Daemon thread in not run till it is completely executed instead it is run only
            if the cpu is free and main thread is not completely executed.
        */
    }

}

class DaemonHelper implements Runnable {
    @Override
    public void run() {
        int count = 0;
        while (count < 500) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            count++;
            System.out.println("Daeamon Thread running ....");
        }

    }
}

class UserThreadHelper implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("User Thread done with execution");
    }
}
