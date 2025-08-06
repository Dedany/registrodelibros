package com.dedany.registrodelibros.ui.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dedany.domain.entities.Book
import com.dedany.registrodelibros.ui.viewmodel.BooksViewModel

@Composable
fun BooksScreen(
    viewModel: BooksViewModel,
    navController: NavController
) {
    val books by viewModel.books.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.searchBooks(it)
            },
            label = { Text("Buscar por título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(books) { book ->
                val safeId = book.id.removePrefix("/") // ✅ Limpieza del ID aquí
                Log.d("BooksScreen", "Navigating with safeId: $safeId")
                BookItem(book = book) {
                    val safeId = Uri.encode(book.id)
                    navController.navigate("bookDetail/$safeId")
                }
            }
        }
    }
}

@Composable
fun BookItem(
    book: Book,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = book.coverId?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" },
                contentDescription = "Portada de ${book.title ?: "Libro"}",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = book.title ?: "Sin título",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1
            )
            Text(
                text = book.author?.joinToString(", ") ?: "Autor desconocido",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1
            )
        }
    }
}
