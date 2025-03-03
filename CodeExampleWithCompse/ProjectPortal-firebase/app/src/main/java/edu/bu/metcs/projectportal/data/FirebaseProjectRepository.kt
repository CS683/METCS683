package edu.bu.metcs.projectportal.data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import edu.bu.metcs.projectportal.services.auth.AuthService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseProjectRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AuthService
): ProjectRepository{

    fun getProjectRef(): CollectionReference {
        return firestore.collection ("users/" + auth.currentUserId + "/projects")
    }

    override fun getProjectsFlow(): Flow<List<Project>> {
        return getProjectRef().snapshots().map { snapshot ->
            snapshot.toObjects(ProjectDoc::class.java).toModel1()
        }
    }

    override fun searchProjectFlowbyId(projId: String): Flow<Project?> {
        return getProjectRef()
            .whereEqualTo("id", projId).snapshots().map { snapshot ->
                snapshot.toObjects(ProjectDoc::class.java).firstOrNull()?.toModel()
            }
    }
    override suspend fun searchProjectbyId(projId: String): Project? {
        return getProjectRef().document(projId)
            .get().await().toObject(ProjectDoc::class.java)?.toModel()

    }
    override suspend fun addProject(title: String, desc: String): String {
        val project = ProjectDoc ("", title, desc)
        return getProjectRef().add(project).await().id
    }
    override suspend fun editProject(id: String, title: String, desc: String) {
        val project = ProjectDoc(id, title, desc)
        getProjectRef().document(id).set(project).await()

    }

    override suspend fun deleteProject(id: String){
        getProjectRef().document(id).delete().await()
    }

}