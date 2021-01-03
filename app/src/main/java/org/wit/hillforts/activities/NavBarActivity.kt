package org.wit.hillforts.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar_nav.*
import kotlinx.android.synthetic.main.navigation.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.hillforts.R
import org.wit.hillforts.fragments.HillfortFragment
import org.wit.hillforts.fragments.HillfortListFragment
import org.wit.hillforts.models.UserModel
import org.wit.hillforts.views.location.EditLocationView

class NavBarActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    lateinit var ft:FragmentTransaction

    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation)
        setSupportActionBar(navToolbar)


        navView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, navToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        ft = supportFragmentManager.beginTransaction()

        val fragment = HillfortListFragment.newInstance()
        ft.replace(R.id.homeFrame, fragment)
        ft.commit()
    }
    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.homeFrame, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_settings -> startActivityForResult(intentFor<SettingsActivity>().putExtra("User_edit", user), 0)
            R.id.nav_list -> startActivityForResult<HillfortListActivity>(0)
            R.id.nav_map -> startActivity<EditLocationView>()
            R.id.nav_favourites -> startActivityForResult<FavouriteActivity>(0)

            else -> toast("You Selected Something Else")
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

}