package edu.bu.projectportal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class EditProjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_project)

        val projTitle = findViewById<EditText> (R.id.projTitleEdit)
        val projDesc =  findViewById<EditText> (R.id.projDescEdit)

        val submit = findViewById<Button>(R.id.submit)
        val cancel = findViewById<Button>(R.id.cancel)

        projTitle.setText(Project.project.title)
        projDesc.setText(Project.project.description)


        val editProjDoneListener = View.OnClickListener{ view ->
            if (view.id == R.id.submit) {
                Project.project.title = projTitle.text.toString()
                Project.project.description = projDesc.text.toString()
            }
            startActivity(Intent(this, MainActivity::class.java))

        }

        submit.setOnClickListener (editProjDoneListener)
        cancel.setOnClickListener (editProjDoneListener)

    }
}