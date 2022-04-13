package edu.bu.projectportal.datalayer

import androidx.lifecycle.LiveData
import edu.bu.projectportal.api.GithubAPI
import edu.bu.projectportal.datalayer.Project
import edu.bu.projectportal.datalayer.ProjectDao
import edu.bu.projectportal.firebaseutil.FirebaseStorage
import java.util.concurrent.Executors

class ProjectPortalRepository (
    private val projectDao: ProjectDao
) {
    val executor =  Executors.newSingleThreadExecutor()

//    fun addProject(project: Project){
//        executor.execute {
//            projectDao.addProject(project)
//        }
//    }

//
//    fun delProject(project: Project) {
//        executor.execute {
//            projectDao.delProject(project)
//        }
//    }
//
//    fun editProject(project: Project) {
//        executor.execute {
//            projectDao.editProject(project)
//        }
//    }

    suspend fun addProject(project:Project){
        projectDao.addProject(project)
    }

    suspend fun delProject(project:Project){
        projectDao.delProject(project)
    }

    suspend fun editProject(project:Project){
        projectDao.editProject(project)
    }

    fun getAllProjects(): LiveData<List<Project>> {
        return projectDao.getAllProjects()
    }

    fun searchProject(projId: Long): LiveData<Project> {
        return projectDao.searchProjectById(projId)
    }

    fun searchProjectsbyTitle(projTitle:String): LiveData<List<Project>> {
        return projectDao.searchProjectsByTitle(projTitle)
    }

    fun count(): LiveData<Int> {
        return projectDao.count()
    }
}

//class ProjectPortalRepository (
//    private val firebaseStorage: FirebaseStorage,
//    private val githubAPI:GithubAPI
//)  {
//     fun addProject(project: Project){
//        firebaseStorage.addProject(project)
//    }
//
//    fun delProject(project: Project) {
//        firebaseStorage.delProject(project)
//    }
//
//    fun editProject(project: Project) {
//        firebaseStorage.editProject(project)
//    }
//
//    fun getAllProjects(): LiveData<List<Project>> {
//        firebaseStorage.loadAllProjects()
//        return firebaseStorage.projectsLiveData
//    }
//
//    fun searchProject(projId: Long): LiveData<Project> {
//        return firebaseStorage.searchProject(projId)
//    }
//
//   fun searchProjectsbyTitle(projTitle:String): LiveData<List<Project>> {
//        return firebaseStorage.searchProjectsbyTitle(projTitle)
//    }
//   fun count(): LiveData<Int>{
//        return firebaseStorage.count
//    }
//
//    fun loadGithubProjects():LiveData<List<Project>> {
//        githubAPI.loadProjects()
//        return githubAPI.projectsLiveData
//    }
//}