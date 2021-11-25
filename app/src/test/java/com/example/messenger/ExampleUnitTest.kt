@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "EXPERIMENTAL_IS_NOT_ENABLED"
)

package com.example.messenger

import com.example.messenger.data.Message
import kotlinx.datetime.toKotlinLocalDateTime
import org.junit.Assert.assertEquals
import org.junit.Test
import java.security.MessageDigest
import java.time.LocalDateTime

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test1() {
        val number: String? = null
        val k = number ?: "+7"
        println(k)
    }

    @Test
    fun test2() {
        val message = Message(message = "32423dawadwa", userId = 5, dateTime = LocalDateTime.now().toKotlinLocalDateTime())
        println(message = message.toString())
    }

    private fun getDigest(str: String): ByteArray =
        MessageDigest.getInstance("MD5").digest(str.toByteArray(Charsets.UTF_8))
}