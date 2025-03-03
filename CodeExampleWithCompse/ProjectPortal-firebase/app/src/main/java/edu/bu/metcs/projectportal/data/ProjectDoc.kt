package edu.bu.metcs.projectportal.data

import com.google.firebase.firestore.DocumentId

data class ProjectDoc (
    @DocumentId
    val id: String = "",
    val title: String = "",
    val description: String = ""
)
