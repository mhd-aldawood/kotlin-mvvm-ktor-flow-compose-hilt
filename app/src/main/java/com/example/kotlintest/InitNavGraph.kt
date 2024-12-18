package com.example.kotlintest

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlintest.screens.users.UserScreen
import com.example.kotlintest.ui.theme.KotlinTestTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun InitNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startDestination: String = NavDestination.USERS
) {
    NavHost(
        navController = navController, startDestination = startDestination, modifier = modifier
    ) {
        composable("first_screen") {
            Log.d("MyComposable", "first screen")
            FirstScreen()
        }
        composable("second_screen") {
            Log.d("MyComposable", "second screen")
            SecondScreen()
        }
        composable("init") {
            Log.d("MyComposable", "third screen")
            InitialScreen(navController)
        }
        composable("USERS") {
            Log.d("MyComposable", "fourth screen")
            UserScreen()
        }
    }

}

@Composable
fun InitialScreen(navController: NavHostController) {
    // Layout for the first screen with two buttons
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "Initial Screen",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )
        Button(onClick = {
            // Navigate to the second screen
            navController.navigate("second_screen")
        }) {
            Text("Go to Second Screen")
        }

        Button(onClick = {
            // Navigate to the first screen (or any other action if needed)
            navController.navigate("first_screen")
        }) {
            Text("GO to first screen")
        }
    }
}

@Composable
fun FirstScreen() {
    // Layout for the first screen with two buttons
    Column(modifier = Modifier.padding(16.dp)) {
        Text("First Screen")
    }
}

@Composable
fun SecondScreen() {
    // Layout for the second screen
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Second Screen")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KotlinTestTheme {
        InitNavGraph()
    }
}
