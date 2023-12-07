package com.example.triviaapp.repository.game.repository

import com.example.triviaapp.game.model.Questions
import com.example.triviaapp.game.repository.GameRepository
import com.example.triviaapp.game.api.GameApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

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

        assertFalse(result.loading!!)
        assertEquals(expectedQuestions, result.data)
        assertNull(result.exception)
    }

    @Test
    fun `getQuestions returns exception on API call failure`() = runBlocking {
        val exception = IOException("Network Error")
        coEvery { gameApi.getAllQuestions() } throws exception

        val result = questionRepository.getQuestions()

        assertTrue(result.loading!!)
        assertNull(result.data)
        assertEquals(exception, result.exception)
    }
}
