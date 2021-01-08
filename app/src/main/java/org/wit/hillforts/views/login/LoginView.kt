package org.wit.hillforts.views.login

import android.os.Bundle
import android.view.View

import kotlinx.android.synthetic.main.user_login.*
import org.jetbrains.anko.toast
import org.wit.hillforts.R
import org.wit.hillforts.views.BaseView

class LoginView : BaseView() {

    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_login)
        setSupportActionBar(toolbarAdd)
        progressBar.visibility = View.GONE

        presenter = initPresenter(LoginPresenter(this)) as LoginPresenter

        userLoginRegBtn.setOnClickListener {
            val email = login_email.text.toString()
            val password = login_password.text.toString()
            if (email == "" || password == "") {
                toast("Please provide email + password")
            }
            else {
                presenter.doSignUp(email,password)

            }
        }

        userLoginBtn.setOnClickListener {
            val email = login_email.text.toString()
            val password = login_password.text.toString()
            if (email == "" || password == "") {
                toast("Please provide email + password")
            }
            else {
                presenter.doLogin(email,password)
            }
        }
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }
}
