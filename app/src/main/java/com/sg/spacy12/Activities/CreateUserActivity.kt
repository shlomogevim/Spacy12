package com.sg.spacy12.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.sg.spacy12.R
import com.sg.spacy12.databinding.ActivityCreateUserBinding
import com.sg.spacy12.utilities.DATE_CREATED
import com.sg.spacy12.utilities.USERS_REF
import com.sg.spacy12.utilities.TAG
import com.sg.spacy12.utilities.USERNAME

class CreateUserActivity : AppCompatActivity() {
    lateinit var binding:ActivityCreateUserBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
    }

    fun createCreateClicked(view: View) {
        /* val email = binding.createEmailTxt.text.toString()
         val password = binding.cratePasswordText.text.toString()
         val username = binding.createUsernameTxt.text.toString()*/

        /*val email="shlomo10@gmail.com"
        val password="123456"
        val username ="shlomo10"*/
        /* val email="shlomo11@gmail.com"
         val password="123456"
         val username ="shlomo11"*/

        var password = binding.cratePasswordText.text.toString()
        password="${password}11111"
        val username = binding.createUsernameTxt.text.toString()
        val email="${username}@gmail.com"

        Log.i(TAG,"email=$email ,password=$password ,username=$username")

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                Log.e(TAG, "insid1: ${result}")
                val changeRequest = UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build()
                Log.e(TAG, "insid2: ${result}")
                result.user.updateProfile(changeRequest)
                    .addOnFailureListener { exception ->
                        Log.e(
                            TAG,"could not update display name: ${exception.localizedMessage}"
                        )
                    }
                val data = HashMap<String, Any>()
                data[USERNAME] = username
                data[DATE_CREATED] = FieldValue.serverTimestamp()

                FirebaseFirestore.getInstance().collection(USERS_REF).document(result.user.uid)
                    .set(data)
                    .addOnSuccessListener {
                        finish()
                    }
                    .addOnFailureListener { exception ->
                        Log.i(TAG,"could not add user document: ${exception.localizedMessage}")
                    }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "could not create user: ${exception.localizedMessage}")
            }
    }
    fun createCancelClicked(view: View) {
        finish()
    }
}