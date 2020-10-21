package com.example.android.navigation

class Score(startScore: Int) {
    var score = startScore
        private set

    var played = false

    fun isValid() : Boolean {
        return this.played
    }

    fun increment() {
        this.played = true
        this.score++
    }
}