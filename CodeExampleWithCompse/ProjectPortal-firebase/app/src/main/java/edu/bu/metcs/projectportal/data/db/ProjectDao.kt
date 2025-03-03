package edu.bu.metcs.projectportal.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/*
This ProjectDao interface defines various CRUD operations
to access the projects table in the database
 */
@Dao
interface ProjectDao {

    @Delete
    suspend fun delProject(project: ProjectInLocalDB)


    @Query("SELECT count(*) From projects")
    fun count(): Flow<Int>

    @Query("SELECT * FROM projects")
    fun getAllProjectsFlow(): Flow<List<ProjectInLocalDB>>

    @Query("SELECT * FROM projects")
    suspend fun getAllProjects(): List<ProjectInLocalDB>


    @Query("SELECT * FROM projects where id = :projId")
    fun searchProjectFlowById(projId: String): Flow<ProjectInLocalDB?>

    @Query("SELECT * FROM projects where id = :projId")
    suspend fun searchProjectById(projId: String): ProjectInLocalDB?

    @Query("SELECT * FROM projects WHERE title like :projTitle ")
    fun searchProjectsByTitle(projTitle:String): Flow<List<ProjectInLocalDB>>

    @Upsert
    suspend fun upsertProject(project: ProjectInLocalDB)

    // return # of projects deleted, should be 1.
    @Query ("DELETE FROM projects WHERE id = :projId")
    suspend fun deleteProject(projId: String):Int
}