package edu.bu.projectportal.datalayer

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProjectPortalProvider: ContentProvider() {
    // Defines a handle to the Room database
    private lateinit var appDatabase: ProjectPortalDatabase
    private var projectDao: ProjectDao? = null

    override fun onCreate(): Boolean {

        // Creates a new database object.
        appDatabase = context?.let {
            Room.databaseBuilder(
                it,
                ProjectPortalDatabase::class.java, DBNAME
            ).build()
        }!!

        // Gets a Data Access Object to perform the database operations
        projectDao = appDatabase.projectDao()

        return true
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }




    fun constructProject(contentValue:ContentValues): Project {
        return Project(0, contentValue.getAsString(PROJECT_TITLE),
        contentValue.getAsString(PROJECT_DESC))

    }

    // Implements the provider's insert method
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        // Insert code here to determine which DAO to use when inserting data, handle error conditions, etc.
        // match Uri using sURIMatcher defined in the contract
        // match Uri using sURIMatcher defined in the contract
        val uriType: Int = sURIMatcher.match(uri)
        var id: Long = 0

        when (uriType) {
            PROJECT -> {
                // call corresponding db insert method
                values?.let {
                    id = projectDao!!.addProject(constructProject(values))
                }
            }
            else -> throw IllegalArgumentException("unknown URI: $uri")
        }
        if (id >= 0) {
            context!!.contentResolver.notifyChange(uri, null)
        }
        return Uri.parse(
            TABLE_NAME.toString() + "/" + id
        )

    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }
    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    companion object{
        val DBNAME = "projectportal-db"
        var PROVIDER_NAME = "edu.bu.projectportal.ProjectProvider"
        val URL = "content://$PROVIDER_NAME"
        val TABLE_NAME = "projects"
        val PROJECT_ID = "id"
        val PROJECT_TITLE = "title"
        val PROJECT_DESC = "desc"

        val projectUri = Uri.parse(URL + "/" + TABLE_NAME)

        const val PROJECT = 1
        const val PROJECTID = 2
        val sURIMatcher = UriMatcher(UriMatcher.NO_MATCH).apply{
            this.addURI(PROVIDER_NAME, TABLE_NAME, PROJECT)
            this.addURI(PROVIDER_NAME, TABLE_NAME + "/#", PROJECTID)

        }
    }
}

