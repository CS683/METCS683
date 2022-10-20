package edu.bu.projectportal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.bu.projectportal.Project

class ProjectListViewModel: ViewModel(){

    private val _projectList: MutableLiveData<MutableList<Project>> = MutableLiveData()
    val projectList:LiveData<MutableList<Project>>
    get() = _projectList

    init{
        _projectList.value = Project.projects
    }

    
    fun addProject(project: Project) {
        _projectList.value = _projectList.value?.apply{
            this.add(project)
        }


    }

}