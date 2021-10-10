package com.ll.myapplication;

/**
 * @Author: ll
 * @CreateTime: 2021/10/09 10:05
 */
public class FactoryModel {
    public static void main(String[] args) {
        //生产Mac电脑
        ComputerFactory macFactory=new MacComputerFactory();
        macFactory.makeComputer().setOperationSystem();

        //生产小米电脑
        ComputerFactory miFactory=new MiComputerFactory();
        miFactory.makeComputer().setOperationSystem();
    }
}

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

interface ComputerFactory {
    Computer makeComputer();
}

class MacComputerFactory implements ComputerFactory {
    @Override
    public Computer makeComputer() {
        return new MacComputer();
    }
}

class MiComputerFactory implements ComputerFactory {
    @Override
    public Computer makeComputer() {
        return new MiComputer();
    }
}
