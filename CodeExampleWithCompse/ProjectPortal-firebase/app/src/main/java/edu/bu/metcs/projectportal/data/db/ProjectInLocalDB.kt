package edu.bu.metcs.projectportal.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="projects")
data class ProjectInLocalDB(
    @PrimaryKey
    val id: String,
    var title: String = "",
    @ColumnInfo(name="desc")
    var description: String = "")