package org.wit.hillforts.views.login


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import org.jetbrains.anko.toast
import org.wit.hillforts.models.HillfortModel
import org.wit.hillforts.models.firebase.HillfortFireStore
import org.wit.hillforts.views.BasePresenter
import org.wit.hillforts.views.BaseView
import org.wit.hillforts.views.VIEW

class LoginPresenter(view: BaseView) : BasePresenter(view) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: HillfortFireStore? = null
    lateinit var db: DatabaseReference
    val hillforts = ArrayList<HillfortModel>()
    val hillfort = HillfortModel()

    init {
        if (app.hillforts is HillfortFireStore) {
            fireStore = app.hillforts as HillfortFireStore
        }
    }

    fun doLogin(email: String, password: String) {
        view?.showProgress()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                if (fireStore != null) {
                    fireStore!!.fetchHillforts {
                        view?.hideProgress()
                        view?.navigateTo(VIEW.LIST)
                    }
                } else {
                    view?.hideProgress()
                    view?.navigateTo(VIEW.LIST)
                }
            }else {
                view?.hideProgress()
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }
        }
    }

    fun doSignUp(email: String, password: String) {
        view?.showProgress()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                fireStore!!.seed()
                fireStore!!.fetchHillforts {
                    view?.hideProgress()
                }
                view?.navigateTo(VIEW.LIST)
            }
                else {
                view?.hideProgress()
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }

        }
    }





}