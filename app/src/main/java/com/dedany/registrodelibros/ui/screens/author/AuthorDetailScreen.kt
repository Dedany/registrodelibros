package com.dedany.registrodelibros.ui.screens.author

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dedany.domain.entities.Book

@Composable
fun AuthorDetailScreen(
    authorId: String,
    navController: NavController,
    viewModel: AuthorDetailViewModel = hiltViewModel()
) {
    val author by viewModel.author.collectAsState()
    val books by viewModel.booksByAuthor.collectAsState()

    // Cargar datos si cambia el authorId
    LaunchedEffect(authorId) {
        Log.d("AuthorDetailScreen", "Cargando datos de autor con id: $authorId")
        viewModel.loadAuthorById(authorId)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        author?.let { authorData ->
            // Limpiar bio
            val cleanBio = authorData.bio
                ?.replace(Regex("<[^>]*>"), "")
                ?.replace("\n", " ")
                ?.trim()

            Text(
                text = authorData.name,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Text(
                text = "Fecha nacimiento: ${authorData.birthDate ?: "Desconocida"}",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = "Biografía: ${cleanBio ?: "No disponible"}",
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Obras:",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            when {
                books.isEmpty() -> {
                    Text(
                        text = "No se encontraron obras para este autor.",
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                else -> {
                    LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
                        items(books) { book ->
                            BookItemInAuthorDetail(book = book, navController = navController)
                        }
                    }
                }
            }
        } ?: run {
            // Mostrar mientras carga el autor
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
            Text(
                text = "Cargando detalles del autor...",
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun BookItemInAuthorDetail(book: Book, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { navController.navigate("bookDetail/${book.id}") } // 🔗 Navegación
    ) {
        Text(
            text = book.title ?: "Título Desconocido",
            fontWeight = FontWeight.SemiBold
        )
        book.publishYear?.let {
            Text(text = "Publicado en: $it", fontSize = 12.sp)
        }
    }
}
