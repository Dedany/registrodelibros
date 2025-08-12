package com.dedany.registrodelibros.ui.screens.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedany.domain.entities.Book
import com.dedany.usecase.book.GetAllBooksUseCase
import com.dedany.usecase.book.GetBooksByTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val getAllBooks: GetAllBooksUseCase,
    private val getBooksByTitle: GetBooksByTitleUseCase
) : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    init {
        loadAllBooks()
    }

    fun loadAllBooks() {
        viewModelScope.launch {
            _books.value = getAllBooks()
        }
    }

    fun searchBooks(title: String) {
        viewModelScope.launch {
            if (title.isBlank()) {
                loadAllBooks()
            } else {
                _books.value = getBooksByTitle(title)

            }
        }
    }
}