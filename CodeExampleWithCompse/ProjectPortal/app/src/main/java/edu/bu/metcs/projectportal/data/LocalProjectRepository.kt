package edu.bu.metcs.projectportal.data

import edu.bu.metcs.projectportal.data.db.ProjectDao
import edu.bu.metcs.projectportal.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalProjectRepository @Inject constructor(
    private val localdb: ProjectDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
):ProjectRepository {
    override fun getProjectsFlow(): Flow<List<Project>> {
        return localdb.getAllProjectsFlow().map { projects ->
            withContext(dispatcher) {
               projects.toModel()
            }
        }
    }

    override fun searchProjectFlowbyId(projId: String): Flow<Project?> {
        return localdb.searchProjectFlowById(projId).map { project ->
            withContext(dispatcher) {
                project.toModel()
            }
        }
    }

    override suspend fun searchProjectbyId(projId: String): Project? {
        return localdb.searchProjectById(projId)!!.toModel()
    }

    override fun searchProjectsFlowByTitle(projTitle: String): Flow<List<Project>> {
        return localdb.searchProjectsFlowByTitle(projTitle).map { projects ->
            withContext(dispatcher) {
                projects.toModel() }
        }
    }

    override suspend fun searchProjectbyTitle(projTitle: String): List<Project> {
        return withContext(dispatcher){
            val projs = localdb.searchProjectsByTitle(projTitle)
            projs.toModel()
        }
      //  return localdb.searchProjectsByTitle(projTitle).toModel()
    }


    override suspend fun addProject(title: String, desc: String): String {
        val projId = UUID.randomUUID().toString()
        val project = Project (projId, title, desc)
        localdb.upsertProject(project.toLocal())
        return projId
    }

    override suspend fun editProject(id: String, title: String, desc: String) {
        val project = Project (id, title, desc)
        localdb.upsertProject(project.toLocal())
    }

    override suspend fun deleteProject(id: String) {
        localdb.deleteProject(id)
    }
}