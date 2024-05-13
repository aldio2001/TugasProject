@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@file:Suppress("DEPRECATION")

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abrila.androidjetpackcompose.R

// Define spacing and padding constants
private val SpacingSmall = 4.dp
private val SpacingMedium = 8.dp
private val SpacingLarge = 16.dp
private val SpacingExtraLarge = 32.dp

// Define typography styles
private val Typography = Typography(
    bodyLarge = TextStyle(fontSize = 20.sp),
    bodyMedium = TextStyle(fontSize = 16.sp),
    bodySmall = TextStyle(fontSize = 14.sp)
)

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text(text = "About") }, // Add title "About" to TopAppBar
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = {
                // Optionally, you can add a navigation icon
                IconButton(onClick = { /* Handle navigation icon click */ }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )
        Spacer(modifier = Modifier.height(SpacingLarge))
        Image(
            painter = painterResource(id = R.drawable.aldio),
            contentDescription = "about_page",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = SpacingLarge) // Add padding between image and text
        )
        UserProfile(
            name = "Aldio Yohanes",
            email = "aldioguire@gmail.com",
            perguruan = "Universitas Muhammadiyah Riau",
            jurusan = "Teknik Informatika",
            modifier = Modifier.padding(top = SpacingLarge) // Add padding between image and text
        )
    }
}

@Composable
fun UserProfile(
    name: String,
    email: String,
    perguruan: String,
    jurusan: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SpacingMedium)
    ) {
        Text(
            text = "Nama : $name",
            style = Typography.bodyLarge,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = SpacingLarge, vertical = SpacingSmall)
        )
        Text(
            text = "Email : $email",
            style = Typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = SpacingLarge, vertical = SpacingSmall)
        )
        Text(
            text = "Perguruan:  $perguruan",
            style = Typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = SpacingLarge, vertical = SpacingSmall)
        )
        Text(
            text = "Jurusan: $jurusan",
            style = Typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = SpacingLarge, vertical = SpacingSmall)
        )
    }
}

@Composable
@Preview
fun ProfileScreenPreview() {
    ProfileScreen()
}
