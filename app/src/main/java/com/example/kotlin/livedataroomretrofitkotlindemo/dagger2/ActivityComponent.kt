package com.example.kotlin.livedataroomretrofitkotlindemo.dagger2

import com.example.kotlin.livedataroomretrofitkotlindemo.MainActivity
import dagger.Component


/**
 * Created by tianlu on 2017/11/24.
 */
@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

}