package com.example.kotlintest.screens.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kotlintest.data.source.network.Address
import com.example.kotlintest.data.source.network.User

@Composable
fun UserScreen(viewModel: UserViewModel=hiltViewModel()) {
    val result by viewModel.response.collectAsStateWithLifecycle()

    if(result is ApiResult.Success)
        (result as ApiResult.Success).data?.let { UserDetailsScreen(users = it) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(users: List<User>) {
    // Scaffold with top bar
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Details") },
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(users) { user ->
                UserDetailsCard(user)
            }
        }
    }
}

@Composable
fun UserDetailsCard(user: User) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // User Name
            Text(
                text = "${user.FirstName} ${user.LastName}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Date of Birth
            Text(
                text = "Date of Birth: ${user.DateOfBirth}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Address Header
            Text(
                text = "Address:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Address Details
            Text(
                text = "${user.Address?.HouseNumber}, ${user.Address?.Street}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "${user.Address?.State}, ${user.Address?.ZipCode}",
                style = MaterialTheme.typography.bodyMedium
            )
            user.Address?.Country?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
