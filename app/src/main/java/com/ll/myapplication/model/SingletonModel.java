package com.ll.myapplication.model;

/**
 * @Author: ll
 * @CreateTime: 2021/10/10 18:40
 */
public class SingletonModel {

    static class Singleton1 {
        private final static Singleton1 INSTANCE = new Singleton1();

        private Singleton1() {
        }

        public static Singleton1 getInstance() {
            return INSTANCE;
        }
    }

    static class Singleton2 {
        private static Singleton2 instance;

        private Singleton2() {
        }

        public static Singleton2 getInstance() {
            if (instance == null) {
                instance = new Singleton2();
            }
            return instance;
        }
    }

    static class Singleton3 {
        private static Singleton3 instance;

        private Singleton3() {
        }

        public static synchronized Singleton3 getInstance() {
            if (instance == null) {
                instance = new Singleton3();
            }
            return instance;
        }
    }

    static class Singleton4 {
        private static volatile Singleton4 instance;

        private Singleton4() {
        }

        public static Singleton4 getInstance() {
            if (instance == null) {
                synchronized (Singleton4.class) {
                    if (instance == null) {
                        instance = new Singleton4();
                    }
                }
            }
            return instance;
        }
    }

    static class Singleton5 {
        private Singleton5() {
        }

        private static class SingletonInstance {
            private final static Singleton5 INSTANCE = new Singleton5();
        }

        public static Singleton5 getInstance() {
            return SingletonInstance.INSTANCE;
        }
    }

    enum Singleton6 {
        INSTANCE;
    }
}