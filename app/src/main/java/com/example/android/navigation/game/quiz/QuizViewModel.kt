package com.example.android.navigation.game.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.android.navigation.game.quiz.data.Question
import com.example.android.navigation.tools.Timer

abstract class QuizViewModel(var questions: MutableList<Question>, var timerSeconds: Int) : ViewModel() {

    enum class GameState {
        LOST, WON, ONGOING
    }

    val timer = Timer(timerSeconds)

    private var gameState = MutableLiveData<GameState>()
    var finalGameState = Transformations.map(timer.secondsCount) { timerSeconds ->
        if (timerSeconds < 0) {
            gameState.value = GameState.LOST
        }
        gameState
    }
        private set

    private var _currentQuestion = MutableLiveData<Question>()
    val currentQuestion : LiveData<Question>
        get() = _currentQuestion

    private var _score = MutableLiveData<Int>()
    val score : LiveData<Int>
        get() = _score

    private var _answers = MutableLiveData<MutableList<String>>()
    val answers : LiveData<MutableList<String>>
        get() = _answers

    var questionIndex = 0
        protected set

    val numQuestions = Math.min((questions.size + 1) / 2, 3)

    init {
        _score.value = -1
    }

    fun restartQuiz() {
        gameState.value = GameState.ONGOING
        _score.value = 0
        questionIndex = 0
    }

    fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    fun setQuestion() {
        _currentQuestion.value = this.questions[questionIndex]
        // randomize the answers into a copy of the array
        _answers.value = this.currentQuestion.value!!.answers.toMutableList()
        // and shuffle them
        _answers.value?.shuffle()
    }

    fun restartTimer() {
        timer.stopTimer()
        timer.startTimer()
    }

    fun handleAnswer(answer: String) {
        restartTimer()
        if (answer.trim() == _currentQuestion.value?.answers?.get(0)) {
            questionIndex++
            _score.value = score.value?.plus(1)
            if (questionIndex < numQuestions) {
                _currentQuestion.value = questions[questionIndex]
                setQuestion()
            } else {
                gameState.value = GameState.WON
            }
        } else {
            gameState.value = GameState.LOST
        }
    }
}