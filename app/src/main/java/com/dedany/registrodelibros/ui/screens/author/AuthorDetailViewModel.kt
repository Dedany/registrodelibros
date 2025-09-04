package com.dedany.registrodelibros.ui.screens.author

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedany.domain.entities.Author
import com.dedany.domain.entities.Book
import com.dedany.usecase.author.GetAuthorByIdUseCase
import com.dedany.usecase.author.GetBooksByAuthorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthorDetailViewModel  @Inject constructor(
    private val getAuthorByIdUseCase: GetAuthorByIdUseCase,
    private val getBooksByAuthorUseCase: GetBooksByAuthorUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _author = MutableStateFlow<Author?>(null)
    val author: StateFlow<Author?> = _author.asStateFlow()

    private val _booksByAuthor = MutableStateFlow<List<Book>>(emptyList())
    val booksByAuthor: StateFlow<List<Book>> = _booksByAuthor.asStateFlow()

    private val authorIdFromNav: String? = savedStateHandle.get<String>("authorId")

    init {
        Log.d("AuthorDetailVM", "ViewModel init. authorIdFromNav: $authorIdFromNav")

        authorIdFromNav?.let { idQueLlegoConPrefijo ->
            // Limpiamos el ID quitándole la parte "/authors/" del principio
            val idClean = idQueLlegoConPrefijo.substringAfterLast('/')

            if (idClean.isNotBlank()) {
                Log.i("AuthorDetailVM", "ID limpio: '$idClean'. Original: '$idQueLlegoConPrefijo'. Cargando datos...")
                // Llamamos a las funciones de carga con el ID ya limpio
                loadAuthorById(idClean)      // Renombrada para claridad, ver abajo
                loadBooksForAuthor(idClean)  // Renombrada para claridad, ver abajo
            } else {
                Log.w("AuthorDetailVM", "El ID '$idQueLlegoConPrefijo' no se pudo limpiar o estaba vacío después de limpiar.")
                // Aquí podrías establecer un estado de error si lo deseas
            }
        } ?: Log.e("AuthorDetailVM", "authorId no se encontró en SavedStateHandle. Revisa la clave 'authorId' en tu grafo de navegación.")
    }

    fun loadAuthorById(idClean: String) {
        viewModelScope.launch {
            Log.d("AuthorDetailVM", "loadAuthorById ejecutado para ID: $idClean")
            try {
                _author.value = getAuthorByIdUseCase(idClean) // Usa el ID limpio
                Log.d("AuthorDetailVM", "Detalles del autor cargados: ${_author.value?.name}")
                if (_author.value == null) {
                    Log.w("AuthorDetailVM", "GetAuthorByIdUseCase devolvió null para ID: $idClean")
                }
            } catch (e: Exception) {
                Log.e("AuthorDetailVM", "Error en loadAuthorByIdInternal para $idClean", e)
                _author.value = null // O maneja el error de otra forma
            }
            // YA NO SE LLAMA A loadBooksForAuthor DESDE AQUÍ
        }
    }

    private fun loadBooksForAuthor(idClean: String) {
        viewModelScope.launch {
            Log.d("AuthorDetailVM", "loadBooksForAuthor ejecutado para ID: $idClean")
            try {
                _booksByAuthor.value = getBooksByAuthorUseCase(idClean)
                Log.d(
                    "AuthorDetailVM",
                    "Libros cargados para $idClean: ${_booksByAuthor.value.size} libros"
                )
            } catch (e: Exception) {
                Log.e("AuthorDetailVM", "Error en loadBooksForAuthor para $idClean", e)
                _booksByAuthor.value = emptyList()
            }
        }
    }

}