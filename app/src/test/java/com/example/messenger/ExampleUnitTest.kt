@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "EXPERIMENTAL_IS_NOT_ENABLED"
)

package com.example.messenger

import com.example.messenger.network.Requests
import io.ktor.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Test

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

    @OptIn(InternalAPI::class, kotlinx.coroutines.DelicateCoroutinesApi::class)
    @Test
    fun test2() {
        GlobalScope.launch(Dispatchers.IO) {
            var k = "123456"
            k = Requests.test()
            withContext(Dispatchers.Main) {
                println("dwadw")
            }
        }
        println("dwadwa")
    }
}