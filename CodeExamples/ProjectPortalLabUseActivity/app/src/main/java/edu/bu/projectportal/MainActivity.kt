package edu.bu.projectportal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var projTitle: TextView
    private lateinit var projDesc: TextView
    private lateinit var editProj: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        projTitle = findViewById(R.id.projTitle)
        projDesc =  findViewById(R.id.projDesc)

        editProj = findViewById(R.id.editProj)

        projTitle.text =  Project.project.title
        projDesc.text = Project.project.description

        editProj.setOnClickListener {
            startActivity(Intent(this, EditProjectActivity::class.java))
        }
    }

}