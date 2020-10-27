package com.example.android.navigation.game.quiz

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.android.navigation.R

abstract class QuizFragment(val name: Name) : Fragment() {

    enum class Name {
        RADIO, EDITTEXT
    }

    protected lateinit var viewModel: QuizViewModel

    protected fun setup() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, viewModel.questionIndex + 1, viewModel.numQuestions)

        viewModel.restartQuiz()

        // Act on game state change using GameState and abstract functions for won and lost.
        viewModel.finalGameState.observe(this, Observer { finalGameState ->
            when (finalGameState.value) {
                QuizViewModel.GameState.LOST -> lost()
                QuizViewModel.GameState.WON -> won()
                else -> {}
            }
        })

        viewModel.randomizeQuestions()
        viewModel.restartTimer()
    }

    override fun onStart() {
        super.onStart()
        viewModel.timer.resumeTimer()
    }

    override fun onStop() {
        super.onStop()
        viewModel.timer.stopTimer()
    }

    abstract fun lost()

    abstract fun won()

    abstract fun handleCheck(view: View)
}