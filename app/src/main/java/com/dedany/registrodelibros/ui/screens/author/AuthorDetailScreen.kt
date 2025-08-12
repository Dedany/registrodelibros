package com.dedany.registrodelibros.ui.screens.author

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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

@Composable
fun AuthorDetailScreen(
    authorId: String,
    navController: NavController,
    viewModel: AuthorDetailViewModel = hiltViewModel()
) {
    val author by viewModel.author.collectAsState()

    // Cargar datos del autor cuando cambie el authorId
    LaunchedEffect(authorId) {
        viewModel.loadAuthorById(authorId)
    }

    author?.let { authorData ->
        // Limpiar la bio por si viene en HTML o con saltos de línea extraños
        val cleanBio = authorData.bio
            ?.replace(Regex("<[^>]*>"), "") // Quita etiquetas HTML
            ?.replace("\n", " ") // Sustituye saltos de línea por espacios
            ?.trim()

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = authorData.name,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Text(
                text = "Fecha nacimiento: ${authorData.birthDate ?: "Desconocida"}",
                fontSize = 16.sp
            )
            Text(
                text = "Biografía: ${cleanBio ?: "No disponible"}",
                fontSize = 14.sp
            )
        }
    } ?: Text("Cargando detalles del autor...")
}
