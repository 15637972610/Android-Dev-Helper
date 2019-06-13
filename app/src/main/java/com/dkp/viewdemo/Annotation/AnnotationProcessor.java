package com.dkp.viewdemo.Annotation;

import java.lang.reflect.Field;

/**
 * Created by dkp on 2019/6/7.
 * 定义一个注解处理器，解读我们定义的注解
 */

public class AnnotationProcessor {

    public static void getFruitInfo(Class<?> clazz){

        String strFruitName=" 水果名称：";
        String strFruitColor=" 水果颜色：";
        String strFruitProvicer="供应商信息：";

        Field[] fields = clazz.getDeclaredFields();
        for (Field field :fields){
            //从打印信息可以看到getDeclaredFields返回的是clazz类中用到注解的成员变量的集合。
            System.out.println("field name ="+field.getName()+",fields.DeclaringClass = "+field.getDeclaringClass());
        }



        for(Field field :fields){
            //判断该程序元素上是否包含指定类型的注解
            if(field.isAnnotationPresent(FruitCatory.class)){
                FruitCatory fruitName = (FruitCatory) field.getAnnotation(FruitCatory.class);
                strFruitName=strFruitName+fruitName.value();
                System.out.println(strFruitName+"do something ...");
            }
            else if(field.isAnnotationPresent(FruitColor.class)){
                FruitColor fruitColor= (FruitColor) field.getAnnotation(FruitColor.class);
                strFruitColor=strFruitColor+fruitColor.fruitColor().toString();
                System.out.println(strFruitColor+"do something ...");
            }
            else if(field.isAnnotationPresent(FruitProvider.class)){
                FruitProvider fruitProvider= (FruitProvider) field.getAnnotation(FruitProvider.class);
                strFruitProvicer=" 供应商编号："+fruitProvider.providerId()+" 供应商名称："+fruitProvider.providerName()+" 供应商地址："+fruitProvider.providerAddress();
                System.out.println(strFruitProvicer+"do something ...");
            }
        }
    }


}
