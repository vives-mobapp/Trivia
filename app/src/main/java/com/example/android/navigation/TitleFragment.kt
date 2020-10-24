package com.example.android.navigation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.FragmentTitleBinding
import com.example.android.navigation.game.quiz.QuizFragment
import com.example.android.navigation.game.quiz.QuizViewModel
import com.example.android.navigation.game.quiz.edittext.EditTextQuizViewModel
import com.example.android.navigation.game.quiz.radio.RadioButtonQuizViewModel

class TitleFragment : Fragment() {

    lateinit var viewModels : MutableMap<QuizFragment.Name, QuizViewModel>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                   savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)
        setHasOptionsMenu(true)

        viewModels = mutableMapOf(
                QuizFragment.Name.RADIO to ViewModelProvider(this).get(RadioButtonQuizViewModel::class.java),
                QuizFragment.Name.EDITTEXT to ViewModelProvider(this).get(EditTextQuizViewModel::class.java)
        )

        viewModels[QuizFragment.Name.RADIO]?.score?.observe(viewLifecycleOwner, Observer { newScore ->
            binding.q1ScoreTv.text = formatScore("Radio Button", newScore, viewModels[QuizFragment.Name.RADIO]!!.numQuestions)
        })

        viewModels[QuizFragment.Name.EDITTEXT]?.score?.observe(viewLifecycleOwner, Observer { newScore ->
            binding.q2ScoreTv.text = formatScore("Edit Text", newScore, viewModels[QuizFragment.Name.EDITTEXT]!!.numQuestions)
        })

        return binding.root
    }

    private fun formatScore(quiz: String, score: Int, max: Int) : String {
        return if (score > -1) "You have scored ${score}/${max} on quiz $quiz" else "You have not yet played the $quiz quiz"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, view!!.findNavController()) || super.onOptionsItemSelected(item)
    }
}