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
import com.example.kotlin.livedataroomretrofitkotlindemo.network.Resource.Status
import com.example.kotlin.livedataroomretrofitkotlindemo.network.User
import com.example.kotlin.livedataroomretrofitkotlindemo.network.githubconfig.GithubService
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

        //Example of a call to a native method
        text_userinfo.text = stringFromJNI()

        //Bind the controls
        initControls()
    }

    fun initControls(){

        var viewModel = ViewModelProviders.of(this, viewModelFactory).get(classOf<UserViewModel>())

        //Get the public information of the user, null if none in repository injection mode
        btnGetUserRepo.setOnClickListener{ _ ->
            progressbar.visibility = View.VISIBLE
            viewModel.initUser(edittext_name.text.toString())
            viewModel.getUser()?.observe(this, Observer{
                when(it?.status){
                    Status.SUCCESS -> {
                        progressbar.visibility = View.GONE
                        text_userinfo.text = getUserInfo(it.data!!)
                    }
                    Status.ERROR -> {
                        progressbar.visibility = View.GONE
                        text_userinfo.text = it.message
                    }
                    Status.LOADING -> {}
                }
            })
        }

        btnGetSubs.setOnClickListener{_ ->
            progressbar.visibility = View.VISIBLE
            viewModel.initSubscriptions(edittext_subname.text.toString())
            viewModel.getSubscriptions()?.observe(this, Observer{
                when(it?.status){
                    Status.SUCCESS -> {
                        var text = ""
                        it.data!!.forEach {
                            text += "${it.name}(${it.owner?.login})=${it.url}\n"
                        }

                        progressbar.visibility = View.GONE
                        text_subscriptions.text = text
                    }
                    Status.ERROR -> {
                        progressbar.visibility = View.GONE
                        text_subscriptions.text = it.message
                    }
                    Status.LOADING -> {}
                }
            })
        }

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
                    text_userinfo.text = getUserInfo(it.body!!)
                }else{
                    text_userinfo.text = it.errorMessage
                }
            })
        }

        //Update the current user's email
        btnUpdateUser.setOnClickListener{_ ->
            progressbar.visibility = View.VISIBLE

            var githubService = Service.createGithubService(resources.getString(R.string.github_access_token))
            githubService!!.getUser(resources.getString(R.string.github_access_name)).observe(this, Observer{

                if(it!!.isSuccessful){
                    var user = it.body
                    if(user != null){
                        user.email = edittext_email.text.toString()
                        githubService.updateUser(user).observe(this, Observer {
                            progressbar.visibility = View.GONE
                            if(it!!.isSuccessful){
                                text_userinfo.text = getUserInfo(it.body!!)
                            }else{
                                text_userinfo.text = it.errorMessage
                            }
                        })
                    }else{
                        progressbar.visibility = View.GONE
                    }
                }else{
                    progressbar.visibility = View.GONE
                    text_userinfo.text = it.errorMessage
                }
            })
        }

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
