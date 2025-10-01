package com.dedany.usecase.author

import com.dedany.domain.repository.BookRepository
import com.dedany.usecase.bookAuthor.GetBookWithAuthorsUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before


class CoordinatorUseCaseTest {

    @RelaxedMockK
    private lateinit var bookRepository: BookRepository
    @RelaxedMockK
    private lateinit var getBookWithAuthorsUseCase: GetBookWithAuthorsUseCase

    lateinit var coordinatorUseCase: CoordinatorUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)

        coordinatorUseCase = CoordinatorUseCase(bookRepository, getBookWithAuthorsUseCase)
    }
}