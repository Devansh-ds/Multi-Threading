package com.sadds.ExecutorService;

import java.util.concurrent.*;

public class CallbackAndReturn {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try (ExecutorService service = Executors.newFixedThreadPool(2)) {
//            service.execute(new ReturnValueTask());
            Future<Integer> result = service.submit(new ReturnValueTask());

            /*
               Imp methods:
                cancel()
                isCancelled()
                isDone()
            */


            System.out.println(result.get());
//            result.get(1, TimeUnit.SECONDS) here first param is timeLimit.
//            If we don't get value then timeout exception
            System.out.println("Main thread execution completed only after getting from get()");
        }
    }

}

/*
   Callable is similar to Runnable but here we implement call() method which can return
   a value. Above result variable is of type Future<> which does not actually contains
   the value. But if the main thread uses the get() method then it checks if it has the
   value else the main thread will be blocked until the call() returns a value. After
   that it resumes the main thread.
*/


class ReturnValueTask implements Callable<Integer> {

//    @Override
//    public void run() {
//        return 3;
//    }

    @Override
    public Integer call() throws Exception {
        Thread.sleep(5000);
        return 3;
    }
}
