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

package com.example.android.navigation.game.endgame

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.game.endgame.GameOverFragmentArgs
import com.example.android.navigation.game.endgame.GameWonFragmentArgs
import com.example.android.navigation.game.endgame.GameWonFragmentDirections
import com.example.android.navigation.R
import com.example.android.navigation.databinding.FragmentGameWonBinding
import com.example.android.navigation.game.quiz.QuizFragment


class GameWonFragment : GameEndFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)
        binding.nextMatchButton.setOnClickListener { view: View ->
            val args = GameOverFragmentArgs.fromBundle(arguments!!)
            when (args.quizFrom) {
                QuizFragment.Name.RADIO -> view.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
                QuizFragment.Name.EDITTEXT -> view.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToEditTextQuizFragment2())
            }
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)
    }
}
