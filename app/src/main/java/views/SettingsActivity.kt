package views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.mickeyassistantadmin.R
import extensions.Extensions.toast
import utils.FirebaseUtils.database
import utils.FirebaseUtils.firebaseAuth

class SettingsActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvResetPass: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        tvName = findViewById(R.id.tvName)
        tvEmail = findViewById(R.id.tvEmail)
        tvResetPass = findViewById(R.id.tvResetPass)

        database.child("user").get()
            .addOnSuccessListener {

                if(it.exists()){

                    val name = it.child("name").value
                    tvName.text = name.toString()
                    val email = it.child("email").value
                    tvEmail.text = email.toString()
                }
                else {
                    toast("Data retrieve failure")
                }

            }
            .addOnFailureListener {
                toast("Failed!")
            }

        tvResetPass.setOnClickListener {

            database.child("user").get()
                .addOnSuccessListener {

                    if(it.exists()){

                        val name = it.child("name").value
                        val email = it.child("email").value

                        firebaseAuth.sendPasswordResetEmail("shriharan18@gmail.com")
                            .addOnCompleteListener { task ->
                                if(task.isSuccessful){
                                    toast("Email sent")
                                }
                            }
                    }
                    else {
                        toast("Data retrieve failure")
                    }

                }
                .addOnFailureListener {
                    toast("Failed!")
                }
        }
    }
}