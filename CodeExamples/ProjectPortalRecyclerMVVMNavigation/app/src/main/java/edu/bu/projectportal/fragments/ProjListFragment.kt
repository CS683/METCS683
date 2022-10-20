package edu.bu.projectportal.fragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import edu.bu.projectportal.Project
import edu.bu.projectportal.databinding.FragmentProjListBinding


class ProjListFragment : Fragment() {
    // use ViewBinding
    private var _binding: FragmentProjListBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val projTitleList = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProjListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        projTitleList.clear()
        Project.projects.forEach { projTitleList.add(it.title)}

        val projListAdapter =
            ArrayAdapter<String>(requireContext(), R.layout.simple_list_item_1, projTitleList)
        binding.projTitleList.adapter = projListAdapter
    }


}