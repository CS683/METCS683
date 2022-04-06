package edu.bu.projectportal.datalayer


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
This is the data entity class.
User @Entity annotation
 */
@Entity(tableName="projects")
data class Project(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    @ColumnInfo(name="desc")
    var description: String)
//{
//    companion object {
//         // val project = Project(0, "Weather Forecast", "Weather Forcast is an app ...")
//        val projects = mutableListOf(
//            Project(0, "Weather Forecast", "Weather Forcast is an app ..."),
//            Project(1, "Connect Me", "Connect Me is an app ... "),
//            Project(2, "What to Eat", "What to Eat is an app ..."),
//            Project(3, "Project Portal", "Project Portal is an app ..."),
//            Project(4, "Smart Sense", "Project Portal is an app ..."))
//
//
//    }
//}


