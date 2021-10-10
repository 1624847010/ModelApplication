package com.ll.myapplication.model;

/**
 * @Author: ll
 * @CreateTime: 2021/10/10 18:28
 * 抽象工厂模式
 */
public class FactoryModel {
    public void main(String[] args) {
        //使用苹果工厂生产苹果公司的系列产品
        AbstractFactory appleFactory=new AppleFactory();
        appleFactory.makeComputer().setOperationSystem();
        appleFactory.makeMobilePhoto().setOperationSystem();

        //使用小米工厂生产小米公司的系列产品
        AbstractFactory miFactory=new XiaoMiFactory ();
        miFactory.makeComputer().setOperationSystem();
        miFactory.makeMobilePhoto().setOperationSystem();
    }
    //电脑接口
    abstract class Computer {
        public abstract void setOperationSystem();
    }

    class MacComputer extends Computer {
        @Override
        public void setOperationSystem() {
            System.out.println("Mac笔记本安装Mac系统");
        }
    }

    class MiComputer extends Computer {
        @Override
        public void setOperationSystem() {
            System.out.println("小米笔记本安装Win10系统");
        }
    }

    //手机接口
    abstract class MobilePhoto {
        public abstract void setOperationSystem();
    }
    class IPhoto extends MobilePhoto {
        @Override
        public void setOperationSystem() {
            System.out.println("苹果手机安装IOS系统");
        }
    }
    class MiPhoto extends MobilePhoto {
        @Override
        public void setOperationSystem() {
            System.out.println("小米手机安装Android系统");
        }
    }
    //抽象工厂接口
    interface AbstractFactory {
        Computer makeComputer();
        MobilePhoto makeMobilePhoto();
    }
    class AppleFactory implements AbstractFactory {
        @Override
        public Computer makeComputer() {
            return new MacComputer();
        }

        @Override
        public MobilePhoto makeMobilePhoto() {
            return new IPhoto();
        }
    }

    class XiaoMiFactory implements AbstractFactory {
        @Override
        public Computer makeComputer() {
            return new MiComputer();
        }

        @Override
        public MobilePhoto makeMobilePhoto() {
            return new MiPhoto();
        }
    }
}





