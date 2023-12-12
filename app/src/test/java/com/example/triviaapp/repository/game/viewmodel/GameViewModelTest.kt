package com.example.triviaapp.repository.game.viewmodel

import com.example.triviaapp.common.domain.ShuffleEngine
import com.example.triviaapp.common.repository.StatsRepository
import com.example.triviaapp.game.model.Questions
import com.example.triviaapp.game.model.QuestionsState
import com.example.triviaapp.game.repository.GameRepository
import com.example.triviaapp.game.viewmodel.GameViewModel
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
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
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val questions: Questions by lazy { Questions() }
    private val gameRepositoryMock: GameRepository by lazy { mockk() }
    private val twister: ShuffleEngine by lazy { mockk(relaxed = true) }
    private val statsRepository: StatsRepository by lazy { mockk() }
    private lateinit var viewModel: GameViewModel

    @BeforeEach
    fun before() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun after() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `gets Success result WHEN questions were fetched correctly`() = runTest {
        coEvery { gameRepositoryMock.getQuestions() } returns Result.success(questions)
        viewModel = GameViewModel(gameRepositoryMock, statsRepository, twister)

        advanceUntilIdle()
        assertTrue(viewModel.questionsState.value is QuestionsState.Success)
    }

    @Test
    fun `gets Loading result before any other result WHEN questions were fetched correctly`() = runTest {
        coEvery { gameRepositoryMock.getQuestions() } returns Result.success(questions)
        viewModel = GameViewModel(gameRepositoryMock, statsRepository, twister)

        assertTrue(viewModel.questionsState.value is QuestionsState.Loading)
    }

    @Test
    fun `gets Error result WHEN questions receive an Exception`() = runTest {
        coEvery { gameRepositoryMock.getQuestions() } returns Result.failure(Exception())
        viewModel = GameViewModel(gameRepositoryMock, statsRepository, twister)

        advanceUntilIdle()

        assertTrue(viewModel.questionsState.value is QuestionsState.Error)
    }

    @Test
    fun `gets an int number WHEN generateQuestionIsCalled`() {
        every { twister.getRandomNumber(100) } returns 10
        viewModel = GameViewModel(gameRepositoryMock, statsRepository, twister)

        val tested = viewModel.generateQuestionNumber()

        assertEquals(tested, 10)
    }

    @Test
    fun `updates currentQuestionIndex with a new random number WHEN updateCurrentQuestionIndex is called`() {
        viewModel = GameViewModel(gameRepositoryMock, statsRepository, twister)
        every { viewModel.generateQuestionNumber() } returns 10

        viewModel.updateCurrentQuestionIndex()

        assertEquals(viewModel.currentQuestionIndex.value, 10)
    }

    @Test
    fun `updates selectedAnswer with a new value WHEN updateSelectedAnswer is called`() {
        val newAnswer = "newAnswer"
        viewModel = GameViewModel(gameRepositoryMock, statsRepository, twister)

        viewModel.updateSelectedAnswer(newAnswer)

        assertEquals(viewModel.selectedAnswer.value, "newAnswer")
    }

    @Test
    fun `increases number by one WHEN increaseQuestionNumber is called`() {
        viewModel = GameViewModel(gameRepositoryMock, statsRepository, twister)

        viewModel.increaseQuestionNumber()

        assertEquals(viewModel.currentQuestionNumber.value, 2)
    }

    @Test
    fun `incrementWrongAnswersNumber is called exactly once WHEN addWrongAnswerToStats is called`() {
        runTest {
            coEvery { gameRepositoryMock.getQuestions() } returns Result.success(questions)
            coEvery { statsRepository.incrementWrongAnswersNumber() } just Runs
            viewModel = GameViewModel(gameRepositoryMock, statsRepository, twister)

            viewModel.addWrongAnswerToStats()
            advanceUntilIdle()

            coVerify(exactly = 1) { statsRepository.incrementWrongAnswersNumber() }
        }
    }
}
