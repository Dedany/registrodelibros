package com.dedany.usecase.author


import com.dedany.domain.entities.Book // Necesario para el tipo de retorno de bookRepository.getBookById
import com.dedany.domain.entities.BookWithAuthors
import com.dedany.domain.repository.BookRepository
import com.dedany.usecase.bookAuthor.GetBookWithAuthorsUseCase // Tu caso de uso existente
import javax.inject.Inject

class CoordinatorUseCase  @Inject constructor(
    private val bookRepository: BookRepository,
    private val getBookWithAuthorsUseCase: GetBookWithAuthorsUseCase
) {
    suspend operator fun invoke(bookIdFromViewModel: String): BookWithAuthors? {


        // PASO 1: Llamar a bookRepository.getBookById() para asegurar que los datos base,
        // los autores y las CrossRef se procesen y guarden en la BD.
        // El 'bookIdFromViewModel' es el ID que viene de la UI/ViewModel.
        // bookRepository.getBookById() internamente manejará la limpieza de este ID si es necesario para la API.

        val preliminaryBook : Book? = bookRepository.getBookById(bookIdFromViewModel)

        if (preliminaryBook == null) {

            return null // Si el libro base no se pudo cargar/guardar, no tiene sentido continuar.
        }


        // PASO 2: Ahora que los datos (incluyendo CrossRefs) deberían estar listos,
        // llamar a getBookWithAuthorsUseCase para leer la @Relation.
        // Es CRUCIAL que el ID que se pasa aquí (preliminaryBook.id) sea el ID LIMPIO del libro,
        // el mismo que se usó como BookDbo.id y BookAuthorCrossRef.bookId.
        // Asumimos que preliminaryBook.id (devuelto por bookRepository.getBookById) ES este ID limpio.
        val bookIdForCrossRefLookup = preliminaryBook.id

        val bookWithAuthorsResult = getBookWithAuthorsUseCase(bookIdForCrossRefLookup)


        if (bookWithAuthorsResult != null && bookWithAuthorsResult.authors.isEmpty()) {

        }
        return bookWithAuthorsResult
    }
}

