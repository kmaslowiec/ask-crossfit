package com.example.triviaapp.repository

import com.example.triviaapp.model.Questions
import com.example.triviaapp.service.QuestionService
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

class QuestionRepositoryTest {

    private lateinit var questionService: QuestionService
    private lateinit var questionRepository: QuestionRepository

    @BeforeEach
    fun setup() {
        questionService = mockk()
        questionRepository = QuestionRepository(questionService)
    }

    @Test
    fun `getQuestions returns data on successful API call`() = runBlocking {
        val expectedQuestions = Questions()
        coEvery { questionService.getAllQuestions() } returns expectedQuestions

        val result = questionRepository.getQuestions()

        assertFalse(result.loading!!)
        assertEquals(expectedQuestions, result.data)
        assertNull(result.exception)
    }

    @Test
    fun `getQuestions returns exception on API call failure`() = runBlocking {
        val exception = IOException("Network Error")
        coEvery { questionService.getAllQuestions() } throws exception

        val result = questionRepository.getQuestions()

        assertTrue(result.loading!!)
        assertNull(result.data)
        assertEquals(exception, result.exception)
    }
}
