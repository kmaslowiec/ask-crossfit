package com.example.triviaapp.repository

import com.example.triviaapp.domain.ShuffleEngine
import com.example.triviaapp.homescreen.HomescreenViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomescreenViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: QuestionRepository
    private lateinit var twister: ShuffleEngine
    private lateinit var statsRepository: StatsRepository
    private lateinit var viewModel: HomescreenViewModel

    @BeforeEach
    fun before() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        twister = mockk(relaxed = true)
        statsRepository = mockk(relaxed = true)
        viewModel = HomescreenViewModel(repository, statsRepository, twister)
    }

    @AfterEach
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get an int number WHEN generateQuestionIsCalled`() {
        every { twister.getRandomNumber(100) } returns 10

        val tested = viewModel.generateQuestionNumber()

        assertEquals(tested, 10)
    }

    @Test
    fun `update currentQuestionIndex with a new random number WHEN updateCurrentQuestionIndex is called`() {
        every { viewModel.generateQuestionNumber() } returns 10

        viewModel.updateCurrentQuestionIndex()

        assertEquals(viewModel.currentQuestionIndex.value, 10)
    }

    @Test
    fun `increase number by one WHEN increaseQuestionNumber is called`() {
        viewModel.increaseQuestionNumber()

        assertEquals(viewModel.currentQuestionNumber.value, 2)
    }

    @Test
    fun `incrementWrongAnswersNumber is called exactly once WHEN addWrongAnswerToStats is called`() = runTest {
        viewModel.addWrongAnswerToStats()
        advanceUntilIdle()

        coVerify(exactly = 1) { statsRepository.incrementWrongAnswersNumber() }
    }
}
