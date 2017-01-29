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
import ru.annin.truckmonitor.domain.model.MeResponse
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
        val subscriber: TestSubscriber<MeResponse> = TestSubscriber()

        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiaXNzIjoiVHJ1Y2tNb25pdG9yIn0.Rbkie--WwbRUmFQk13AXVf-my9tvi0e6YR057Sutpls"

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