package edu.bu.projectportal.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import edu.bu.projectportal.adapter.MyProjListRecyclerViewAdapter
import edu.bu.projectportal.datalayer.Project
import edu.bu.projectportal.R
import edu.bu.projectportal.databinding.FragmentProjListRecyclerViewBinding
import edu.bu.projectportal.viewmodel.CurProjectViewModel
import edu.bu.projectportal.viewmodel.ProjectListViewModel

/**
 * A fragment representing a list of Items.
 */
class GithubProjListFragment : Fragment() {
    private var _binding: FragmentProjListRecyclerViewBinding? = null
    private val binding get() = _binding!!

    private var columnCount = 1
    private var largeScreen = false

    private lateinit var myAdapter: MyProjListRecyclerViewAdapter
    private lateinit var viewModel: CurProjectViewModel
    private lateinit var listViewModel: ProjectListViewModel
    private lateinit var onProjectClickListener: MyProjListRecyclerViewAdapter.OnProjectClickListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MyProjListRecyclerViewAdapter.OnProjectClickListener) {
            onProjectClickListener = context
        } else {
            throw RuntimeException("Must implement EditProjectListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel =
            ViewModelProvider(requireActivity()).get(CurProjectViewModel::class.java)
        listViewModel =
            ViewModelProvider(this).get(ProjectListViewModel::class.java)


        binding.projlist.apply {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }

            myAdapter = MyProjListRecyclerViewAdapter(
                //      listViewModel.projectList.value ?: emptyList(),
                object : MyProjListRecyclerViewAdapter.OnProjectClickListener {
                    override fun onProjectClick(project: Project) {
                        viewModel.setCurProject(project)
                        // this is just for sliding pane
                        onProjectClickListener?.onProjectClick(project)

                    }
                })

            this.adapter = myAdapter

//            listViewModel.loadGithubProjects().observe(viewLifecycleOwner, Observer {
//                myAdapter.replaceItems(it)
//                viewModel.initCurProject(myAdapter.getProject(0))
//            })

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