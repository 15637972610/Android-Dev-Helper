package com.dkp.viewdemo.dispatch;

import android.os.Looper;

import java.util.Arrays;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Administrator on 2019/5/18.
 */

public class StringTest {
    // "static void main" must be defined in a public class.
    static  int i=0 ;
    static boolean isStop =true;
    static Thread mThread;
    public static void main(String[] args) {
            // initialize
//            String s1 = "Hello World";
//            System.out.println("s1 is \"" + s1 + "\"");
//            String s2 = s1;
//            System.out.println("s2 is another reference to s1.");
//            String s3 = new String(s1);
//            System.out.println("s3 is a copy of s1.");
//            // compare using '=='
//            System.out.println("Compared by '==':");
//            // true since string is immutable and s1 is binded to "Hello World"
//            System.out.println("s1 and \"Hello World\": " + (s1 == "Hello World"));
//            // true since s1 and s2 is the reference of the same object
//            System.out.println("s1 and s2: " + (s1 == s2));
//            // false since s3 is refered to another new object
//            System.out.println("s1 and s3: " + (s1 == s3));
//            // compare using 'equals'
//            System.out.println("Compared by 'equals':");
//            System.out.println("s1 and \"Hello World\": " + s1.equals("Hello World"));
//            System.out.println("s1 and s2: " + s1.equals(s2));
//            System.out.println("s1 and s3: " + s1.equals(s3));
//            // compare using 'compareTo'
//            System.out.println("Compared by 'compareTo':");
//            System.out.println("s1 and \"Hello World\": " + (s1.compareTo("Hello World") == 0));
//            System.out.println("s1 and s2: " + (s1.compareTo(s2) == 0));
//            System.out.println("s1 and s3: " + (s1.compareTo(s3) == 0));

//            String s1 = "Hello World";
//            s1.toCharArray()[5] = ',';
//            char s = s1.charAt(4);
//            Long l1 = 128L;
//            Long l2 = 128L;
//            Long l3 = 127L;
//            Long l4 = 127L;
//            System.out.println(l1 == l2);
//            System.out.println((l1 == 128L));
//            System.out.println(l3 == l4);
//
//            Integer a = new Integer(2);
//            Integer b = new Integer(2);
//
//
//            System.out.println(a == b);//false
//            System.out.println(a.equals(b));//true
//
//            Integer c = 128;
//            Integer d = 128;
//
//            Integer e = 127;
//            Integer f =127;
//            System.out.println(c == d);//false
//            System.out.println(e == f);//true


             mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            break;
                        }
                        System.out.println("执行中 i = "+ i++ );

                        if (i==30){
                            stop();
                            break;
                        }

                    }
                }
            });
            mThread.start();

        }

        static void stop (){
            mThread.interrupt();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("mThread.isInterrupted(); "+ mThread.isInterrupted());//true
        }

}
