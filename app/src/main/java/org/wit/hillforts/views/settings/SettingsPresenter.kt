package org.wit.hillforts.views.settings

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.email
import org.jetbrains.anko.toast
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.firebase.HillfortFireStore
import org.wit.hillforts.views.BasePresenter
import org.wit.hillforts.views.BaseView
import org.wit.hillforts.views.VIEW

class SettingsPresenter (view: BaseView) : BasePresenter(view) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var user = FirebaseAuth.getInstance().currentUser
    val emailAddress = user!!.email







    fun doUpdateEmail(email: String) {
        user!!.updateEmail(email)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    Log.d(TAG, "User email address updated")
                }
            }

        }

    fun doSendPasswordReset(){
        if (emailAddress != null) {
            auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Email sent.")
                    }
                }
        }


    }


}
