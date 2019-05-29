package com.dkp.viewdemo.sort;

/**
 * Created by Administrator on 2019/5/24.
 */

public class CharSort {

    public static void main(String[] args){

        charSort();
    }

    private static void charSort() {
        char[] mychar = {'9','8','7','A','B','C','e','r','f','t'};
        for (int i =0;i<mychar.length;i++){
            char temp ;
            int j = i+1;
            for (;j<mychar.length;j++){
                if (mychar[j]<mychar[i]){//后一个数比前一个数小交换位置
                    temp = mychar[i];
                    mychar[i] = mychar[j];
                    mychar[j] = temp;
                }
            }
        }

        for (int i =0;i<mychar.length;i++){
            System.out.println("第"+(i+1) +"个数是："+mychar[i]);
        }

    }


}
