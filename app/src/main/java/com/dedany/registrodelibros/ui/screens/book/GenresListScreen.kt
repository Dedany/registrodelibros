package com.dedany.registrodelibros.ui.screens.book

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun GenresListScreen(navController: NavController) {
    // Lista ampliada a 24 géneros para un layout más completo
    val generos = listOf(
        // Ficción
        "Fantasy", "Science Fiction", "Romance",
        "Mystery", "Horror", "Thriller",
        "Adventure", "Classic", "Historical Fiction",
        "Humor", "Poetry", "Plays",

        // No Ficción
        "History", "Science", "Biography",
        "Psychology", "Philosophy", "Art",
        "Cooking", "Health", "Travel",
        "Technology", "Business", "Politics"
    ).distinct() // .distinct() por si acaso

    // Lógica de división actualizada para 12/12
    val topGenres = generos.take(12)
    val bottomGenres = generos.drop(12)

    var dropTargetBounds by remember { mutableStateOf<Rect?>(null) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // 1. Pinta los 12 géneros de arriba
            items(topGenres) { genero ->
                DraggableGenreBox(
                    genero = genero,
                    dropTargetBounds = dropTargetBounds
                ) { droppedGenre ->
                    val encoded = Uri.encode(droppedGenre)
                    navController.navigate("generos/$encoded")
                }
            }

            // 2. Pinta el hueco central, ocupando las 3 columnas
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(3) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .onGloballyPositioned { coords ->
                            dropTargetBounds = coords.boundsInWindow()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(
                                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                                CircleShape
                            )
                            .border(
                                2.dp,
                                MaterialTheme.colorScheme.primary,
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Arrastra aquí",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            // 3. Pinta los 12 géneros de abajo
            items(bottomGenres) { genero ->
                DraggableGenreBox(
                    genero = genero,
                    dropTargetBounds = dropTargetBounds
                ) { droppedGenre ->
                    val encoded = Uri.encode(droppedGenre)
                    navController.navigate("generos/$encoded")
                }
            }
        }
    }
}
