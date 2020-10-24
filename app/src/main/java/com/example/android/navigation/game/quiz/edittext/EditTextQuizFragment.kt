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

package com.example.android.navigation.game.quiz.edittext

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.navigation.R
import com.example.android.navigation.databinding.FragmentEdittextquizBinding
import com.example.android.navigation.game.quiz.QuizFragment
import com.example.android.navigation.game.quiz.radio.RadioButtonQuizViewModel


class EditTextQuizFragment : QuizFragment(Name.EDITTEXT) {

    private lateinit var binding : FragmentEdittextquizBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_edittextquiz, container, false)

        viewModel = ViewModelProvider(this).get(RadioButtonQuizViewModel::class.java)

        binding.game = this.viewModel as EditTextQuizViewModel

        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            handleCheck(view)
        }

        setup()

        return binding.root
    }


    private fun hideKeyboard() {
        // Hide keyboard.
        val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    override fun handleCheck(view: View) {
        hideKeyboard()

        if (!binding.quizEditText.text.isBlank()) {
            viewModel.handleAnswer(binding.quizEditText.text.toString())
        } else {
            Toast.makeText(this.context, "No blank answers allowed.", Toast.LENGTH_SHORT).show()
        }
        binding.quizEditText.text.clear()
    }

    override fun lost() {
        findNavController().navigate(EditTextQuizFragmentDirections.actionEditTextQuizFragment2ToGameOverFragment2(name))
    }

    override fun won() {
        findNavController().navigate(EditTextQuizFragmentDirections.actionEditTextQuizFragment2ToGameWonFragment(name))
    }
}
