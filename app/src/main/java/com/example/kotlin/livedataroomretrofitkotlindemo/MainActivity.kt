package com.example.kotlin.livedataroomretrofitkotlindemo

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.kotlin.livedataroomretrofitkotlindemo.dagger2.ActivityComponent
import com.example.kotlin.livedataroomretrofitkotlindemo.dagger2.ActivityModule
import com.example.kotlin.livedataroomretrofitkotlindemo.dagger2.DaggerActivityComponent
import com.example.kotlin.livedataroomretrofitkotlindemo.githubconfig.GithubService
import com.example.kotlin.livedataroomretrofitkotlindemo.network.Status
import com.example.kotlin.livedataroomretrofitkotlindemo.network.User
import com.example.kotlin.livedataroomretrofitkotlindemo.utils.DateUtils
import com.example.kotlin.livedataroomretrofitkotlindemo.utils.classOf
import com.example.kotlin.livedataroomretrofitkotlindemo.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

//Make an alias of type
typealias Service = GithubService

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var activityComponent : ActivityComponent? = null

    fun getActivityComponent(): ActivityComponent? {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(ActivityModule(this))
                    .applicationComponent(MainApplication.get(this).getComponent())
                    .build()
        }
        return activityComponent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Inject this activity
        getActivityComponent()?.inject(this);

        //Get the all contributors' contributions count
        btnGetContributors.setOnClickListener { _ ->

            progressbar.visibility = View.VISIBLE
            var githubService = Service.createGithubService(resources.getString(R.string.github_access_token))

            githubService!!.getContributors("square", "retrofit").observe(this, Observer{
                progressbar.visibility = View.GONE
                if(it!!.isSuccessful){
                    var text = ""
                    for(contributor in it.body!!){
                        text += "${contributor.login}=${contributor.contributions}\r\n"
                    }
                    AlertDialog.Builder(this)
                            .setMessage(text)
                            .setCancelable(true)
                            .setPositiveButton(R.string.btn_sure
                                    , DialogInterface.OnClickListener{ dialog, _ ->
                                        dialog.dismiss()
                                    })
                            .create().show()
                }

            })
        }

        //Get the public information of the user, null if none
        btnGetUser.setOnClickListener { _ ->
            progressbar.visibility = View.VISIBLE

            var githubService = Service.createGithubService(resources.getString(R.string.github_access_token))
            githubService!!.getUser(edittext_name.text.toString()).observe(this, Observer{
                progressbar.visibility = View.GONE
                if(it!!.isSuccessful){
                    //Add the name with 'hello '
                    sample_text.text = getUserInfo(it.body!!)
                }else{
                    sample_text.text = it.errorMessage
                }
            })
        }

        //Get the public information of the user, null if none in repository injection mode
        var viewModel = ViewModelProviders.of(this, viewModelFactory).get(classOf<UserViewModel>())
        btnGetUserRepo.setOnClickListener{ _ ->
            progressbar.visibility = View.VISIBLE
            viewModel.initUser(edittext_name.text.toString())
            viewModel.getUser()?.observe(this, Observer{
                when(it?.status){
                    Status.SUCCESS -> {
                        progressbar.visibility = View.GONE
                        sample_text.text = getUserInfo(it.data!!)
                    }
                    Status.ERROR -> {
                        progressbar.visibility = View.GONE
                        sample_text.text = it.message
                    }
                    Status.LOADING -> {}
                }
            })
        }

        //Update the current user's email
        btnUpdateUser.setOnClickListener{_ ->
            progressbar.visibility = View.VISIBLE

            var githubService = Service.createGithubService(resources.getString(R.string.github_access_token))
            githubService!!.getUser(resources.getString(R.string.github_access_name)).observe(this, Observer{

                if(it!!.isSuccessful){
                    var user = it.body!!
                    user.email = edittext_email.text.toString()
                    githubService!!.updateUser(user).observe(this, Observer {
                        progressbar.visibility = View.GONE
                        if(it!!.isSuccessful){
                        //Add the name with 'hello '
                            sample_text.text = getUserInfo(it.body!!)
                        }else{
                            sample_text.text = it.errorMessage
                        }
                    })
                }else{
                    progressbar.visibility = View.GONE
                    sample_text.text = it.errorMessage
                }
            })
        }

        // Example of a call to a native method
        sample_text.text = stringFromJNI()
    }

    fun getUserInfo(user : User) : String{
        return String.format(resources.getString(R.string.user_info),
                encodeStringFromJNI(user.name), user.type, user.email, user.location, user.company,
                DateUtils.format(user.created_at!!, DateUtils.DateFormatType.YYYY_MM_DD_T_HH_MM_SS_Z))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    private external fun stringFromJNI(): String

    private external fun encodeStringFromJNI(stringNeedToEncode : String?): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
