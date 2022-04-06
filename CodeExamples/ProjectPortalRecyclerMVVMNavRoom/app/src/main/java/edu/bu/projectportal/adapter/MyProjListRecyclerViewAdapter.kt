package edu.bu.projectportal.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import edu.bu.projectportal.datalayer.Project
import edu.bu.projectportal.databinding.FragmentProjItemBinding


class MyProjListRecyclerViewAdapter(
  //  private val projects: List<Project>,
    private val onProjectClickListener: OnProjectClickListener
)
    : RecyclerView.Adapter<MyProjListRecyclerViewAdapter.ViewHolder>() {

    private val projects = mutableListOf<Project>()
    //changes data source content
    fun replaceItems(myProjects: List<Project>) {
        projects.clear()
        projects.addAll(myProjects)
        notifyDataSetChanged()
    }


    interface OnProjectClickListener {
        fun onProjectClick(project: Project);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    return ViewHolder(FragmentProjItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = projects[position]
        holder.idView.text = project.id.toString()
        holder.contentView.text = project.title
        holder.cardView.setOnClickListener{
//                val action = ProjListRecycleViewFragmentDirections
//                    .actionProjListRecycleViewFragmentToDetailFragment(position)
//                it.findNavController().navigate(action)

            onProjectClickListener.onProjectClick(project)
//            it.findNavController()?.navigate(
//                R.id.action_projListRecycleViewFragment_to_nav_graph)
        }
    }

    override fun getItemCount(): Int = projects.size

    fun getProject(pos: Int): Project {
        if (projects.size > 0)
            return projects[pos]
        else
            return Project(0,"","")
    }


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