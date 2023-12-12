package com.example.triviaapp.repository.game.repository

import com.example.triviaapp.game.api.GameApi
import com.example.triviaapp.game.model.Questions
import com.example.triviaapp.game.repository.GameRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GameRepositoryTest {

    private lateinit var gameApi: GameApi
    private lateinit var questionRepository: GameRepository

    @BeforeEach
    fun setup() {
        gameApi = mockk()
        questionRepository = GameRepository(gameApi)
    }

    @Test
    fun `getQuestions returns data on successful API call`() = runBlocking {
        val expectedQuestions = Questions()
        coEvery { gameApi.getAllQuestions() } returns expectedQuestions

        val result = questionRepository.getQuestions()

        assertTrue(result.isSuccess)
        assertNotNull(result.getOrNull())
    }

    @Test
    fun `getQuestions returns exception on API call failure`() = runBlocking {
        val exception = Exception()
        coEvery { gameApi.getAllQuestions() } throws exception

        val result = questionRepository.getQuestions()

        assertNull(result.getOrNull())
        assertEquals(exception, result.exceptionOrNull())
        assertTrue(result.isFailure)
    }
}
