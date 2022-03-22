package edu.bu.projectportal

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView

class DetailFragment : Fragment() {
    private lateinit var projTitle:TextView
    private lateinit var projDesc:TextView
    private lateinit var editProj: ImageButton

    private lateinit var editProjListener: EditProjectListener

    companion object {
        fun newInstance() = DetailFragment()
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
    ): View {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        projTitle = view.findViewById(R.id.projTitle)
        projDesc =  view.findViewById(R.id.projDesc)
        editProj = view.findViewById(R.id.editProj)

        projTitle.text =  Project.project.title
        projDesc.text = Project.project.description

        editProj.setOnClickListener{
           editProjListener.editProj()

        }
    }

}