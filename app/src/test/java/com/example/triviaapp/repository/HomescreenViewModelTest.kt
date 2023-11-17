package com.example.triviaapp.repository

import com.example.triviaapp.domain.ShuffleEngine
import com.example.triviaapp.homescreen.HomescreenViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomescreenViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun before() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getQuestions returns data on successful API call`() {
        val repository: QuestionRepository = mockk()
        val twister: ShuffleEngine = mockk()
        val statsRepository: StatsRepository = mockk()
        val viewModel = HomescreenViewModel(repository, statsRepository, twister)
        every { twister.getRandomNumber(100) } returns 10

        val tested = viewModel.generateQuestionNumber()

        assertEquals(tested, 10)
    }
    //TODO more tests
}
