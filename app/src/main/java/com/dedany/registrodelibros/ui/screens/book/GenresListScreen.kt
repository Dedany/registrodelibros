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
        "Science Fiction",
        "Romance",
        "Mystery",
        "Horror",
        "Thriller",
        "Historical Fiction",
        "Young Adult",
        "Literature",
        "Adventure",
        "Action",
        "Comedy",
        "Drama",
        "War",
        "Western",
        "Crime",
        "Suspense",
        "Magic",
        "Humor",
        "Biography",
        "Autobiographies",
        "History",
        "Philosophy",
        "Science",
        "Self-help",
        "Psychology",
        "Religion",
        "Politics",
        "Economics",
        "Business",
        "Management",
        "Finance",
        "Technology",
        "Medicine",
        "Nature",
        "Environment",
        "Travel",
        "Sports",
        "Mathematics",
        "Physics",
        "Chemistry",
        "Biology",
        "Programming",
        "Computer Science",
        "Engineering",
        "Education",
        "Geography",
        "Sociology",
        "Anthropology",
        "Archaeology",
        "Political Science",
        "Art",
        "Art History",
        "Music",
        "Photography",
        "Film",
        "Theater",
        "Dance",
        "Design",
        "Fashion",
        "Architecture",
        "Painting",
        "Art criticism",
        "Poetry",
        "Plays",
        "Short Stories",
        "Reference",
        "Cookbooks",
        "Cooking",
        "Health",
        "Exercise",
        "Nutrition",
        "Mental Health",
        "Children",
        "Kids Books",
        "Picture Books",
        "Baby Books",
        "Bedtime Books",
        "Stories in Rhyme",
        "Christianity",
        "Bible",
        "Theology",
        "Mythology",
        "Folklore",
        "Legends",
        "Ethics",
        "English Language",
        "Linguistics",
        "Classic"
    )

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