package com.example.ui

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object LearnCategories : Screen("learn_categories")
    object LearnNumbers : Screen("learn_numbers")
    object LearnDictionary : Screen("learn_dictionary/{category}") {
        fun createRoute(category: String) = "learn_dictionary/$category"
    }
    object GameDifficulty : Screen("game_difficulty")
    object GamePlay : Screen("game_play/{difficulty}") {
        fun createRoute(difficulty: String) = "game_play/$difficulty"
    }
    object FlashcardsMenu : Screen("flashcards_menu")
    object Flashcards : Screen("flashcards/{category}") {
        fun createRoute(category: String) = "flashcards/$category"
    }
    object WordMatch : Screen("word_match")
}
