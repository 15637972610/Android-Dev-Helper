package com.dkp.viewdemo.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * Created by dkp on 2019/6/7.
 * 定义一个水果类型的注解
 * 步骤：
 * 1.创建类用@interface修饰
 * 2.用元数据Target描述注解的使用方位
 * 3.用@Retention描述该注解生效范围
 * 以上是必须添加的元数据，下面是根据需求可自己配置的元数据
 * @Documented 一个简单的Annotations标记注解，表示是否将注解信息添加在java文档中
 * @Inherited：描述某个被标注的类型是可被继承的
 */

@Target(value={ElementType.METHOD,ElementType.CONSTRUCTOR,ElementType.FIELD,ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface FruitCatory {

     String value() default "";

}
