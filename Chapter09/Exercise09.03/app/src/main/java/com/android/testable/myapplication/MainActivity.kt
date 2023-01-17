package com.android.testable.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.testable.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    Column(modifier = Modifier.padding(16.dp)) {
                        MyApp(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun MyApp(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "itemCountScreen") {
        composable("itemCountScreen") {
            ItemCountScreen { navController.navigate("itemScreen/?itemCount=$it") }
        }
        composable(
            "itemScreen/?itemCount={itemCount}",
            arguments = listOf(navArgument("itemCount") { type = NavType.StringType })
        ) {
            ItemScreen(
                it.arguments?.getString("itemCount").orEmpty()
            )
        }
    }
}
