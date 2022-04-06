package edu.bu.projectportal.fragments

import android.content.ContentUris
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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

class EditFragment : Fragment() {
    // use ViewBinding
    private var _binding: FragmentEditBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    // No need these when using ViewBinding
//    private lateinit var projTitle: EditText
//    private lateinit var projDesc: EditText
//    private lateinit var submit:Button
//    private lateinit var cancel:Button

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

        // No need of findViewById anymore with ViewBinding
//        projTitle = view.findViewById(R.id.projTitleEdit)
//        projDesc =  view.findViewById(R.id.projDescEdit)
//
//        submit = view.findViewById<Button>(R.id.submit)
//        cancel = view.findViewById<Button>(R.id.cancel)
        val position:Int = arguments?.getInt("position")?:0
        Log.d("TAG","position:"+position)


        val viewModel =
            ViewModelProvider(requireActivity()).get(CurProjectViewModel::class.java)

        viewModel.curProject.observe(viewLifecycleOwner, Observer {
            binding.projTitleEdit.setText(it.title)
            binding.projDescEdit.setText(it.description)
        })

        binding.submit.setOnClickListener {
            viewModel.updateCurProject( binding.projTitleEdit.text.toString(),
                binding.projDescEdit.text.toString() )
            view.findNavController().navigate(R.id.action_editFragment_pop)
        }

        binding.cancel.setOnClickListener {
            view.findNavController().
            navigate(R.id.action_editFragment_pop)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}