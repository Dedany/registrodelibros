package com.dedany.registrodelibros.ui.screens.booksAuthors

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage


@Composable
fun BooksWithAuthorsScreen(
    viewModel: BooksWithAuthorsViewModel = hiltViewModel()
) {
    val books by viewModel.books.collectAsState()
    val bookAuthors by viewModel.bookAuthors.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.padding(8.dp)
    ) {
        items(books, key = { it.id }) { book ->
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = book.coverId?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" },
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f / 3f) // Portada estilo libro
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = book.title ?: "Sin título",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}