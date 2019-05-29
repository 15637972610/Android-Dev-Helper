package com.dkp.viewdemo.expandablelist;




public class Person implements MultiItemEntity{

    public Person(String name, int age) {
        this.age = age;
        this.name = name;
    }

    public String name;
    public int age;

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_ENTITY;
    }
}