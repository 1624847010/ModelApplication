package com.ll.myapplication.model

/**
 * @Author: ll
 * @CreateTime: 2022/05/05 17:05
 */
class Singleton {

    companion object {
        @Volatile
        private var singleton1: Singleton? = null

        fun getInstance(): Singleton {
            return singleton1 ?: synchronized(this) {
                singleton1?.let {
                    return it
                }

                val instance = Singleton()
                singleton1 = instance
                instance
            }
        }


        val singleton2 by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { Singleton() }
    }
}

