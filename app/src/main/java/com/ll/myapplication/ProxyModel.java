package com.ll.myapplication;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: ll
 * @CreateTime: 2021/09/28 10:20
 */
public class ProxyModel {

    public static void main(String[] args) {
        ILawSuit proxy= (ILawSuit) ProxyFactory.getDynProxy(new CuiHuaNiu());
        proxy.submit("工资流水在此");
        proxy.defend();
    }

}

interface ILawSuit {
    void submit(String proof);//提起诉讼
    void defend();//法庭辩护
}

class CuiHuaNiu implements ILawSuit {
    @Override
    public void submit(String proof) {
        System.out.println(String.format("老板欠薪跑路，证据如下：%s",proof));
    }
    @Override
    public void defend() {
        System.out.println(String.format("铁证如山，%s还牛翠花血汗钱","马旭"));
    }
}

class DynProxyLawyer implements InvocationHandler {
    private Object target;//被代理的对象
    public DynProxyLawyer(Object obj){
        this.target=obj;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("案件进展："+method.getName());
        Object result=method.invoke(target,args);
        return result;
    }
}

class ProxyFactory {
    public static Object getDynProxy(Object target) {
        InvocationHandler handler = new DynProxyLawyer(target);
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), handler);
    }
}
