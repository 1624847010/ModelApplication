package com.ll.myapplication.utils.mapper

/**
 * @Author: ll
 * @CreateTime: 2022/05/24 16:06
 */
typealias Mapper<T, R> = (T) -> R

class MapperTest{
    class A class B

    class ABMapper :Mapper<A,B>{
        override fun invoke(p1: A): B {
            return B()
        }
    }

    fun main() {
        val a = ABMapper()
        a(A())
    }
}