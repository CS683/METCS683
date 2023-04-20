package edu.bu.projectportal.firebaseutil

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference

import com.google.firebase.firestore.QueryDocumentSnapshot

import com.google.firebase.firestore.QuerySnapshot

import com.google.android.gms.tasks.OnCompleteListener
import edu.bu.projectportal.datalayer.Project


class FirebaseStorage {

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val mAuth = FirebaseAuth.getInstance()
    private val TAG = javaClass.simpleName

    val projectsLiveData: MutableLiveData<List<Project>> = MutableLiveData()

    var count: MutableLiveData<Int> = MutableLiveData<Int>(0)

    fun getProjectRef(): CollectionReference{
        return firebaseFirestore.collection ("users/" + mAuth.uid + "/projects")
    }

    fun addProject(project: Project){
        getProjectRef().add (project).
            addOnSuccessListener ( OnSuccessListener<DocumentReference> () {
                    Log.d (TAG, "DocumentSnapshot added with ID: " + it.id);
            }).addOnFailureListener (OnFailureListener () {
                    Log.w (TAG, "Error adding document", it);

            })
    }


    fun delProject(project: Project){
        getProjectRef().document(project.docId)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }


    fun loadAllProjects(){
        getProjectRef()
            .addSnapshotListener {documents,e->
                var projects = ArrayList<Project>()
                documents?.forEach{document ->
                        document.toObject<Project>(Project::class.java).let{
                                project->
                            project.docId = document.id
                            projects.add(project)
                        }
                }
                projectsLiveData.setValue(projects)
                e?.let{
                    Log.d("Firebase storage","listen failed", e)
                }
            }

    }



    //to-do
    fun searchProject(projId: Long): MutableLiveData<Project>{

        var project:MutableLiveData<Project> = MutableLiveData()
        return project
    }

    //to-do
    fun searchProjectsbyTitle(projTitle:String): MutableLiveData<List<Project>>{

        var projects:MutableLiveData<List<Project>> = MutableLiveData()

        return projects
    }

    //to-do
    fun editProject(project: Project){

    }
}