package edu.bu.metcs.projectportal.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

/*
This is the Database Entry point
 */
// define database configuraiton, and the access point to the database
@Database(
    entities = [ProjectInLocalDB::class],
    version = 1
)
abstract class ProjectPortalDatabase: RoomDatabase() {
    abstract fun projectDao(): ProjectDao
}