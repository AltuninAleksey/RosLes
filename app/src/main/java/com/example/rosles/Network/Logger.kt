package com.example.rosles.Network

interface Logger {

    fun log(tag: String, message: String)

    fun error(tag: String, e: Throwable)

}