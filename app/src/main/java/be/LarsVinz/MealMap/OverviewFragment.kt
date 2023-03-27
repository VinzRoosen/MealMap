package be.LarsVinz.MealMap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.LarsVinz.MealMap.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment(R.layout.fragment_overview) {

    private lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOverviewBinding.inflate(layoutInflater)

        return binding.root
    }
}