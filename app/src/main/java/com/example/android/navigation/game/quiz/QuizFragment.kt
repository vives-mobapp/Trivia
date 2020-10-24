package com.example.android.navigation.game.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        // Act on game state change using GameState and abstract functions for won and lost.
        viewModel.gameState.observe(this, Observer { gameState ->
            when (gameState) {
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
        viewModel.timer?.resumeTimer()
    }

    override fun onStop() {
        super.onStop()
        viewModel.timer?.stopTimer()
    }

    abstract fun lost()

    abstract fun won()

    abstract fun handleCheck(view: View)
}