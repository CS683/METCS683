package edu.bu.projectportal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import edu.bu.projectportal.datalayer.Project
import edu.bu.projectportal.ProjectPortalApplication
import java.util.concurrent.Executors
/*
Create a ViewModel class to keep track of Current Project
Use AndroidViewModel as the super class, we can pass the
application object directly to it.
 */
class CurProjectViewModel(application: Application): AndroidViewModel(application){
    private val _curProject: MutableLiveData<Project> = MutableLiveData()
    val curProject: LiveData<Project>
        get() = _curProject

    val projectPortalRepository =
        (application as ProjectPortalApplication).projectPortalRepository

    fun initCurProject(project: Project){
        if (_curProject.value == null)
            _curProject.value = project
    }

    fun setCurProject(project: Project){
        _curProject.value = project
    }

    fun isCurProject(project:Project):Boolean{
        return _curProject.value?.id == project.id
    }
    fun updateCurProject(title:String, desp:String){
        _curProject.value = _curProject.value?.apply{
            this.title = title
            this.description = desp
        }

        projectPortalRepository.editProject(_curProject.value!!)

    }



}