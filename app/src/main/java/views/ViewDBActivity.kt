package views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.mickeyassistantadmin.R
import extensions.Extensions.toast
import utils.FirebaseUtils.database

class ViewDBActivity : AppCompatActivity() {

    lateinit var etClass: EditText
    lateinit var readdataBtn: Button
    lateinit var tvClass: TextView
    lateinit var tvName: TextView
    lateinit var textLeft1: TextView
    lateinit var textLeft2: TextView
    lateinit var autoComplete: AutoCompleteTextView
    lateinit var parentName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_dbactivity)

        val items = listOf("Class Teachers", "School", "Club Activity", "Co-Curriculum")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)

        autoComplete = findViewById(R.id.auto_complete_view)
        autoComplete.setAdapter(adapter)

        etClass = findViewById(R.id.etClass)
        readdataBtn = findViewById(R.id.readDataBtn)
        tvClass = findViewById(R.id.tvClass)
        tvName = findViewById(R.id.tvName)
        textLeft1 = findViewById(R.id.textLeft1)
        textLeft2 = findViewById(R.id.textLeft2)

        parentName = ""

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

        readdataBtn.setOnClickListener {

            val className: String = etClass.text.toString().uppercase()
            if(parentName.isNotEmpty() && className.isNotEmpty()){

                readData(parentName, className)
            } else {
                toast("Please enter all fields")
            }
        }

    }

    private fun readData(parent: String, className: String){

       database.child(parent).child(className).get()
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
}