package com.dkp.viewdemo.Annotation;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by dkp on 2019/6/7.
 */

public class AnnotationTest {
    public static void main(String[]arg){
        AnnotationProcessor.getFruitInfo(Fruit.class);
        Fruit f =new Fruit();
        new SoftReference<Fruit>(f);
        HashMap
        LinkedList linkedList = new LinkedList();
        linkedList.add("dddd");
        linkedList.add("1233");
    }
}
