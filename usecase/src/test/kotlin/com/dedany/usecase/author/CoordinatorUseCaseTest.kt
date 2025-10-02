package com.dedany.usecase.author

import com.dedany.domain.entities.Author
import com.dedany.domain.entities.Book
import com.dedany.domain.entities.BookWithAuthors
import com.dedany.domain.repository.BookRepository
import com.dedany.usecase.bookAuthor.GetBookWithAuthorsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import net.bytebuddy.matcher.ElementMatchers.any
import org.junit.Before
import org.junit.Test


class CoordinatorUseCaseTest {

    fun fakeBook(
        id: String = "123",
        title: String? = "Libro Test",
        coverId: Int? = 1,
        publishYear: Int? = 2025,
        description: String? = "Descripción",
        subjects: List<String>? = listOf("Ficción"),
        rating: Int = 0,
        isRead: Boolean = false,
        isFavorite: Boolean = false
    ) :Book{
        return Book(id, title, coverId, publishYear, description, subjects, rating, isRead, isFavorite)

    }

    fun fakeAuthor(
        id: String = "1",
        name: String = "Daniel Pérez",
        birthDate: String? = null,
        topWork: String? = null,
        workCount: Int? = null,
        alternateNames: List<String>? = emptyList(),
        topSubjects: List<String>? = emptyList(),
        bio: String? = null,
        isFavorite: Boolean = false
    ): Author {
        return Author(id = id, name = name, birthDate = birthDate, topWork = topWork, workCount = workCount, alternateNames = alternateNames, topSubjects = topSubjects, bio = bio, isFavorite = isFavorite
        )
    }


    @MockK
    private lateinit var bookRepository: BookRepository

    @MockK
    private lateinit var getBookWithAuthorsUseCase: GetBookWithAuthorsUseCase

    lateinit var coordinatorUseCase: CoordinatorUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)

        coordinatorUseCase = CoordinatorUseCase(bookRepository, getBookWithAuthorsUseCase)
    }

    @Test
    fun `when bookRepository doesnt return book anything then get values null`() = runBlocking {

        //Given
        coEvery { bookRepository.getBookById(any()) } returns null

        //When
        val result = coordinatorUseCase("ID_Fake")

        //then
        assert(result == null)
    }

    @Test
    fun `when repository returns a book but getBookWithAuthorsUseCase returns null then result is null`() =
        runBlocking {

            //Given
            val book = fakeBook()
            // Y un BookWithAuthors fake aunque no lo vamos a usar en este test
            val fakeBookWithAuthors = BookWithAuthors(
                book = book,
                authors = emptyList()
            )

            // Cuando se llame a bookRepository.getBookById("123"), devuelve book
            coEvery { bookRepository.getBookById("123") } returns book

            // Cuando se llame a getBookWithAuthorsUseCase("123"), devuelve null
            // Esto simula el escenario donde no se encuentran autores
            coEvery { getBookWithAuthorsUseCase("123") } returns null

            //When: ejecutamos la función que queremos testear

            val result = coordinatorUseCase("123")

            //Then: verificamos el resultado y las llamadas

            // Debe ser null porque getBookWithAuthorsUseCase devolvió null
            assert(result == null)

            // Verificamos que se llamó exactamente una vez a bookRepository.getBookById
            coVerify(exactly = 1) { bookRepository.getBookById("123") }

            // Verificamos que se llamó exactamente una vez a getBookWithAuthorsUseCase
            coVerify(exactly = 1) { getBookWithAuthorsUseCase("123") }

        }

    @Test
    fun `When you find an id that returns a book, pass the correct author`() = runBlocking {
        //Given
        val book = fakeBook()
        val fakeBookWithAuthors = BookWithAuthors(
            book = book,
            authors = listOf( fakeAuthor())
        )

        coEvery { bookRepository.getBookById("123") }returns book
        coEvery { getBookWithAuthorsUseCase("123") } returns fakeBookWithAuthors

        //When
        val result = coordinatorUseCase("123")

        //Then
        assert(result == fakeBookWithAuthors)

        coVerify(exactly = 1) { bookRepository.getBookById("123") }
        coVerify(exactly = 1) { getBookWithAuthorsUseCase("123") }

    }
}