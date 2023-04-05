package edu.bu.projectportal.fragments

import edu.bu.projectportal.datalayer.Project
import edu.bu.projectportal.viewmodel.ProjectListViewModel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import edu.bu.projectportal.R
import edu.bu.projectportal.databinding.FragmentEditBinding
import edu.bu.projectportal.viewmodel.CurProjectViewModel

class AddFragment : Fragment(),View.OnClickListener {
    // use ViewBinding
    private var _binding: FragmentEditBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var listViewModel: ProjectListViewModel
    private lateinit var viewModel: CurProjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Use ViewBinding
        // FragmentDetailBinding is a generated binding class mapped to fragment_detail.xml
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        listViewModel =
            ViewModelProvider(requireActivity()).get(ProjectListViewModel::class.java)
        viewModel =
            ViewModelProvider(requireActivity()).get(CurProjectViewModel::class.java)

        binding.submit.setOnClickListener (this)
        binding.cancel.setOnClickListener (this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.submit) {
            val project = Project(
                0, binding.projTitleEdit.text.toString(),
                binding.projDescEdit.text.toString())
            listViewModel.addProject(project)
            viewModel.setCurProject(project)
        }
        view.findNavController().navigate(R.id.action_addFragment_pop)
        activity?.onBackPressed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}