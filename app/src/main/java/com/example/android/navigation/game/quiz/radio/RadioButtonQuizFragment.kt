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

package com.example.android.navigation.game.quiz.radio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.navigation.R
import com.example.android.navigation.databinding.FragmentRadiobuttonquizBinding
import com.example.android.navigation.game.quiz.QuizFragment

class RadioButtonQuizFragment : QuizFragment(Name.RADIO) {

    private lateinit var binding: FragmentRadiobuttonquizBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentRadiobuttonquizBinding>(
                inflater, R.layout.fragment_radiobuttonquiz, container, false)

        viewModel = ViewModelProvider(activity!!).get(RadioButtonQuizViewModel::class.java)
        binding.game = this.viewModel as RadioButtonQuizViewModel

        setup()

        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            handleCheck(view)
        }

        // TODO:
        viewModel.timer.secondsCount.observe(viewLifecycleOwner, Observer { secondsCount ->
            binding.timerTextView.text = secondsCount.toString()
        })

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun handleCheck(view: View) {
        val checkedId = binding.questionRadioGroup.checkedRadioButtonId

        // Do nothing if nothing is checked (id == -1)
        if (-1 != checkedId) {

            // Get indices.
            var answerIndex = 0
            when (checkedId) {
                R.id.secondAnswerRadioButton -> answerIndex = 1
                R.id.thirdAnswerRadioButton -> answerIndex = 2
                R.id.fourthAnswerRadioButton -> answerIndex = 3
            }

            // Let viewmodel decide if its the correct answer.
            viewModel.handleAnswer(viewModel.answers.value!![answerIndex])
        }
    }

    override fun lost() {
        findNavController().navigate(RadioButtonQuizFragmentDirections.actionGameFragmentToGameOverFragment2(name))
    }

    override fun won() {
        findNavController().navigate(RadioButtonQuizFragmentDirections.actionGameFragmentToGameWonFragment(name))
    }
}
