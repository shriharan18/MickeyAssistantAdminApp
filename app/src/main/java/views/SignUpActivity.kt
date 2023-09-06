package views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mickeyassistantadmin.R
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseUser
import extensions.Extensions.toast
import utils.FirebaseUtils.firebaseAuth
import utils.FirebaseUtils.firebaseUser

class SignUpActivity : AppCompatActivity() {
    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var btnCreateAccount: Button
    lateinit var btnSignIn2: Button
    lateinit var createAccountInputsArray: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        etEmail = findViewById(R.id.textInputLayout)
        etPassword = findViewById(R.id.etChild)
        etConfirmPassword = findViewById(R.id.etNewValue)
        btnCreateAccount = findViewById(R.id.btnUpdate)
        btnSignIn2 = findViewById(R.id.btnSignIn2)

        createAccountInputsArray = arrayOf(etEmail, etPassword, etConfirmPassword)

        btnCreateAccount.setOnClickListener{
            signIn()
        }
        btnSignIn2.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            toast("Please login into your account!")
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = firebaseAuth.currentUser
        user?.let {
            startActivity(Intent(this, HomeActivity::class.java))
            toast("Welcome back!")
        }
    }

    private fun notEmpty(): Boolean = etEmail.text.toString().trim().isNotEmpty() &&
            etPassword.text.toString().trim().isNotEmpty() &&
            etConfirmPassword.text.toString().trim().isNotEmpty()

    private fun identicalPassword(): Boolean {
        var identical = false
        if (notEmpty() &&
            etPassword.text.toString().trim() == etConfirmPassword.text.toString().trim()
        ) {
            identical = true
        } else if (!notEmpty()) {
            createAccountInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required!"
                }
            }
        } else {
            toast("Passwords are not matching !")
        }
        return identical
    }

    private fun signIn() {
        if (identicalPassword()) {
            // identicalPassword() returns true only  when inputs are not empty and passwords are identical
            userEmail = etEmail.text.toString().trim()
            userPassword = etPassword.text.toString().trim()

            /*create a user*/
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        toast("Account created successfully!")
                        sendEmailVerification()
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else {
                        toast("Failed to authenticate!")
                    }
                }
        }
    }

    /* send verification email to the new user. This will only
    *  work if the firebase user is not null.
    */

    private fun sendEmailVerification() {
        firebaseUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    toast("Email has been sent to $userEmail")
                }
            }
        }
    }
}