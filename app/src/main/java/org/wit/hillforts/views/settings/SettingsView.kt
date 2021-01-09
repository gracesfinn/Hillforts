package org.wit.hillforts.views.settings

import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_hillfort_map.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.user_login.*
import kotlinx.android.synthetic.main.user_login.toolbarAdd
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hillforts.R
import org.wit.hillforts.views.BaseView



class SettingsView : BaseView() {

    lateinit var presenter: SettingsPresenter
    override var user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        toolbar.title = user?.email

        presenter = initPresenter(SettingsPresenter(this)) as SettingsPresenter


        settingsBtn.setOnClickListener() {
            val email = update_email.text.toString()
            val email2 = update_email2.text.toString()

            if (email != email2) {
                toast("Email Mismatch, Please Re-enter")
            }
            else if(email == null){
                toast("Please Enter an Email Address")
            }
            else if(email2 == null){
                toast("Please Verify Email Address")
            }
            else {
                toast("Email Updated")
                presenter.doUpdateEmail(email)
            }
        }

        resetPassword.setOnClickListener(){
            toast("Password Reset Sent - Please Check Your Email")
            presenter.doSendPasswordReset()

        }

    }
}