import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dedany.registrodelibros.ui.screens.book.GenreBooksViewModel

@Composable
fun GenreScreen(
    genre: String,
    navController: NavController,
    viewModel: GenreBooksViewModel = hiltViewModel()
) {
    val books by viewModel.books.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(genre) {
        viewModel.loadBooksByGenre(genre)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Libros de $genre",
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            books.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No se encontraron libros para este género",
                        fontSize = 18.sp
                    )
                }
            }

            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(books) { book ->
                        Text(
                            text = book.title ?: "Sin título",
                            fontSize = 18.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val encodedId = Uri.encode(book.id)
                                    navController.navigate("bookDetail/$encodedId")
                                }
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
