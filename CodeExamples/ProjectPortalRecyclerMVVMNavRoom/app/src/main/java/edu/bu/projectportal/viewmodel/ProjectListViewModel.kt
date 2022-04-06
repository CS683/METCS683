package edu.bu.projectportal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import edu.bu.projectportal.datalayer.Project
import edu.bu.projectportal.ProjectPortalApplication
import edu.bu.projectportal.datalayer.ProjectDao
import java.util.concurrent.Executors
/*
Create a ViewModel class to keep track of the Project list
Use AndroidViewModel as the super class, we can pass the
application object directly to it.
 */
class ProjectListViewModel(application: Application): AndroidViewModel(application) {
    // pass the projectportalApplication as a parameter
    // make sure to define the application name in the manifest file.

    val projectPortalRepository =
        (application as ProjectPortalApplication).projectPortalRepository


    private val _projectList: LiveData<List<Project>>
    = projectPortalRepository.getAllProjects()
    val projectList:LiveData<List<Project>>
        get() = _projectList

    fun getAllProjects(): LiveData<List<Project>> {
        return projectPortalRepository.getAllProjects()
    }

    fun addProject(project: Project) {
        projectPortalRepository.addProject(project)
    }

    fun delProject(project: Project) {
        projectPortalRepository.delProject(project)
    }

}