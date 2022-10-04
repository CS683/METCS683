package edu.bu.projectportal

import android.graphics.Insets.add
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.fragment.app.replace


class MainActivity : AppCompatActivity(R.layout.main_activity){//, EditProjectListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

//    override fun editProj(){
//        supportFragmentManager.commit{
//            replace<EditFragment>(R.id.container)
//            addToBackStack("detail")
//        }
//
//    }
//
//    override fun editProjDone(){
////        findViewById<FragmentContainerView>(R.id.container)?.let { frameLayout ->
////            supportFragmentManager.beginTransaction()
////                .replace(frameLayout.id, DetailFragment.newInstance())
////                .commitNow()
////        }
////        supportFragmentManager.commit{
////            replace<DetailFragment>(R.id.container)
////        }
//        supportFragmentManager.popBackStack()
//    }
}