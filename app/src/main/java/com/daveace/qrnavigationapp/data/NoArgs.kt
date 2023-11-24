package com.daveace.qrnavigationapp.data

import kotlin.reflect.KClass

interface NoArgs {

    companion object {
        fun<T:Any> newInstance(what: KClass<T>): T = what.java.getConstructor().newInstance()
    }
}