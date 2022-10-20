package edu.bu.projectportal

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController

class DetailFragment : Fragment(R.layout.fragment_detail) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        val projTitle = view.findViewById<TextView>(R.id.projTitle)
        val projDesc =  view.findViewById<TextView>(R.id.projDesc)
        val editProj = view.findViewById<ImageButton>(R.id.editProj)

        val position:Int = arguments?.getInt("projId")?:0
        Log.d("TAG","position:"+position)
        projTitle.text =  Project.projects[position].title
        projDesc.text = Project.projects[position].description


        editProj.setOnClickListener{
            val action = DetailFragmentDirections
                .actionDetailFragmentToEditFragment(position)
            view.findNavController().navigate(action)
//            view.findNavController().
//            navigate(R.id.action_detailFragment_to_editFragment)
        }
    }
}