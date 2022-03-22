package edu.bu.projectportal

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

class EditFragment : Fragment() {

    private lateinit var projTitle: EditText
    private lateinit var projDesc: EditText
    private lateinit var submit:Button
    private lateinit var cancel:Button

    private lateinit var editProjListener: EditProjectListener

    companion object {
        fun newInstance() = EditFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EditProjectListener) {
            editProjListener = context
        } else {
            throw RuntimeException("Must implement EditProjectListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        projTitle = view.findViewById(R.id.projTitleEdit)
        projDesc =  view.findViewById(R.id.projDescEdit)

        submit = view.findViewById(R.id.submit)
        cancel = view.findViewById(R.id.cancel)

        projTitle.setText(Project.project.title)
        projDesc.setText(Project.project.description)


        submit.setOnClickListener {
            Project.project.title = projTitle.text.toString()
            Project.project.description = projDesc.text.toString()
             editProjListener.editProjDone()

        }

        cancel.setOnClickListener {
            editProjListener.editProjDone()
        }




    }



}