import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import com.lowjunee.healthsafe.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentsScreen(
    onBackClick: () -> Unit,
    onDoctorClick: (String, String) -> Unit // Pass doctor's name and clinic
) {
    var doctors by remember { mutableStateOf(listOf<Map<String, Any>>()) }
    val firestore = FirebaseFirestore.getInstance()

    // Fetch doctors from Firestore
    LaunchedEffect(Unit) {
        firestore.collection("doctors")
            .get()
            .addOnSuccessListener { result ->
                doctors = result.map { it.data }
            }
            .addOnFailureListener { e ->
                println("Error fetching doctors: ${e.message}")
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Find a Doctor", color = Color.White, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = PrimaryColor)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(16.dp)
                .padding(bottom = 64.dp)
        ) {
            if (doctors.isEmpty()) {
                // Show empty state message
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No doctors available.",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            } else {
                // List of doctors
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    doctors.forEach { doctor ->
                        val name = doctor["name"] as? String ?: "Unknown"
                        val clinic = doctor["clinic"] as? String ?: "Unknown"
                        val expertise = doctor["expertise"] as? String ?: "Unknown"
                        val imageUrl = doctor["image"] as? String ?: ""

                        DoctorCard(
                            name = name,
                            clinic = clinic,
                            expertise = expertise,
                            imageUrl = imageUrl, // Pass the image URL
                            onClick = { onDoctorClick(name, clinic) } // Pass name and clinic
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun DoctorCard(
    name: String,
    clinic: String,
    expertise: String,
    imageUrl: String, // Add image URL parameter
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Load doctor image
            AsyncImage(
                model = imageUrl,
                contentDescription = "Doctor Image",
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontSize = 18.sp, color = Color.Black)
                Text(text = clinic, fontSize = 14.sp, color = Color.Gray)
                Text(text = expertise, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}
