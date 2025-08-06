package com.dedany.registrodelibros.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedany.domain.entities.Author
import com.dedany.domain.entities.Book
import com.dedany.usecase.bookAuthor.GetBookWithAuthorsUseCase
import com.dedany.usecase.book.GetAllBooksUseCase
import com.dedany.usecase.book.SetBookFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksWithAuthorsViewModel @Inject constructor(
    private val getAllBooksUseCase: GetAllBooksUseCase,
    private val getAuthorsByBookUseCase: GetBookWithAuthorsUseCase,
    private val setBookFavoriteUseCase: SetBookFavoriteUseCase,
) : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    private val _bookAuthors = MutableStateFlow<Map<String, List<Author>>>(emptyMap())
    val bookAuthors: StateFlow<Map<String, List<Author>>> = _bookAuthors

    init {
        viewModelScope.launch {
            val booksList = getAllBooksUseCase()
            _books.value = booksList

            // Para cada libro, obtener autores y guardarlos en el mapa
            val map = mutableMapOf<String, List<Author>>()
            for (book in booksList) {
                val authorsWithBook = getAuthorsByBookUseCase(book.id)
                map[book.id] = authorsWithBook.authors
            }
            _bookAuthors.value = map
        }
    }

    fun toggleFavorite(bookId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            setBookFavoriteUseCase(bookId, isFavorite)
            // Aquí puedes actualizar el estado local si quieres reflejar el cambio inmediatamente
        }
    }
    fun loadBookById(bookId: String) {
        viewModelScope.launch {
            val booksList = getAllBooksUseCase()
            _books.value = booksList

            val authorsMap = mutableMapOf<String, List<Author>>()
            for (book in booksList) {
                val authors = getAuthorsByBookUseCase(book.id).authors
                authorsMap[book.id] = authors
            }
            _bookAuthors.value = authorsMap
        }
    }
}
