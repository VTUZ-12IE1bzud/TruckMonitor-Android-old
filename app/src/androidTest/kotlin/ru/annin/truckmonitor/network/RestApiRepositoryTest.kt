package ru.annin.truckmonitor.network

import android.support.test.runner.AndroidJUnit4
import junit.framework.TestCase
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import ru.annin.truckmonitor.data.repository.RestApiRepository
import ru.annin.truckmonitor.domain.model.AccountResponse
import ru.annin.truckmonitor.domain.model.SignInResponse
import rx.observers.TestSubscriber

/**
 * Тест REST API.
 *
 * @author Pavel Annin.
 */
@RunWith(AndroidJUnit4::class)
class RestApiRepositoryTest : TestCase() {

    companion object {
        lateinit var repository: RestApiRepository

        @JvmStatic
        @BeforeClass
        fun setUpClass() {
            repository = RestApiRepository
        }
    }

    @Before
    public override fun setUp() {
        super.setUp()
    }

    @After
    public override fun tearDown() {
        super.tearDown()
    }

    @Test
    fun signIn() {
        val subscriber: TestSubscriber<SignInResponse> = TestSubscriber()

        val userName = "ivanov@truck.ru"
        val password = "qwerty12345"

        repository.signIn(userName, password).subscribe(subscriber)

        // Request
        subscriber.assertNoErrors()
        subscriber.assertCompleted()
        assertTrue(subscriber.onNextEvents.isNotEmpty())

        // Data
        val data = subscriber.onNextEvents[0]
        assertThat("[SignIn]", data, notNullValue())
    }

    @Test
    fun getMe() {
        val subscriber: TestSubscriber<AccountResponse> = TestSubscriber()

        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJFbWFpbCI6Iml2YW5vdkB0cnVjay5ydSIsImlzcyI6IlRydWNrTW9uaXRvciJ9.oi6hmG_gv5F2W0q4xi24uAz64dIUfp9Cg6rj3wTVJZU"

        repository.getMe(token).subscribe(subscriber)

        // Request
        subscriber.assertNoErrors()
        subscriber.assertCompleted()
        assertTrue(subscriber.onNextEvents.isNotEmpty())

        // Data
        val data = subscriber.onNextEvents[0]
        assertThat("[Me]", data, notNullValue())
    }
}