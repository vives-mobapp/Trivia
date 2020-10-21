package com.example.android.navigation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                   savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)
        setHasOptionsMenu(true)

        binding.q1ScoreTv.text = formatScore("Radio Button", MainActivity.Scores.radio_score, MainActivity.Scores.radio_max)
        binding.q2ScoreTv.text = formatScore("Edit Text", MainActivity.Scores.edittext_score, MainActivity.Scores.edittext_max)

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