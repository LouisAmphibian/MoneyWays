package com.example.moneywaysapp.manager


//(gamification logic)
object RewardsManager {
    fun getBadge(totalSpent: Double): Pair<String, String> {
        return when {
            totalSpent < 1000 -> "Bronze Saver" to "Great job staying under budget! 🥉"
            totalSpent < 5000 -> "Silver Saver" to "Well done, keep it up! 🥈"
            else -> "Gold Saver" to "Excellent savings habit! 🥇"
        }
    }
}