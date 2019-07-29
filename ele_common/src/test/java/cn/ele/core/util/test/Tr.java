package cn.ele.core.util.test;

public class Tr {

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.println("来"+i);
                }
            }
        }); Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.println("去"+i);
                }
            }
        });
        thread1.start();
        thread1.join();
        thread2.start();


    }
}
