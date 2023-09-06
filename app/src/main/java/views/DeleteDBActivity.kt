package views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.mickeyassistantadmin.R
import extensions.Extensions.toast
import utils.FirebaseUtils

class DeleteDBActivity : AppCompatActivity() {

    lateinit var etClass: EditText
    lateinit var deleteDataBtn: Button
    lateinit var readDataBtn: Button
    lateinit var tvClass: TextView
    lateinit var tvName: TextView
    lateinit var textLeft1: TextView
    lateinit var textLeft2: TextView
    lateinit var autoComplete: AutoCompleteTextView
    lateinit var parentName: String
    lateinit var className: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_dbactivity)

        val items = listOf("Class Teachers", "School", "Club Activity", "Co-Curriculum")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)

        autoComplete = findViewById(R.id.auto_complete_view)
        autoComplete.setAdapter(adapter)

        etClass = findViewById(R.id.etClass)
        deleteDataBtn = findViewById(R.id.deleteDataBtn)
        readDataBtn = findViewById(R.id.readDataBtn)
        tvClass = findViewById(R.id.tvClass)
        tvName = findViewById(R.id.tvName)
        textLeft1 = findViewById(R.id.textLeft1)
        textLeft2 = findViewById(R.id.textLeft2)

        parentName = ""
        className = ""

        autoComplete.onItemClickListener = AdapterView.OnItemClickListener {
                adapterView, view, i, l ->

            val itemSelected = adapterView.getItemAtPosition(i).toString()
            when(itemSelected){

                "Class Teachers" -> {
                    parentName = "class_teachers"
                    etClass.hint = "Class (e.g: '12 C')"
                    textLeft1.text = "CLASS:"
                    textLeft2.text = "CLASS TEACHER:"}
                "School" ->         {
                    parentName = "school"
                    etClass.hint = "Enter designation"
                    textLeft1.text = "DESIGNATION:"
                    textLeft2.text = "NAME:"}
                "Club Activity" -> {
                    parentName = "clubs"
                    etClass.hint = "Club Name"
                    textLeft1.text = "CLUB NAME:"
                    textLeft2.text = "TEAM LEADER:"}
                "Co-Curriculum" -> {
                    parentName = "co-curriculum"
                    etClass.hint = "Co-Curriculum"
                    textLeft1.text = "CO-CURRICULUM:"
                    textLeft2.text = "TEACHER NAME:"}
            }
            toast("Item: $itemSelected")

        }

        readDataBtn.setOnClickListener {
            className = etClass.text.toString().uppercase()
            if(parentName.isNotEmpty() && className.isNotEmpty()){

                readData(parentName, className)
            } else {
                toast("Please enter all fields")
            }
        }

        deleteDataBtn.setOnClickListener {
            if(parentName.isNotEmpty() && className.isNotEmpty()){

                val mBuilder = AlertDialog.Builder(this)
                    .setTitle("Confirm")
                    .setMessage("Are you sure you want to delete the data?")
                    .setPositiveButton("Yes", null)
                    .setNegativeButton("No", null)
                    .show()

                val mPositiveButton = mBuilder.getButton(AlertDialog.BUTTON_POSITIVE)
                mPositiveButton.setOnClickListener {
                    deleteData(parentName, className)
                }
            }
            else{
                toast("Please enter all fields")
            }

        }

    }

    private fun readData(parent: String, className: String){

        FirebaseUtils.database.child(parent).child(className).get()
            .addOnSuccessListener {

                if(it.exists()){

                    val name = it.child("name").value
                    etClass.text.clear()
                    tvClass.text = className
                    tvName.text = name.toString()


                } else {

                    toast("Doesn't exists!")
                    etClass.text.clear()
                }
            }.addOnFailureListener {

                toast("Failed!")
            }

    }

    private fun deleteData(parent: String, className: String){

        FirebaseUtils.database.child(parent).child(className).removeValue()
            .addOnSuccessListener {
                toast("Data deleted successfully!")
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                toast("Data deletion failed!")
            }
    }

}