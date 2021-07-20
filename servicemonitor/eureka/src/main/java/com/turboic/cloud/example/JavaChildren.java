package com.turboic.cloud.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

/**
 * @author liebe
 */
public class JavaChildren extends JavaParent{
    private static final Logger logger = LoggerFactory.getLogger(JavaChildren.class);
    public JavaChildren(){
        //隐式调用父类的构造方法
        logger.info("JavaChildren构造方法初始化");
    }

    public JavaChildren(String name){
        super(name);
        //隐式调用父类的构造方法
        logger.info("JavaChildren构造方法初始化 有参数");

    }

    public String show(){
        logger.info(this.name);
        return super.name;
    }

    public static void main(String[] args) {
        JavaChildren javaChildren = new JavaChildren("liebe");
        javaChildren.show();
        Mono<JavaChildren> javaChildrenMono = Mono.just(javaChildren);
        Disposable disposable = javaChildrenMono.subscribe(System.out::println);
        String disposableString = disposable.toString();
        logger.info(disposableString);
    }

    @Override
    public String toString() {
        return "JavaChildren{" +
                "name='" + name + '\'' +
                '}';
    }
}
