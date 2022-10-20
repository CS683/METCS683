package edu.bu.projectportal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import edu.bu.projectportal.databinding.FragmentEditBinding

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
        val position:Int = arguments?.getInt("projId")?:0
        Log.d("TAG","position:"+position)

        binding.projTitleEdit.setText(Project.projects[position].title)
        binding.projDescEdit.setText(Project.projects[position].description)


        binding.submit.setOnClickListener {
            Project.projects[position].title = binding.projTitleEdit.text.toString()
            Project.projects[position].description = binding.projDescEdit.text.toString()
            view.findNavController().
            navigate(R.id.action_editFragment_pop)
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