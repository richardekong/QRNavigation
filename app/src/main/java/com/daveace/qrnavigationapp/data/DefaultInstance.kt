package com.daveace.qrnavigationapp.data

import kotlin.reflect.KClass

interface DefaultInstance {

    companion object {
        fun  newInstance(what: KClass<*>): Any = what::class.java.getConstructor().newInstance()
    }
}