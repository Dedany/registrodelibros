package com.dedany.usecase.author

import com.dedany.domain.entities.Book
import com.dedany.domain.repository.BookRepository
import com.dedany.usecase.FakeData
import com.dedany.usecase.FakeData.fakeAuthor
import com.dedany.usecase.FakeData.fakeBook
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetBooksByAuthorUseCaseTest {

    @MockK
    private lateinit var bookRepository: BookRepository

    lateinit var getBooksByAuthorUseCase: GetBooksByAuthorUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)

        getBooksByAuthorUseCase = GetBooksByAuthorUseCase(bookRepository)
    }

    @Test
    fun `When authorId is empty return empty list`() = runBlocking {

        //Given
        coEvery { bookRepository.getBooksByAuthorId(any()) } returns emptyList()


        //When
        val result = getBooksByAuthorUseCase("")

        //Then
        assert(result.isEmpty())
    }

    @Test
    fun `When authorId has author return books`() = runBlocking {

        //Given
        val author = fakeAuthor("123")
        val book = fakeBook()
        coEvery { bookRepository.getBooksByAuthorId(author.id) } returns listOf(book)

        //When
        val result = getBooksByAuthorUseCase(author.id)

        //Then
        assert(result == listOf(book))
    }
}



