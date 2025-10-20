import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.Module

@Composable
fun BoxWithColumnClickableBoxes(
    onGeneroClick: () -> Unit = {},
    onAutoresClick: () -> Unit = {},
    onLibrosClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Welcome to BiblioBox",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 200.dp))
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.8f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color.LightGray)
                    .clickable { onGeneroClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Genre")
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color.LightGray)
                    .clickable { onAutoresClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Authors")
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color.LightGray)
                    .clickable { onLibrosClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Books")
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(Color.LightGray)
                    .clickable { onGeneroClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "My Space",
                    color = Color.White,

                )
            }
        }

    }

}




@Composable
fun BookCard(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() }
            .background(Color(0xFFEDEDED), shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text)
    }
}
