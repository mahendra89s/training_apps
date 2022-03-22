package com.example.neosoftstore

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.menu_badges.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.neosoftstore.apis.ApiClient
import com.example.neosoftstore.apis.SharedPrefManagerForUserLogReg
import com.example.neosoftstore.models.EditUserModel
import com.example.neosoftstore.models.UserInfoModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_my_account.*
import kotlinx.android.synthetic.main.menu_badges.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeScreenActivity : AppCompatActivity() {
    lateinit var toggle : ActionBarDrawerToggle
    lateinit var view : View
    lateinit var navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        // Custom Toolbar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.homeScreenToolbar) as androidx.appcompat.widget.Toolbar?
        setSupportActionBar(toolbar)


        // Navigation Drawer
        toggle = object : ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close){
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                val scaleFactor = 7f
                val slideX = drawerView.width * slideOffset

                holder.translationX = slideX
//                holder.scaleX = 1 - slideOffset / scaleFactor
                holder.scaleY = 1 - slideOffset / scaleFactor
                super.onDrawerSlide(drawerView, slideOffset)

            }
        }
        drawerLayout.addDrawerListener(toggle)
        drawerLayout.setScrimColor(Color.TRANSPARENT)
        drawerLayout.drawerElevation = 0f
        toggle.syncState()







        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_icon)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // Set Badge text
        view = LayoutInflater.from(this).inflate(
            R.layout.menu_badges,
            null,
            false
        )
        navView.menu.findItem(R.id.menuMyCart).actionView = view.badgeTextView


        //Nav Menu Item Click
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menuMyOrders -> {
                    navController.navigate(R.id.myOrderFragment)
                    drawerLayout.closeDrawers()
                    return@setNavigationItemSelectedListener false
                }
                R.id.menuStoreLocator -> {
                    navController.navigate(R.id.storeLocatorFragment)
                    drawerLayout.closeDrawers()
                    return@setNavigationItemSelectedListener false
                }
                R.id.menuMyCart -> {
                    navController.navigate(R.id.myCartFragment)
                    drawerLayout.closeDrawers()
                    return@setNavigationItemSelectedListener false
                }
                R.id.menuMyAccount -> {
                    navController.navigate(R.id.myAccountFragment)
                    drawerLayout.closeDrawers()
                    return@setNavigationItemSelectedListener false
                }
                R.id.menuTable -> {
                    val bundle = Bundle()
                    bundle.putInt("product_category_id",1)
                    bundle.putString("catName","Tables")
                    navController.navigate(R.id.productListingFragment,bundle)
                    drawerLayout.closeDrawers()
                    return@setNavigationItemSelectedListener false
                }
                R.id.menuChairs -> {
                    val bundle = Bundle()
                    bundle.putInt("product_category_id",2)
                    bundle.putString("catName","Chairs")
                    navController.navigate(R.id.productListingFragment,bundle)
                    drawerLayout.closeDrawers()
                    return@setNavigationItemSelectedListener false
                }
                R.id.menuSofas -> {
                    val bundle = Bundle()
                    bundle.putInt("product_category_id",3)
                    bundle.putString("catName","Sofas")
                    navController.navigate(R.id.productListingFragment,bundle)
                    drawerLayout.closeDrawers()
                    return@setNavigationItemSelectedListener false
                }
                R.id.menuCupboards -> {
                    val bundle = Bundle()
                    bundle.putInt("product_category_id",4)
                    bundle.putString("catName","Cupboards")
                    navController.navigate(R.id.productListingFragment,bundle)
                    drawerLayout.closeDrawers()
                    return@setNavigationItemSelectedListener false
                }
                R.id.menuLogout -> {
                    SharedPrefManagerForUserLogReg.getInstance(applicationContext).clearAccessKey()
                    if(!SharedPrefManagerForUserLogReg.getInstance(this).isLogged){
                        Intent(this,LoginActivity::class.java).also{
                            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(it)
                        }
                    }
                    return@setNavigationItemSelectedListener false
                }
                else -> return@setNavigationItemSelectedListener false

            }
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController

        toolbar?.setupWithNavController(navController,drawerLayout)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_icon)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
           when(destination.id){
               R.id.myAccountFragment,
               R.id.productDetailsFragment,
               R.id.productListingFragment,
               R.id.editProfileFragment,
               R.id.myCartFragment,
               R.id.listAddressFragment,
               R.id.addAddressFragment,
               R.id.myOrderFragment,
               R.id.orderDetailsScreen,
               R.id.resetPasswordFragment,
               R.id.storeLocatorFragment-> {
                   supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_btn)
               }
               R.id.homeFragment -> {
                   supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_icon)
               }
           }
        }
        toolbar?.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.searchBar -> {
                    Toast.makeText(this,"SearchBar Clicked",Toast.LENGTH_LONG).show()
                }
                R.id.menu_add_address -> {
                    navController.navigate(R.id.action_listAddressFragment_to_addAddressFragment)
                    return@setOnMenuItemClickListener false
                }
            }
            return@setOnMenuItemClickListener false
        }
        fetchUserDetail()
    }

    override fun onStart() {
        super.onStart()
        if(!SharedPrefManagerForUserLogReg.getInstance(this).isLogged){
            Intent(this,LoginActivity::class.java).also{
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
        fetchUserDetail()
    }
    fun refreshData(){
        fetchUserDetail()
    }

    private fun fetchUserDetail(){
        val accessKey = SharedPrefManagerForUserLogReg.getInstance(applicationContext).accessKey
        val call = ApiClient.getUserInfoClient.getUserDetails(accessKey!!, accessKey)
        call.enqueue(object : Callback<UserInfoModel> {
            override fun onResponse(call: Call<UserInfoModel>, response: Response<UserInfoModel>) {
                if(response.body()?.status == 200){

                    val userName : String = response.body()!!.data.user_data.first_name +" "+response.body()!!.data.user_data.last_name
                    val email : String = response.body()!!.data.user_data.email
                    val profile : String = response.body()!!.data.user_data.profile_pic

                    view.badgeTextView.text = response.body()!!.data.total_carts.toString()

                    val sharedPref = applicationContext.getSharedPreferences("address_list", Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putString("userName",userName)
                    editor.apply()

                    val navHeader = navView.getHeaderView(0)
                    navHeader.findViewById<TextView>(R.id.username_header).text = userName
                    navHeader.findViewById<TextView>(R.id.email_header).text = email
                    Glide.with(applicationContext).load(profile).into(navHeader.findViewById<ImageView>(R.id.imageView))

                }
            }

            override fun onFailure(call: Call<UserInfoModel>, t: Throwable) {
                Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_screen_app_bar,menu)
        return true
    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        super.onOptionsItemSelected(item)
//        // For Toolbar
//        when(item.itemId){
//            R.id.searchBar -> Toast.makeText(this,"SearchBar Clicked",Toast.LENGTH_LONG).show()
//        }
////        // For Navigation Drawer
////        if(toggle.onOptionsItemSelected(item)){
////            return true
////        }
//        return true
//    }
}