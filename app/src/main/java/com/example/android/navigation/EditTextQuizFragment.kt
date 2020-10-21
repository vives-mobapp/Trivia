/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.android.navigation.databinding.FragmentEdittextquizBinding

class EditTextQuizFragment : QuizFragment() {

    private lateinit var binding : FragmentEdittextquizBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        MainActivity.Scores.edittext_max = numQuestions
        randomizeQuestions()

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentEdittextquizBinding>(
                inflater, R.layout.fragment_edittextquiz, container, false)

        // Bind this fragment class to the layout
        binding.game = this
        val nav = findNavController()
        restartTimer()

        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            handleCheck(view)
        }
        return binding.root
    }


    protected fun handleCheck(view: View) {
        // Hide keyboard.
        val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

        MainActivity.Scores.edittext_score = questionIndex

        if (!binding.quizEditText.text.isBlank()) {
            restartTimer()
            if (binding.quizEditText.text.trim().toString().equals(currentQuestion.answers[0])) {
                questionIndex++
                MainActivity.Scores.edittext_score = questionIndex
                // Advance to the next question
                if (questionIndex < numQuestions) {
                    currentQuestion = questions[questionIndex]
                    setQuestion()
                    binding.invalidateAll()
                } else {
                    // We've won!  Navigate to the gameWonFragment.
                    view.findNavController().navigate(EditTextQuizFragmentDirections.actionEditTextQuizFragment2ToGameWonFragment(numQuestions, questionIndex, QuizFragment.Names.edittext))
                }
            } else {
                lost()
            }
        } else {
            Toast.makeText(this.context, "No blank answers allowed.", Toast.LENGTH_SHORT).show()
        }
    }

    fun lost() {
        findNavController().navigate(EditTextQuizFragmentDirections.actionEditTextQuizFragment2ToGameOverFragment2(QuizFragment.Names.edittext))
    }

    fun restartTimer() {
        timer = Timer(15, lifecycle, binding.timerTextView,  Runnable {
            lost()
        })
    }
}
