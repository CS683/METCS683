package edu.bu.projectportal.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import edu.bu.projectportal.Project
import edu.bu.projectportal.databinding.FragmentProjItemBinding


class MyProjListRecyclerViewAdapter(
    private val projects: List<Project>,
    private val onProjectClick:(Project)-> Unit
)
    : RecyclerView.Adapter<MyProjListRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    return ViewHolder(FragmentProjItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = projects[position]
        holder.idView.text = (project.id +1).toString()
        holder.contentView.text = project.title
        holder.cardView.setOnClickListener{
            onProjectClick(project)
      }
    }


    override fun getItemCount(): Int = projects.size

    inner class ViewHolder(binding: FragmentProjItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.projIdView
        val contentView: TextView = binding.projTitleinCard
        val cardView: CardView = binding.projectCard

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}