package com.daveace.qrnavigationapp.data

import kotlin.reflect.KClass

interface DefaultInstance {

    companion object {
        fun<T:Any> newInstance(what: KClass<T>): T = what.java.getConstructor().newInstance()
    }
}