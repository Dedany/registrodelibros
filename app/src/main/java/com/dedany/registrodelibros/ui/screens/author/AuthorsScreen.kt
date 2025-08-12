package com.dedany.registrodelibros.ui.screens.author

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import android.net.Uri


@Composable
fun AuthorsScreen(
    viewModel: AuthorsViewModel,
    navController: NavController
) {
    val authors = viewModel.authors.collectAsState()
    val query = viewModel.query.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        // Buscador
        OutlinedTextField(
            value = query.value,
            onValueChange = { viewModel.onQueryChanged(it) },
            label = { Text("Buscar autor") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))


        val letters = ('A'..'Z').toList()
        val chunked = letters.chunked(2)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            chunked.forEach { columnLetters ->
                Column {
                    columnLetters.forEach { letter ->
                        Text(
                            text = letter.toString(),
                            modifier = Modifier
                                .padding(1.dp)
                                .clickable { viewModel.loadAuthors(letter.toString()) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn {
            items(authors.value) { author ->
                Text(
                    text = author.name,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            val encodedId = Uri.encode(author.id)
                            navController.navigate("authorDetail/$encodedId")
                        }
                )
            }
        }
    }
}
