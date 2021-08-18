package com.example.workflow.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Modifier

internal class ConstantsTest {

    @Test
    @Throws(
        NoSuchMethodException::class,
        IllegalAccessException::class,
        InstantiationException::class
    )
    fun testConstructorIsPrivate() {
        val constructor = Constants::class.java.getDeclaredConstructor()
        assertTrue(Modifier.isPrivate(constructor.modifiers))
        constructor.isAccessible = true
        try {
            constructor.newInstance()
        } catch (invocationTargetException: InvocationTargetException) {
            assertEquals(
                IllegalStateException("Constants Utility Class. Cannot be instantiated").message,
                invocationTargetException.cause!!.message
            )
        }
    }

}