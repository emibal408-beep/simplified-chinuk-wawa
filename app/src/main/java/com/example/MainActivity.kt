package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.UserProgressRepository
import com.example.ui.GameDifficultyScreen
import com.example.ui.GameScreen
import com.example.ui.HomeScreen
import com.example.ui.LearnCategoriesScreen
import com.example.ui.LearnDictionaryScreen
import com.example.ui.LearnNumbersScreen
import com.example.ui.Screen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "chinuk_wawa_database"
        ).build()
    }
    private val repository by lazy { UserProgressRepository(db.userProgressDao()) }
    private val viewModel: AppViewModel by viewModels { AppViewModelFactory(repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                ChinukWawaApp(viewModel)
            }
        }
    }
}

@Composable
fun ChinukWawaApp(viewModel: AppViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToLearn = { navController.navigate(Screen.LearnCategories.route) },
                onNavigateToGame = { navController.navigate(Screen.GameDifficulty.route) },
                onNavigateToFlashcards = { navController.navigate(Screen.FlashcardsMenu.route) },
                onNavigateToWordMatch = { navController.navigate(Screen.WordMatch.route) }
            )
        }
        composable(Screen.LearnCategories.route) {
            LearnCategoriesScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCategory = { route -> navController.navigate(route) }
            )
        }
        composable(Screen.LearnNumbers.route) {
            LearnNumbersScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.LearnDictionary.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: "ALL"
            LearnDictionaryScreen(
                viewModel = viewModel,
                categoryString = category,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.GameDifficulty.route) {
            GameDifficultyScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToGame = { route -> navController.navigate(route) }
            )
        }
        composable(
            route = Screen.GamePlay.route,
            arguments = listOf(navArgument("difficulty") { type = NavType.StringType })
        ) { backStackEntry ->
            val difficulty = backStackEntry.arguments?.getString("difficulty") ?: "EASY"
            GameScreen(
                difficulty = difficulty,
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.FlashcardsMenu.route) {
            com.example.ui.FlashcardsMenuScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToFlashcards = { category -> navController.navigate(Screen.Flashcards.createRoute(category)) }
            )
        }
        composable(
            route = Screen.Flashcards.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: "WORDS"
            com.example.ui.FlashcardsScreen(
                category = category,
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.WordMatch.route) {
            com.example.ui.WordMatchScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
