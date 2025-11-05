package com.dedany.registrodelibros.ui.screens.book

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedany.domain.entities.Book
import com.dedany.domain.entities.BookWithAuthors
import com.dedany.usecase.author.CoordinatorUseCase
import com.dedany.usecase.book.GetBookByIdUseCase
import com.dedany.usecase.book.SetBookFavoriteUseCase
import com.dedany.usecase.bookAuthor.GetBookWithAuthorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val getBookWithAuthorsByIdUseCase: GetBookWithAuthorsUseCase,
    private val getBookByIdUseCase: GetBookByIdUseCase,
    private val bookCoordinatorUseCase: CoordinatorUseCase,
    private val setBookFavoriteUseCase: SetBookFavoriteUseCase
) : ViewModel() {

    private val _book = MutableStateFlow<BookWithAuthors?>(null)
    val book: StateFlow<BookWithAuthors?> = _book.asStateFlow()

    fun loadBookById(id: String) {
        // --- LA LÓGICA DEBE ESTAR DIRECTAMENTE AQUÍ, NO DENTRO DE OTRA FUNCIÓN ANIDADA ---
        viewModelScope.launch {
            Log.i(
                "ViewModelFlow",
                "[${Thread.currentThread().name}] ViewModel.loadBookById - INICIO para ID: $id. Llamando a PrepareAndGetBookDetailsUseCase."
            )
            // --- LLAMADA ÚNICA AL CASO DE USO COORDINADOR ---
            _book.value = bookCoordinatorUseCase(id)

            Log.i(
                "ViewModelFlow",
                "[${Thread.currentThread().name}] ViewModel.loadBookById - _book.value actualizado: ${_book.value?.book?.title}, Autores: ${_book.value?.authors?.joinToString { it.name }}"
            )
            if (_book.value?.authors.isNullOrEmpty() && _book.value != null) {
                Log.e(
                    "ViewModelFlow",
                    "[${Thread.currentThread().name}]   ¡¡¡ERROR CRÍTICO!!! BookWithAuthors cargado pero la lista de autores está vacía para el libro: ${_book.value?.book?.title} (ID Libro: ${_book.value?.book?.id})"
                )
            } else if (_book.value != null && _book.value!!.authors.isNotEmpty()) {
                Log.i(
                    "ViewModelFlow",
                    "[${Thread.currentThread().name}]   ¡ÉXITO! Libro '${_book.value!!.book.title}' cargado con ${_book.value!!.authors.size} autores."
                )
            } else if (_book.value == null) {
                Log.e(
                    "ViewModelFlow",
                    "[${Thread.currentThread().name}]   ¡¡¡ERROR CRÍTICO!!! _book.value es NULL después de llamar al caso de uso para ID: $id"
                )
            }
        }
    }
    fun toggleFavorite(bookId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            setBookFavoriteUseCase(bookId, isFavorite)
            _book.value?.let { current ->
                _book.value = current.copy(
                    book = current.book.copy(isFavorite = isFavorite)
                )
            }
        }
    }
}