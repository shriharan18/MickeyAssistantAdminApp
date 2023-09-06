package views

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.mickeyassistantadmin.R
import com.example.mickeyassistantadmin.R.*
import com.google.android.material.navigation.NavigationView
import extensions.Extensions.toast
import utils.FirebaseUtils.firebaseAuth
import kotlin.system.exitProcess

class HomeActivity : AppCompatActivity(){
//    private lateinit var drawerLayout:
    private lateinit var viewDB: LinearLayout
    private lateinit var updateDB: LinearLayout
    private lateinit var deleteDB: LinearLayout
    private lateinit var settings: LinearLayout
    private lateinit var info: LinearLayout
    private lateinit var logout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_home)

        viewDB = findViewById(R.id.viewDB)
        updateDB = findViewById(R.id.updateDB)
        deleteDB = findViewById(R.id.deleteDB)
        settings = findViewById(R.id.settings)
        info = findViewById(R.id.info)
        logout = findViewById(R.id.logout)

        viewDB.setOnClickListener {
            startActivity(Intent(this, ViewDBActivity::class.java))
            finish()
        }
        updateDB.setOnClickListener {
            startActivity(Intent(this, UpdateDBActivity::class.java))
            finish()
        }
        deleteDB.setOnClickListener {
            startActivity(Intent(this, DeleteDBActivity::class.java))
            finish()
        }
        settings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            finish()
        }
        info.setOnClickListener {
            startActivity(Intent(this, InfoActivity::class.java))
            finish()
        }
        logout.setOnClickListener {

            val mBuilder = AlertDialog.Builder(this)
                    .setTitle("Confirm")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Yes", null)
                    .setNegativeButton("No", null)
                    .show()
                val mPositiveButton = mBuilder.getButton(AlertDialog.BUTTON_POSITIVE)
                mPositiveButton.setOnClickListener {
                    logout()
                }

        }

//        updateText = findViewById(id.updateText)
//        btnSignOut.setOnClickListener {
//            firebaseAuth.signOut()
//            startActivity(Intent(this, SignUpActivity::class.java))
//            toast("Logged out!")
//            finish()
//        }
//
//        updateText.setOnClickListener {
//            startActivity(Intent(this, UpdateDBActivity::class.java))
//            finish()
//        }

//        drawerLayout = findViewById(R.id.drawer_layout)
//
//        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)
//
//        val navigationView = findViewById<NavigationView>(R.id.nav_view)
//        navigationView.setNavigationItemSelectedListener(this)
//
//        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//
//        if (savedInstanceState == null){
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, HomeFragment()).commit()
//            navigationView.setCheckedItem(R.id.nav_home)
//        }

    }

    private fun logout(){

        firebaseAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            toast("Logged out!")
            finish()

    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when (item.itemId){
//            R.id.nav_home -> supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, HomeFragment()).commit()
//            R.id.nav_settings -> supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, SettingsFragment()).commit()
//            R.id.nav_info -> supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, AboutFragment()).commit()
//            R.id.nav_logout -> {
//                val mBuilder = AlertDialog.Builder(this)
//                    .setTitle("Confirm")
//                    .setMessage("Are you sure you want to log out?")
//                    .setPositiveButton("Yes", null)
//                    .setNegativeButton("No", null)
//                    .show()
//                val mPositiveButton = mBuilder.getButton(AlertDialog.BUTTON_POSITIVE)
//                mPositiveButton.setOnClickListener {
//                    logout()
//                }
//            }
//        }
//        drawerLayout.closeDrawer(GravityCompat.START)
//        return true
//    }

    override fun onBackPressed() {
        val mBuilder = AlertDialog.Builder(this)
            .setTitle("Confirm")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes", null)
            .setNegativeButton("No", null)
            .show()

        val mPositiveButton = mBuilder.getButton(AlertDialog.BUTTON_POSITIVE)
        mPositiveButton.setOnClickListener{
            finishAffinity()
        }
    }
}