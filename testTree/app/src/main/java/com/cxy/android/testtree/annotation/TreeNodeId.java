package com.cxy.android.testtree.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by chenxinying on 17/2/10
 */
@Target(ElementType.FIELD)//表明注解申明在那些地方，可以选择类，方法上等
//class 表示在编译时可见
//Runtime 表示运行时可见
//source 会打到源码里面去
@Retention(RetentionPolicy.RUNTIME)//注解在什么时候可见 ，需要在运行时可见

public @interface TreeNodeId {

}








































