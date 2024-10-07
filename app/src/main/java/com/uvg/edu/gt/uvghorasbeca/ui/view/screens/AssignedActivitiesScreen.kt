import android.annotation.SuppressLint
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.CustomCard
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.TopAppBar
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.BottomNavigationBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.uvg.edu.gt.uvghorasbeca.ui.theme.RedImportant
import com.uvg.edu.gt.uvghorasbeca.ui.theme.YellowMedium
import com.uvg.edu.gt.uvghorasbeca.ui.theme.NotImportantColor

// Data class to hold activity information
data class Activity(
    val title: String,
    val location: String,
    val date: String,
    val timeRange: String,
    val backgroundColor: Color
)

// Mock activities
val activities = listOf(
    Activity("Tutorías", "CIT - 126", "16 / 06 / 2024", "14:00 - 17:30", RedImportant),
    Activity("Capacitación", "DHIVE", "20 / 06 / 2024", "08:00 - 10:40", YellowMedium),
    Activity("Staff de delvas", "CIT - 336", "27 / 06 / 2024", "13:00 - 14:00", NotImportantColor)
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AssignedActivitiesScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
//        topBar = { TopAppBar() },
//        bottomBar = { BottomNavigationBar(navController) }
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            SearchBar(searchQuery) { newQuery ->
                searchQuery = newQuery
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(activities.filter {
                    it.title.contains(searchQuery, ignoreCase = true)
                }) { activity ->
                    // Componente CustomCard
                    CustomCard(
                        title = activity.title,
                        location = activity.location,
                        date = activity.date,
                        timeRange = activity.timeRange,
                        totalHours = null,
                        backgroundColor = activity.backgroundColor,
                        showRating = false
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        textStyle = TextStyle(fontSize = 18.sp),
        modifier = Modifier
            .background(Color.LightGray, MaterialTheme.shapes.small)
            .padding(16.dp)
            .fillMaxWidth()
    )
}

// Componente de CustomCard
@Composable
fun CustomCard(
    title: String,
    location: String,
    date: String,
    timeRange: String,
    totalHours: Int?,
    backgroundColor: Color,
    showRating: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = "Lugar: $location", fontSize = 16.sp)
            Text(text = "Fecha: $date", fontSize = 16.sp)
            Text(text = "Hora: $timeRange", fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAssignedActivitiesScreen() {
    val navController = rememberNavController()
    AssignedActivitiesScreen(navController)
}
