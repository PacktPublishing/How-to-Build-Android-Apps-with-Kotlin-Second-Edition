package com.android.testable.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    MyApp(navController)
                }
            }
        }
    }
}

@Composable
fun MyApp(navController: NavHostController) {
    var rowCount by rememberSaveable {
        mutableStateOf("")
    }
    NavHost(navController = navController, startDestination = "insertRowsScreen") {
        composable("insertRowsScreen") {
            InsertRowsScreen {
                rowCount = it
                navController.navigate("insertColumnsScreen")
            }
        }
        composable("insertColumnsScreen") {
            InsertColumnsScreen { navController.navigate("gridScreen/?rowCount=$rowCount&columnCount=$it") }
        }
        composable(
            "gridScreen/?rowCount={rowCount}&columnCount={columnCount}",
            arguments = listOf(navArgument("rowCount") { type = NavType.StringType },
                navArgument("columnCount") { type = NavType.StringType })
        ) {
            GridScreen(
                it.arguments?.getString("rowCount").orEmpty(),
                it.arguments?.getString("columnCount").orEmpty()
            )
        }
    }
}
