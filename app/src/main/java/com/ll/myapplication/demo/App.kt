package com.ll.myapplication.demo

import com.blankj.utilcode.util.LogUtils

/**
 * @Author: ll
 * @CreateTime: 2022/04/26 11:44
 */
data class Data1(
    val a: Int = 1,
    val b: Int = 1,
    val c: Int = 1,
)

//不能放相同类 必须单独写在某些位置
fun Data1.asEntity() = this.copy(2, 3, 4)


class App : BaseApp() {

    override fun sout() {
        super.sout()
        LogUtils.d("App")

        C {
            copy(a = 2)
        }

        D {
            it.copy(a = 2)
        }

        LogUtils.d(A(Data::A))

        listOf(Data1(), Data1(2), Data1(3))
            .map(Data1::asEntity)
    }

    fun A(a: Data.(e: Int) -> Int) = a(Data(2, 2, 2), 3)


    //其实等价于 a:(Data) ->Data 同时存在时报错
    fun C(a: Data.() -> Data) {
        LogUtils.d(a(Data()).a)
    }

    fun D(a: (Data) -> Data) {
        LogUtils.d(a(Data()).a)
    }

    fun A(a: (Data) -> Data) = a(Data())

    data class Data(
        val a: Int = 1,
        val b: Int = 1,
        val c: Int = 1,
    ) {
        fun A(d: Int): Int = a + d
    }


}