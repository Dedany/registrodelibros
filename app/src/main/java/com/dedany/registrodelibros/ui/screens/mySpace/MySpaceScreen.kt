package com.dedany.registrodelibros.ui.screens.mySpace

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dedany.domain.entities.Book


@Composable
fun MySpaceScreen(viewModel: MySpaceViewModel) {
    // Observamos el StateFlow como Compose State
    val favoriteBooks = viewModel.favoriteBooks.collectAsState()

    LazyColumn {
        items(favoriteBooks.value) { book ->
            Text(text = book.title ?: "Sin título")
        }
    }

    // Cargar los favoritos cuando la pantalla se crea
    LaunchedEffect(Unit) {
        viewModel.loadFavoriteBooks()
    }
}




@Composable
fun MySpaceScreenPreview() {
    val fakeBooks = listOf(
        Book(
            id = "1",
            title = "Harry Potter y la piedra filosofal",
            coverId = 123,
            publishYear = 1997,
            description = "El inicio de la saga del joven mago.",
            subjects = listOf("Fantasía", "Aventura"),
            rating = 8,
            isRead = false,
            isFavorite = true
        ),
        Book(
            id = "2",
            title = "El Hobbit",
            coverId = 456,
            publishYear = 1937,
            description = "La gran aventura de Bilbo Bolsón.",
            subjects = listOf("Fantasía", "Clásico"),
            rating = 7,
            isRead = true,
            isFavorite = false
        ),
        Book(
            id = "3",
            title = "El Principito",
            coverId = 789,
            publishYear = 1943,
            description = "Un cuento filosófico sobre la infancia y la amistad.",
            subjects = listOf("Filosofía", "Infancia"),
            rating = 4,
            isRead = true,
            isFavorite = true
        )
    )

    LazyColumn {
        items(fakeBooks) { book ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = book.title ?: "Sin título",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }



    LazyColumn {
        items(fakeBooks) { book ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = book.title ?: "Sin título",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMySpaceScreen() {
    MySpaceScreenPreview()
}
