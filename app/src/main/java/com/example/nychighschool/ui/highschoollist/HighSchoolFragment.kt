package com.example.nychighschool.ui.highschoollist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.nychighschool.databinding.FragmentHighSchoolBinding


class HighSchoolFragment : Fragment() {

    private val viewModel by lazy {
        HighSchoolViewModel()
    }

    private val highSchoolAdapter by lazy {
        HighSchoolAdapter({
            val direction = HighSchoolFragmentDirections.actionHighSchoolToSat(it)
            findNavController().navigate(direction)
        })
    }

    private var binding : FragmentHighSchoolBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHighSchoolBinding.inflate(inflater, container, false)
        return binding?.root
    }

    //TODO: if given more time, add refresh button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.highSchoolRecyclerView?.adapter = highSchoolAdapter

        viewModel.schoolListResult.observe(viewLifecycleOwner) {
            // if successful, submit the list to adapter else show refresh button
            if (it.isSuccessful) {
                binding?.highSchoolRefresh?.visibility = View.INVISIBLE
                highSchoolAdapter.submitList(it.body())
            }
            else {
                Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show()
                binding?.highSchoolRefresh?.visibility = View.VISIBLE
            }
        }
        viewModel.getHighSchoolList()

        binding?.highSchoolRefresh?.setOnClickListener {
            viewModel.getHighSchoolList()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}