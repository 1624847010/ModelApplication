package com.ll.myapplication.ui.infix

/**
 * @Author: ll
 * @CreateTime: 2022/05/06 14:46
 */
object InfixDemo {
    fun sam(): Int =
        1 plus {
            1
        } minus 1 plus {
            1
        } minus 1


    infix fun Int.plus(f: () -> Int): Int {
        return this + f.invoke()
    }

    infix fun Int.minus(f: Int): Int {
        return this - f
    }
}