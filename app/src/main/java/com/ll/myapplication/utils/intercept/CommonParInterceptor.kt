package com.ll.myapplication.utils.intercept

import com.ll.myapplication.utils.intercept.GeneratorUtil
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response

class CommonParInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val timestamp = System.currentTimeMillis()
        val nonce = GeneratorUtil.randomSequence(32)

        val request = chain.request()
        val newRequest = if (request.method == "POST") {
            val builder = request.newBuilder()
            val requestBody = FormBody.Builder()
                .add("timestamp", timestamp.toString())
                .add("nonce", nonce)
                .build()
            builder
                .post(requestBody)
                .build()
        } else {
            val originalHttpUrl = request.url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("nonce", nonce)
                .addQueryParameter("timestamp", timestamp.toString())
                .build()
            val requestBuilder = request.newBuilder()
                .url(url)
            requestBuilder.build()
        }
        return chain.proceed(newRequest)
    }


}