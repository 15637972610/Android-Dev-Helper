package com.dkp.viewdemo.Annotation;

/**
 * Created by dkp on 2019/6/7.
 * 第二步：注解使用
 */

public class Fruit {
    @FruitCatory("苹果")
    private String fruitCatory;

    @FruitColor(fruitColor = FruitColor.Color.YELLOW)
    private String fruitColor;

    @FruitProvider(providerId = 1,providerName = "烟台富士",providerAddress = "山东省烟台市富士苹果园")
    private String providerdes;

    public String getFruitCatory() {
        return fruitCatory;
    }

    public void setFruitCatory(String fruitCatory) {
        this.fruitCatory = fruitCatory;
    }

    public String getFruitColor() {
        return fruitColor;
    }

    public void setFruitColor(String fruitColor) {
        this.fruitColor = fruitColor;
    }

    public String getProviderdes() {
        return providerdes;
    }

    public void setProviderdes(String providerdes) {
        this.providerdes = providerdes;
    }

}
