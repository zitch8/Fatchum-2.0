package com.example.fatchum2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {

    private var passCheck1: Boolean = false; private var passCheck2: Boolean = false
    private var passCheck3: Boolean = false; private var canContinue: Boolean = false

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var pass: String

    // For Password Validation
    private lateinit var passValidation1: CardView
    private lateinit var passValidation2: CardView
    private lateinit var passValidation3: CardView
    private lateinit var passText1: TextView
    private lateinit var passText2: TextView
    private lateinit var passText3: TextView

    //
    private lateinit var changedName: EditText
    private lateinit var changedEmail: EditText
    private lateinit var changedPassword: EditText

    //
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var user: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Set Back Function
        val login = findViewById<TextView>(R.id.tvLogin)
        login.setOnClickListener {
            this.onBackPressed()
            finish()
        }

        db = Firebase.firestore
        auth = Firebase.auth

        val getStartedBtn = findViewById<Button>(R.id.btnRegister)
        getStartedBtn.setOnClickListener{
            name = findViewById<EditText>(R.id.editRegisterName).text.toString().trim()
            email = findViewById<EditText>(R.id.editRegisterEmail).text.toString().trim()
            pass = findViewById<EditText>(R.id.editRegisterPassword).text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty()){
                if(canContinue){
                    checkIfEmailExists(email) { emailExists ->
                        if (emailExists) {
                            Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show()
                        } else {
                            val newUser = hashMapOf<String, String>(
                                "name" to name!!,
                                "email" to email!!,
                                "profile" to "1"
                            )
                            createAccount(newUser, pass)
                        }
                    }
                } else if (!passCheck1 || !passCheck2 || !passCheck3){
                    Toast.makeText(this, "The entered password is invalid.", Toast.LENGTH_SHORT).show()
                }
            } else{
                Toast.makeText(this, "Input must not be empty.", Toast.LENGTH_SHORT).show()
            }
        }

        // To check password if valid
        contChange()
    }

    // To validate input password
    @SuppressLint("ResourceAsColor")
    private fun passwordValidation(name: String, email: String, passwordCheck: String){
        passValidation1 = findViewById(R.id.valid1)
        passValidation2 = findViewById(R.id.valid2)
        passValidation3 = findViewById(R.id.valid3)
        passText1 = findViewById(R.id.tvValidation1)
        passText2 = findViewById(R.id.tvValidation2)
        passText3 = findViewById(R.id.tvValidation3)

        if (passwordCheck.length >= 8){
            passValidation1.setCardBackgroundColor(ContextCompat.getColor(this, R.color.green))
            passText1.setTextColor(ContextCompat.getColor(this,R.color.green))
            passCheck1 = true
        } else {
            passValidation1.setCardBackgroundColor(ContextCompat.getColor(this, R.color.gray))
            passText1.setTextColor(ContextCompat.getColor(this,R.color.gray))
            passCheck1 = false

        }

        // PASSWORD VALIDATION
        if (("^.*[A-Z].*$").toRegex().matches(passwordCheck)){
            passValidation2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.green))
            passText2.setTextColor(ContextCompat.getColor(this,R.color.green))
            passCheck2 = true
        } else {
            passValidation2.setCardBackgroundColor(ContextCompat.getColor(this, R.color.gray))
            passText2.setTextColor(ContextCompat.getColor(this,R.color.gray))
            passCheck2 = false
        }

        if(("^.*[0-9].*$").toRegex().matches(passwordCheck)){
            passValidation3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.green))
            passText3.setTextColor(ContextCompat.getColor(this,R.color.green))
            passCheck3 = true
        } else {
            passValidation3.setCardBackgroundColor(ContextCompat.getColor(this, R.color.gray))
            passText3.setTextColor(ContextCompat.getColor(this,R.color.gray))
            passCheck3 = false
        }

        checkAll(name, email)
    }

    private fun checkAll(name: String, email: String) {
        if (passCheck1 && passCheck2 && passCheck3 && name.isNotEmpty() && email.isNotEmpty()) {
            canContinue = true
        } else {
            canContinue = false
        }
    }

    // To check changes in name, email, and password
    private fun contChange(){
        changedName = findViewById(R.id.editRegisterName)
        changedEmail = findViewById(R.id.editRegisterEmail)
        changedPassword = findViewById(R.id.editRegisterPassword)

        changedName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("TextWatcher", "Password text changed: $s")
                passwordValidation(changedName.text.toString(), changedEmail.text.toString(), changedPassword.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        changedEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("TextWatcher", "Password text changed: $s")
                passwordValidation(changedName.text.toString(), changedEmail.text.toString(), changedPassword.text.toString())
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        changedPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("TextWatcher", "Password text changed: $s")
                passwordValidation(changedName.text.toString(), changedEmail.text.toString(), changedPassword.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun checkIfEmailExists(email: String, callback: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val existing = !querySnapshot.isEmpty
                callback(existing)
            }
            .addOnFailureListener { _ ->
                // Handle the failure
                Log.w("FIREBASE_DB_LOG", "Adding User to DB:failure")
                Toast.makeText(
                    baseContext,
                    "Connection failed.",
                    Toast.LENGTH_SHORT
                ).show()
                callback(false) // Indicate failure
            }
    }
    fun createAccount(newUser: HashMap<String, String>, pass: String?) {
        val email = newUser["email"]
        auth.createUserWithEmailAndPassword(email ?: "", pass ?: "")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("FIREBASE_AUTH_LOG", "createUserWithEmail:success")
                    user = auth.currentUser

                    val uid = user?.uid ?:""
                    if (uid == "") {
                        Log.w("FIREBASE_DB_LOG", "Adding User to DB:failure no UID")
                        Toast.makeText(
                            baseContext,
                            "Adding to database failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        return@addOnCompleteListener
                    }

                    newUser.set("uid",uid)
                    db.collection("users").document(uid)
                        .set(newUser)
                        .addOnSuccessListener {
                            Log.w("FIREBASE_DB_LOG", "Adding User to DB:Success")
                            val openLogin = Intent(this@Register, Login::class.java)
                            startActivity(openLogin)
                            finish()
                        }
                        .addOnFailureListener {
                            Log.w("FIREBASE_DB_LOG", "Adding User to DB:failure", it)
                            Toast.makeText(
                                baseContext,
                                "Adding to database failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("FIREBASE_AUTH_LOG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}