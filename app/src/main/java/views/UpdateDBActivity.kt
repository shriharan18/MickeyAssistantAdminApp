package views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mickeyassistantadmin.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.FirebaseDatabase
import extensions.Extensions.toast
import utils.FirebaseUtils.database
import utils.FirebaseUtils.firebaseAuth
import utils.FirebaseUtils.firebaseUser

class UpdateDBActivity : AppCompatActivity() {

    lateinit var etChild: EditText
    lateinit var etNewValue: EditText
    lateinit var btnUpdate: Button
    lateinit var autoComplete: AutoCompleteTextView
    lateinit var parentName: String

    lateinit var updateInputsArray: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_dbactivity)

        val items = listOf("Class Teachers", "School", "Club Activity", "Co-Curriculum")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)

        autoComplete = findViewById(R.id.auto_complete)
        autoComplete.setAdapter(adapter)

        etChild = findViewById(R.id.etChild)
        etNewValue = findViewById(R.id.etNewValue)
        btnUpdate = findViewById(R.id.btnUpdate)

        parentName = ""

        updateInputsArray = arrayOf(etChild, etNewValue)

        autoComplete.onItemClickListener = AdapterView.OnItemClickListener {
                adapterView, view, i, l ->

            val itemSelected = adapterView.getItemAtPosition(i).toString()
            when(itemSelected){
                "Class Teachers" -> {parentName = "class_teachers"
                                    etChild.hint = "Enter class name (e.g: '12 C')"
                                    etNewValue.hint = "Enter New Class Teacher Name"}
                "School" ->         {parentName = "school"
                                    etChild.hint = "Enter designation"
                                    etNewValue.hint = "Enter name"}
                "Club Activity" -> {parentName = "clubs"
                                    etChild.hint = "Enter club name"
                                    etNewValue.hint = "Enter the team leader name"}
                "Co-Curriculum" -> {parentName = "co-curriculum"
                                    etChild.hint = "Enter the co-curriculum name"
                                    etNewValue.hint = "Enter the teacher name"}

            }
            toast("Item: $itemSelected")
        }

        btnUpdate.setOnClickListener {
            updateDB(parentName, etChild.text.toString().uppercase(),
                etNewValue.text.toString().uppercase()
                )
        }


    }

    private fun notEmpty(): Boolean = parentName.trim().isNotEmpty() &&
            etChild.text.toString().trim().isNotEmpty() &&
            etNewValue.text.toString().trim().isNotEmpty()

    private fun updateDB(
        parent: String,
        child: String,
        newValue: String
    ){
        if (notEmpty()){

            database.child(parent).child(child).updateChildren(hashMapOf<String, Any>("name" to newValue))
                .addOnSuccessListener {
                    toast("Database has been updated!")
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    toast("Database update failed! Please try again")

                }

        }
        else {
            toast("Enter all the fields!")
        }
    }
}