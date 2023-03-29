package edu.bu.projectportal.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import edu.bu.projectportal.adapter.MyProjListRecyclerViewAdapter
import edu.bu.projectportal.Project
import edu.bu.projectportal.R
import edu.bu.projectportal.databinding.FragmentProjListRecyclerViewBinding
import edu.bu.projectportal.viewmodel.CurProjectViewModel
import edu.bu.projectportal.viewmodel.ProjectListViewModel

/**
 * A fragment representing a list of Items.
 */
class ProjListRecyclerViewFragment : Fragment() {
    private var _binding: FragmentProjListRecyclerViewBinding? = null
    private val binding get() = _binding!!

    private var columnCount = 1
    private var largeScreen = false

    private lateinit var onProjectClickListener: OnProjectClickListener

    interface OnProjectClickListener{
        fun onProjectClick(proj:Project)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnProjectClickListener) {
            onProjectClickListener = context
        } else {
            throw RuntimeException("Must implement OnProjectClickListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
        // This is not needed when using slidepane
        arguments?.let {
            largeScreen = it.getBoolean(ARG_LARGE_SCREEN)
        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentProjListRecyclerViewBinding.inflate(inflater,
            container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         // this view model is shared among all fragments,
        // so the owner needs to be activity.
        // use requireActivity() to get the activity containing this fragment
        val viewModel =
            ViewModelProvider(requireActivity()).get(CurProjectViewModel::class.java)
        // this view model is only for this fragment, so owner is just "this"
        val listViewModel =
            ViewModelProvider(this).get(ProjectListViewModel::class.java)
//        val viewModel:CurProjectViewModel by activityViewModels()
//        val listViewModel: ProjectListViewModel by viewModels()


        binding.projlist.apply {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }

             val myAdapter = MyProjListRecyclerViewAdapter(
                listViewModel.projectList?.value ?: emptyList())
                 { project ->
                     viewModel.setCurProject(project)
                     // this is only used when using sliding pane
         //            onProjectClickListener?.onProjectClick(project)

                     // this is only used when not using sliding pane
                      if (!largeScreen) {
                            view.findNavController().navigate(
                                R.id.action_projListRecycleViewFragment_to_nav_graph
                            )
                       }
                 }

            this.adapter = myAdapter

            listViewModel.projectList.observe(viewLifecycleOwner, Observer {
                myAdapter.notifyDataSetChanged()
            })

            viewModel.curProject.observe(viewLifecycleOwner, Observer {
                myAdapter.notifyDataSetChanged()
            })

        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
        const val ARG_LARGE_SCREEN = "large-screen"


        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                ProjListRecyclerViewFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}