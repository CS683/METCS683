package edu.bu.projectportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import edu.bu.projectportal.adapter.MyProjListRecyclerViewAdapter
import edu.bu.projectportal.fragments.ProjListRecyclerViewFragment

class MainActivity : AppCompatActivity(), ProjListRecyclerViewFragment.OnProjectClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.main_activity)
        setContentView(R.layout.main_activity_slidepane)
    }

    // this is only used for sliding pane
    override fun onBackPressed() {
        findViewById<SlidingPaneLayout>(R.id.slidepane).close()
    }

    // this is only used for sliding pane
    override fun onProjectClick(project: Project){
        findViewById<SlidingPaneLayout>(R.id.slidepane).open()
    }

}