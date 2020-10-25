package com.example.android.navigation.game.quiz

import android.text.Editable
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.navigation.game.quiz.data.Question
import com.example.android.navigation.tools.Timer

abstract class QuizViewModel(var questions: MutableList<Question>, var timerSeconds: Int) : ViewModel() {

    enum class GameState {
        LOST, WON, ONGOING
    }

    val timer = Timer(timerSeconds)

    // TODO: encapsulate
    var gameState = MutableLiveData<GameState>()
    var currentQuestion = MutableLiveData<Question>()
    var score = MutableLiveData<Int>()
    var answers = MutableLiveData<MutableList<String>>()
    var hasPlayed = MutableLiveData<Boolean>()

    var questionIndex = 0
        protected set

    val numQuestions = Math.min((questions.size + 1) / 2, 3)

    init {
        score.value = -1
    }

    fun restartQuiz() {
        gameState.value = GameState.ONGOING
        score.value = 0
        questionIndex = 0
    }

    // randomize the questions and set the first question
    fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    fun setQuestion() {
        currentQuestion.value = this.questions[questionIndex]
        // randomize the answers into a copy of the array
        answers.value = this.currentQuestion.value!!.answers.toMutableList()
        // and shuffle them
        answers.value?.shuffle()
    }

    fun restartTimer() {
        timer.stopTimer()
        timer.startTimer()
    }

    fun handleAnswer(answer: String) {
        hasPlayed.value = true
        restartTimer()
        if (answer.trim() == currentQuestion.value?.answers?.get(0)) {
            questionIndex++
            score.value = score.value?.plus(1)
            if (questionIndex < numQuestions) {
                currentQuestion.value = questions[questionIndex]
                setQuestion()
            } else {
                gameState.value = GameState.WON
            }
        } else {
            gameState.value = GameState.LOST
        }
    }
}