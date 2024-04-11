package edu.bu.metcs.projectportal.data

import kotlinx.coroutines.flow.Flow

interface ProjectRepository {

    fun getProjectsFlow(): Flow<List<Project>>

    fun searchProjectFlowbyId(projId: String): Flow<Project?>

    suspend fun searchProjectbyId(projId: String): Project?

    fun searchProjectsFlowByTitle(projTitle: String): Flow<List<Project>>

    suspend fun searchProjectbyTitle(projTitle: String): List<Project>

    suspend fun addProject(title: String, desc: String): String
    suspend fun editProject(id: String, title: String, desc: String)

    suspend fun deleteProject(id: String)

}