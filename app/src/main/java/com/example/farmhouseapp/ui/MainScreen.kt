package com.example.farmhouseapp.ui

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.farmhouseapp.AnimalAdaptor
import com.example.farmhouseapp.R
import com.example.farmhouseapp.SharedPreferencesUtils
import com.example.farmhouseapp.Users
import com.example.farmhouseapp.models.Animal
import com.example.farmhouseapp.utils.Constants
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main_screen.*
import kotlinx.android.synthetic.main.drawerlayout.*
import java.util.*


class MainScreen : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    SearchView.OnQueryTextListener {
    lateinit var animalAdaptor: AnimalAdaptor
    lateinit var arrayList: ArrayList<Animal>
    lateinit var layout: LinearLayoutManager
    lateinit var myNavHostFragment: NavHostFragment
    lateinit var navController: NavController

    //var name = "${SharedPreferencesUtils.getUserRole(this)}"
    //  var name :String?=null
    lateinit var drawer_layout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawerlayout)
        drawer_layout = findViewById(R.id.drawer_layout)
        myNavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = myNavHostFragment.navController
        if (SharedPreferencesUtils.getUserRole(this) == "${Users.SELLER}") {
            val inflater = myNavHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.seller_navigations)
            myNavHostFragment.navController.graph = graph
            nav_view_drawer.getMenu().clear();
            nav_view_drawer.inflateMenu(R.menu.seller_menu);
            /* btn_start.setOnClickListener {
                 startActivity(Intent(this, AddAnimalActivty::class.java))
             }*/
        } else if (SharedPreferencesUtils.getUserRole(this) == "${Users.BUYER}") {
            val inflater = myNavHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.buyyer_navigation)
            myNavHostFragment.navController.graph = graph
            nav_view_drawer.getMenu().clear();
            nav_view_drawer.inflateMenu(R.menu.buyer_menu);
        } else if (SharedPreferencesUtils.getUserRole(this) == "${Users.DOCTOR}") {
            val inflater = myNavHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.doctor_navigation)
            myNavHostFragment.navController.graph = graph
            nav_view_drawer.getMenu().clear();
            nav_view_drawer.inflateMenu(R.menu.doctors_menu);

        } else if (SharedPreferencesUtils.getUserRole(this) == "${Users.ADMIN}") {
            val inflater = myNavHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.nav_graph)
            myNavHostFragment.navController.graph = graph
            nav_view_drawer.getMenu().clear();
            nav_view_drawer.inflateMenu(R.menu.admin_menu);

        }

        listenerFunction()
        setNavigationViewListener()

    }

    private fun listenerFunction() {
        if (SharedPreferencesUtils.getUserRole(this) == "${Users.SELLER}") {
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                when (destination.id) {
                    R.id.addAnimalinSeller -> {
                        tvToolbarTitle.text = "Add Animals"


                        //  showBottomNavigationView()
                    }
                    R.id.addFarmInSeller -> {

                        tvToolbarTitle.text = "Add Farm"


                    }
                    R.id.showFarmFragment -> {
                        tvToolbarTitle.text = "Farms"

                    }


                }


            }
        } else if (SharedPreferencesUtils.getUserRole(this) == "${Users.BUYER}") {
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                when (destination.id) {
                    R.id.showAnimalFragment -> {
                        tvToolbarTitle.text = "Animals"

                    }
                    R.id.orderAnimalFragment -> {
                        tvToolbarTitle.text = "Order Animal"

                    }
                    R.id.addBillingInformation -> {
                        tvToolbarTitle.text = "Add Billing Information"

                    }
                    R.id.chattingwithFargment -> {
                        tvToolbarTitle.text = "Chat"

                    }


                }


            }
        } else if (SharedPreferencesUtils.getUserRole(this) == "${Users.DOCTOR}") {
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                when (destination.id) {
                    R.id.doctorMainPage -> {
                        tvToolbarTitle.text = "Doctor"
                    }
                    R.id.addDoctor2 -> {
                        tvToolbarTitle.text = "Add Details"
                    }


                }


            }

        } else if (SharedPreferencesUtils.getUserRole(this) == "${Users.ADMIN}") {
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                when (destination.id) {
                    R.id.addUser -> {

                    }
                    R.id.addUser -> {

                    }


                }


            }

        }


    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (SharedPreferencesUtils.getUserRole(this) == "${Users.SELLER}") {
            return when (item.itemId) {
                R.id.addanimal -> {
                    drawer_layout.closeDrawer(GravityCompat.START)
                    navController.navigate(R.id.action_showFarmFragment_to_addAnimalinSeller)
                    true
                }
                R.id.farmrating -> {
                    drawer_layout.closeDrawer(GravityCompat.START)
                    // startActivity(Intent(this@MainActivity, SettingConActivity::class.java))
                    true
                }
                R.id.edtifarm -> {
                    drawer_layout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        } else if (SharedPreferencesUtils.getUserRole(this) == "${Users.BUYER}") {
            return when (item.itemId) {
                R.id.myordershistry -> {
                    drawer_layout.closeDrawer(GravityCompat.START)



                    when (navController.currentDestination?.id) {
                        R.id.showAnimalFragment -> {
                            navController.navigate(R.id.action_showAnimalFragment_to_orderHistory)

                        }
                        R.id.orderHistory -> {
                            navController.navigate(R.id.action_orderHistory_self)
                        }

                    }
//                    if(navController.currentDestination?.id= R.id.showAnimalFragment){
//
//
//                    }
                    true
                }

                else -> super.onOptionsItemSelected(item)
            }
        } else if (SharedPreferencesUtils.getUserRole(this) == "${Users.DOCTOR}") {
            return when (item.itemId) {
                R.id.apointments -> {
                    drawer_layout.closeDrawer(GravityCompat.START)
                    true
                }

                else -> super.onOptionsItemSelected(item)
            }
        } else if (SharedPreferencesUtils.getUserRole(this) == "${Users.ADMIN}") {
            return when (item.itemId) {
                R.id.addbuyer -> {
                    Constants.userType= "${Users.BUYER}"
                    tvToolbarTitle.text = "Add Buyer"
                    navController.navigate(R.id.action_adminMainScreen_to_addUser)
                    drawer_layout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.adddoctor -> {
                    Constants.userType= "${Users.DOCTOR}"
                    tvToolbarTitle.text = "Add Doctor"
                    navController.navigate(R.id.action_adminMainScreen_to_addUser)
                    drawer_layout.closeDrawer(GravityCompat.START)
                    true
                } R.id.addfarmer -> {
                    Constants.userType= "${Users.SELLER}"
                    tvToolbarTitle.text = "Add Farmer"
                    navController.navigate(R.id.action_adminMainScreen_to_addUser)
                    drawer_layout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.managerating -> {
                    tvToolbarTitle.text = "Rating"

                    drawer_layout.closeDrawer(GravityCompat.START)
                    true
                }

                else -> super.onOptionsItemSelected(item)
            }
        }


        /*    return when (item.itemId) {
                R.id.nav_about_us -> {
                    drawer_layout.closeDrawer(GravityCompat.START)


                    true
                }
                R.id.settings -> {
                    // startActivity(Intent(this@MainActivity, SettingConActivity::class.java))
                    true
                }
                R.id.nav_share_app -> {
                    true
                }
                R.id.nav_rate_us -> {


                    true
                }
                R.id.nav_privacy -> {
                    true
                }
                R.id.nav_remove_ads -> {
                    true
                }

                else -> super.onOptionsItemSelected(item)
            }*/
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setNavigationViewListener() {
        val navigationView = findViewById<View>(R.id.nav_view_drawer) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.setItemIconTintList(null);
        navigationicon.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


    override fun onQueryTextChange(query: String?): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }


    override fun onBackPressed() {
        if (SharedPreferencesUtils.getUserRole(this) == "${Users.SELLER}") {


            when (navController.currentDestination?.id) {
                R.id.addanimal -> {

                }

                R.id.farmrating -> {

                }
                R.id.showFarmFragment -> {
                    backPressed()
                }

                else -> {
                    backPressed()
                }
            }


        } else if (SharedPreferencesUtils.getUserRole(this) == "${Users.BUYER}") {
            when (navController.currentDestination?.id) {
                R.id.orderHistory -> {

                }

                R.id.showAnimalFragment -> {

                }

                else -> {
                    backPressed()
                }
            }

        } else if (SharedPreferencesUtils.getUserRole(this) == "${Users.DOCTOR}") {

            when (navController.currentDestination?.id) {
                R.id.orderHistory -> {


                }

                R.id.showAnimalFragment -> {

                }

                else -> {
                    backPressed()
                }
            }
        }

    }

    private fun backPressed() {
        SharedPreferencesUtils?.setUserEmail(this,"")
        SharedPreferencesUtils?.setOwner(this,"")
        SharedPreferencesUtils?.setUserRole(this,"")
        SharedPreferencesUtils?.setFirstName(this,"")
        SharedPreferencesUtils?.setFarmName(this,"")
        SharedPreferencesUtils?.setUserPhone(this,"")
        System.exit(0)
    }


}