package com.example.triviaapp.repository

import com.example.triviaapp.model.Questions
import com.example.triviaapp.service.QuestionService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.IOException

class QuestionRepositoryTest {

    @Test
    fun `getQuestions returns data on successful API call`() = runBlocking {
        val questionService: QuestionService = mockk()
        val questionRepository = QuestionRepository(questionService)
        val mockkData = Questions()

        coEvery { questionService.getAllQuestions() } returns mockkData

        val result = questionRepository.getQuestions()

        assertEquals(mockkData, result.data)
        assertEquals(false, result.loading)
        assertEquals(null, result.exception)

        coVerify(exactly = 1) { questionService.getAllQuestions() }
    }

    @Test
    fun `getQuestions returns exception on API call failure`() = runBlocking {
        val questionService: QuestionService = mockk()
        val questionRepository = QuestionRepository(questionService)
        val exception = IOException("Network Error")

        coEvery { questionService.getAllQuestions() } throws exception

        val result = questionRepository.getQuestions()

        assertEquals(null, result.data)
        assertEquals(true, result.loading)
        assertEquals(exception, result.exception)
        assertThrows<IOException>(message = { "Network Error" }) {
            throw IOException("Network Error")
        }

        coVerify(exactly = 1) { questionService.getAllQuestions() }
    }
}
