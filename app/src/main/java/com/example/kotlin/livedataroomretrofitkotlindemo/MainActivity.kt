package com.example.kotlin.livedataroomretrofitkotlindemo

import android.arch.lifecycle.Observer
import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.kotlin.livedataroomretrofitkotlindemo.githubconfig.GithubService
import com.example.kotlin.livedataroomretrofitkotlindemo.repo.GithubRepository

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

//Make an alias of type
typealias Service = GithubService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Get the all contributiors' contributions count
        btnGetContributors.setOnClickListener { view ->

            progressbar.visibility = View.VISIBLE
            var githubService = Service.createGithubServiceAsLiveData(resources.getString(R.string.github_access_token))

            githubService!!.getContributors("square", "retrofit").observe(this, Observer{
                progressbar.visibility = View.GONE
                if(it!!.isSuccessful)
                    for(contributor in it!!.body!!){
                        println(contributor.login + "=" + contributor.contributions)
                    }
            })
        }

        //Get the public email of the user, null if none
        btnGetUser.setOnClickListener { view ->

            progressbar.visibility = View.VISIBLE
            var githubService = Service.createGithubServiceAsLiveData(resources.getString(R.string.github_access_token))

            githubService!!.getUser(sample_edittext.text.toString()).observe(this, Observer{
                progressbar.visibility = View.GONE
                if(it!!.isSuccessful){
                    //Add the name with 'hello '
                    sample_text.text = encodeStringFromJNI(it?.body!!.name) + " = ${it?.body!!.email}"
                }else{
                    sample_text.text = it!!.errorMessage
                }
                println(sample_text.text)
            })
        }

        //Get the public email of the user, null if none
        var repo = GithubRepository(resources.getString(R.string.github_access_token), MainApplication.database.userDao(), Executors.newFixedThreadPool(5))

        btnGetUserRepo.setOnClickListener{ view ->

            progressbar.visibility = View.VISIBLE
            repo.getUser(sample_edittext.text.toString())!!.observe(this, Observer{
                progressbar.visibility = View.GONE
                sample_text.text = "${encodeStringFromJNI(it?.name)} = ${it?.email}"
            })

        }

        // Example of a call to a native method
        sample_text.text = stringFromJNI()
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
    external fun stringFromJNI(): String

    external fun encodeStringFromJNI(stringNeedToEncode : String?): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
