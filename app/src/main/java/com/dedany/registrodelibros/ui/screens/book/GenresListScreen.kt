package com.dedany.registrodelibros.ui.screens.book


import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun GenresListScreen(navController: NavController) {
    val generos = listOf(
        "Fantasy",
        "Science Fiction", "Romance",
        "Mystery",
        "Horror",
        "Thriller",
        "Adventure",
        "Classic",
        // No Ficción Popular
        "History",
        "Science",
        "Biography",
        "Psychology",
        // Otros
        "Kids Books",
        "Cooking",
        "Art",
        "Poetry"
    ).distinct() // .distinct() por si acaso para eliminar duplicados


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Selecciona tu género",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                items(generos) { genero ->
                    Button(
                        onClick = {
                            val encodedGenre = Uri.encode(genero)
                            navController.navigate("generos/$encodedGenre")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(
                            text = genero,
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}