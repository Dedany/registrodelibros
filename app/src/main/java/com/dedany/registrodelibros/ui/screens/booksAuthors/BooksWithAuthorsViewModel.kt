package com.dedany.registrodelibros.ui.screens.booksAuthors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedany.domain.entities.Author
import com.dedany.domain.entities.Book
import com.dedany.domain.entities.BookWithAuthors // Asegúrate de que esta importación esté presente
import com.dedany.usecase.bookAuthor.GetBookWithAuthorsUseCase // Este es el que devuelve BookWithAuthors?
import com.dedany.usecase.book.GetAllBooksUseCase
import com.dedany.usecase.book.SetBookFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow // Es buena práctica exponer como StateFlow inmutable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksWithAuthorsViewModel @Inject constructor(
    private val getAllBooksUseCase: GetAllBooksUseCase,
    private val getBookWithAuthorsUseCase: GetBookWithAuthorsUseCase, // Renombrado mentalmente a GetBookWithAuthorsByIdUseCase
    private val setBookFavoriteUseCase: SetBookFavoriteUseCase,
) : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books.asStateFlow() // Exponer como StateFlow

    private val _bookAuthors = MutableStateFlow<Map<String, List<Author>>>(emptyMap())
    val bookAuthors: StateFlow<Map<String, List<Author>>> = _bookAuthors.asStateFlow() // Exponer como StateFlow

    init {
        viewModelScope.launch {
            // 1. Obtener la lista de todos los libros
            val booksList = getAllBooksUseCase()
            _books.value = booksList

            // 2. Para cada libro, obtener sus autores y construir el mapa
            val authorsMapAccumulator = mutableMapOf<String, List<Author>>()
            for (book in booksList) {
                // getBookWithAuthorsUseCase se asume que devuelve BookWithAuthors? para el book.id dado
                val bookWithAuthorsResult: BookWithAuthors? = getBookWithAuthorsUseCase(book.id)

                // Manejar el resultado nullable de forma segura
                // Si bookWithAuthorsResult es null o su lista de autores es null, usa una lista vacía
                authorsMapAccumulator[book.id] = bookWithAuthorsResult?.authors ?: emptyList()
            }
            _bookAuthors.value = authorsMapAccumulator
        }
    }

    fun toggleFavorite(bookId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            setBookFavoriteUseCase(bookId, isFavorite)
            // Opcional: Si quieres reflejar el cambio inmediatamente en la UI sin recargar todo,
            // necesitarías actualizar el estado local de _books o el libro específico dentro de la lista.
            // Ejemplo (requiere que Book tenga isFavorite como var o crear una nueva instancia):
            // _books.update { currentBooks ->
            //     currentBooks.map { book ->
            //         if (book.id == bookId) {
            //             book.copy(isFavorite = isFavorite) // Asumiendo que Book es data class
            //         } else {
            //             book
            //         }
            //     }
            // }
        }
    }

    /**
     * Recarga la lista completa de libros y sus respectivos autores.
     * El parámetro bookId no se usa actualmente en esta implementación si la meta es recargar todo.
     * Si la intención fuera cargar solo un libro y sus autores, la lógica sería diferente,
     * similar a lo que se haría en un BookDetailViewModel.
     */
    fun refreshAllBooksAndAuthors() { // Nombre cambiado para mayor claridad de la acción
        viewModelScope.launch {
            val booksList = getAllBooksUseCase()
            _books.value = booksList

            val authorsMapAccumulator = mutableMapOf<String, List<Author>>()
            for (book in booksList) {
                val bookWithAuthorsResult: BookWithAuthors? = getBookWithAuthorsUseCase(book.id)
                authorsMapAccumulator[book.id] = bookWithAuthorsResult?.authors ?: emptyList()
            }
            _bookAuthors.value = authorsMapAccumulator
        }
    }
}
