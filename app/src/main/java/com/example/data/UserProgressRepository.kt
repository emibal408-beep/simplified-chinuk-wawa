package com.example.data

import kotlinx.coroutines.flow.Flow

class UserProgressRepository(private val dao: UserProgressDao) {
    val progress: Flow<UserProgress?> = dao.getProgress()

    suspend fun saveScore(score: Int) {
        dao.insertProgress(UserProgress(id = 1, knowledgeScore = score))
    }
}
