package edu.bu.metcs.projectportal.data

import edu.bu.metcs.projectportal.data.db.ProjectDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalProjectRepository @Inject constructor(
    private val localDb: ProjectDao
 ):ProjectRepository {
    override fun getProjectsFlow(): Flow<List<Project>> {
        return localDb.getAllProjectsFlow().map { projects ->
            projects.toModel()
        }

    }

    override fun searchProjectFlowbyId(projId: String): Flow<Project?> {
        return localDb.searchProjectFlowById(projId).map { project ->
            project.toModel()
        }
   }

    override suspend fun searchProjectbyId(projId: String): Project? {
        return localDb.searchProjectById(projId)!!.toModel()
    }

    override fun searchProjectsFlowByTitle(projTitle: String): Flow<List<Project>> {
        return localDb.searchProjectsFlowByTitle(projTitle).map { projects ->
                projects.toModel() }
    }

    override suspend fun searchProjectbyTitle(projTitle: String): List<Project> {
        return localDb.searchProjectsByTitle(projTitle).toModel()
    }


    override suspend fun addProject(title: String, desc: String): String {
        val projId = UUID.randomUUID().toString()
        val project = Project (projId, title, desc)
        localDb.upsertProject(project.toLocal())
        return projId
    }

    override suspend fun editProject(id: String, title: String, desc: String) {
        val project = Project (id, title, desc)
        localDb.upsertProject(project.toLocal())
    }

    override suspend fun deleteProject(id: String) {
        localDb.deleteProject(id)
    }
}