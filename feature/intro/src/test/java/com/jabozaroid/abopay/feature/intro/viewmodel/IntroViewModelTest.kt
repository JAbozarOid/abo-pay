package com.jabozaroid.abopay.feature.intro.viewmodel

import com.jabozaroid.abopay.core.test.rule.MainDispatcherRule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class IntroViewModelTest {


    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun createDispatcherProvider(dispatcher: TestDispatcher): com.jabozaroid.abopay.core.common.dispatcher.DispatcherProvider {
        return  object : com.jabozaroid.abopay.core.common.dispatcher.DispatcherProvider {
            override val ui: CoroutineDispatcher
                get() = dispatcher
            override val io: CoroutineDispatcher
                get() = dispatcher
            override val default: CoroutineDispatcher
                get() = dispatcher
        }
    }

    @Test
    fun `when enter button clicked process then app should navigate to login graph`() {

        runTest {

            val testDispatcher = StandardTestDispatcher(testScheduler)

//            val viewModel = spyk(
//                IntroViewModel(
//                    createDispatcherProvider(testDispatcher)
//                )
//            )

//            viewModel.process(IntroAction.EntryButtonClicked)
//
//            viewModel.navigationFlow.test {
//
//                val command = awaitItem()
//                assertEquals(
//                    ApplicationRoutes.loginGraphRoute,
//                    command.route
//                )

        }

    }

}



