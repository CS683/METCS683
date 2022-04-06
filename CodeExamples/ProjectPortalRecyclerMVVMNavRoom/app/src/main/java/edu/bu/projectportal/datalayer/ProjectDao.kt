package edu.bu.projectportal.datalayer

import androidx.lifecycle.LiveData
import androidx.room.*
/*
This ProjectDao interface defines various CRUD operations
to access the projects table in the database
 */
@Dao
interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProject(project: Project):Long

    @Delete
    fun delProject(project: Project)

    @Update
    fun editProject(project: Project)

    @Query("SELECT count(*) From projects")
    fun count(): LiveData<Int>

    @Query("SELECT * FROM projects")
    fun getAllProjects(): LiveData<List<Project>>

    @Query("SELECT * FROM projects where id = :projId")
    fun searchProjectById(projId: Long): LiveData<Project>

    @Query("SELECT * FROM projects WHERE title like :projTitle ")
    fun searchProjectsByTitle(projTitle:String): LiveData<List<Project>>
}