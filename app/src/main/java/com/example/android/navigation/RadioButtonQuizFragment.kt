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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentEdittextquizBinding
import com.example.android.navigation.databinding.FragmentRadiobuttonquizBinding

class RadioButtonQuizFragment : QuizFragment() {

    private lateinit var binding : FragmentRadiobuttonquizBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        MainActivity.Scores.radio_max = numQuestions
        randomizeQuestions()

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentRadiobuttonquizBinding>(
                inflater, R.layout.fragment_radiobuttonquiz, container, false)

        // Shuffles the questions and sets the question index to the first question.
        randomizeQuestions()

        // Bind this fragment class to the layout
        binding.game = this

        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            handleCheck(view)
        }
        return binding.root
    }


    protected fun handleCheck(view: View) {
        MainActivity.Scores.radio_score = questionIndex

        val checkedId = binding.questionRadioGroup.checkedRadioButtonId
        // Do nothing if nothing is checked (id == -1)
        if (-1 != checkedId) {
            var answerIndex = 0
            when (checkedId) {
                R.id.secondAnswerRadioButton -> answerIndex = 1
                R.id.thirdAnswerRadioButton -> answerIndex = 2
                R.id.fourthAnswerRadioButton -> answerIndex = 3
            }
            // The first answer in the original question is always the correct one, so if our
            // answer matches, we have the correct answer.
            if (answers[answerIndex] == currentQuestion.answers[0]) {
                questionIndex++
                // Advance to the next question
                if (questionIndex < numQuestions) {
                    currentQuestion = questions[questionIndex]
                    setQuestion()
                    binding.invalidateAll()
                } else {
                    // We've won!  Navigate to the gameWonFragment.
                    view.findNavController().navigate(RadioButtonQuizFragmentDirections.actionGameFragmentToGameWonFragment(numQuestions, questionIndex))
                }
            } else {
                // Game over! A wrong answer sends us to the gameOverFragment.
                view.findNavController().navigate(R.id.action_gameFragment_to_gameOverFragment2)
            }
        }
    }
}
