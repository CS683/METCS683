package edu.bu.widgetsexplore

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.SwitchCompat

class MainActivity : AppCompatActivity() {
    // use lateinit to initialize them later
    private lateinit var nameET:EditText
    private lateinit var countrySp:Spinner
    private lateinit var isAdultCb:CheckBox
    private lateinit var genderRG:RadioGroup
    private lateinit var isPublicSw:SwitchCompat
    private lateinit var commentsET:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //get the references to those widgets after
        //they are inflated.
        nameET = findViewById(R.id.editTextId_name)
        countrySp= findViewById(R.id.spinnerId_country)
        genderRG = findViewById(R.id.radioGroupId_gender)
        isAdultCb = findViewById(R.id.checkBoxId_age)
        isPublicSw = findViewById(R.id.switchId_public)
        commentsET = findViewById(R.id.editTextId_comments)

        // set the submit button event listener
       findViewById<Button>(R.id.buttonId_submit)!!.setOnClickListener {
           submitInfo() }
    }

    @SuppressLint("StringFormatInvalid")
    private fun submitInfo() {
        // get the name from the EditText
        val name = nameET.text.toString().trim()
        val country = countrySp.selectedItem.toString()
        val isAdult = isAdultCb.isChecked
        val isAdultStr = if(isAdult) "18 years or older " else "18 years younger"

        // get the gender from the RadioGroup
        val gender = when (genderRG.checkedRadioButtonId) {
            R.id.radioButtonId_female -> "female"
            R.id.radioButtonId_male -> "male"
            else -> "unknown"
        }
         // get isPublic from the Switch
        val isPublic = isPublicSw.isChecked
        val isPublicStr= if (isPublic) "public" else "private"

        // get comments from the Multiline EditText
        val comments = commentsET.text.toString()

        // compose a message from the above information
        if (name.isNotEmpty()) {
            findViewById<TextView>(R.id.textViewId_info).text =
                getString(R.string.yourinfosummary, name, country, gender,
                    isAdultStr, isPublicStr, comments)
            // display a toast message
            Toast.makeText(this, "Submit successfully!", Toast.LENGTH_LONG).show()
        } else
            Toast.makeText(this, "Please input your name!", Toast.LENGTH_LONG).show()

        clearData()

    }
    private fun clearData() {
        nameET.text?.clear()
        countrySp.id = 0
        isAdultCb.isChecked = false
        genderRG.clearCheck()
        isPublicSw.isChecked = false
        commentsET.text?.clear()
        nameET.requestFocus()
    }
}