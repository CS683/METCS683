package edu.bu.projectportal.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import edu.bu.projectportal.R
import edu.bu.projectportal.databinding.FragmentEditBinding
import edu.bu.projectportal.viewmodel.CurProjectViewModel
import com.bumptech.glide.Glide
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class EditFragment : Fragment() {
    // use ViewBinding
    private var _binding: FragmentEditBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private var receivedUri:Uri? = null

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
        super.onViewCreated(view, savedInstanceState)

        // No need of findViewById anymore with ViewBinding
//        projTitle = view.findViewById(R.id.projTitleEdit)
//        projDesc =  view.findViewById(R.id.projDescEdit)
//
//        submit = view.findViewById<Button>(R.id.submit)
//        cancel = view.findViewById<Button>(R.id.cancel)
        val position: Int = arguments?.getInt("position") ?: 0
        Log.d("TAG", "position:" + position)


        val viewModel =
            ViewModelProvider(requireActivity()).get(CurProjectViewModel::class.java)

        viewModel.curProject.observe(viewLifecycleOwner, Observer {
            binding.projTitleEdit.setText(it.title)
            binding.projDescEdit.setText(it.description)
        })

        binding.submit.setOnClickListener {
            viewModel.updateCurProject(
                binding.projTitleEdit.text.toString(),
                binding.projDescEdit.text.toString()
            )
            view.findNavController().navigate(R.id.action_editFragment_pop)
        }

        binding.cancel.setOnClickListener {
            view.findNavController().navigate(R.id.action_editFragment_pop)
        }

        // Use ActivityResultContract to call an activity for result type-safe.
        // It specifies that an activity can be called with
        // an input of type I, and produce an output of type o.

        binding.imageView.setOnClickListener {
            openDialog()
        }
    }
    // Use activity result contract to specify an activity to
    // be called for result type-safe
    // register a call back for an activity result
    private val getImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            // Handle the returned Uri
            uri.let {
                Glide.with(this).load(uri).into(binding.imageView)
            }
        }


    private val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            receivedUri.let { uri ->
                Glide.with(this).load(uri).into(binding.imageView)
            }
        }
    }

    // open a alert dialog to let the user choose
    fun openDialog() {
        val optionMenu = arrayOf("Take Photo", "choose from galary", "exit")
        AlertDialog.Builder(activity).setItems(optionMenu) {
                dialogInterface: DialogInterface, i: Int ->
            when (i) {
                0 -> {
                    receivedUri = getTmpFileUri()
                    takePhoto.launch(receivedUri)
                }
                1 -> getImage.launch("image/*")
            }
        }.show()
    }

    // this method is to create a Uri for the tmp file used to
    // store the picture taken by the camera
    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile(
            SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH).
            format(System.currentTimeMillis()), ".jpg",
            requireActivity().applicationContext.
            getExternalFilesDir("Pictures")).apply {
            createNewFile()
            deleteOnExit()
        }
        Log.d("Filename", tmpFile.absolutePath)
        // make sure define the file provider in the manifest file
        // and define the path
        return FileProvider.getUriForFile(requireActivity().applicationContext,
            "edu.bu.projectportal.fileprovider", tmpFile)
    }
}