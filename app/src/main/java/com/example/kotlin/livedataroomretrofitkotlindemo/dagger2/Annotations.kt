package com.example.kotlin.livedataroomretrofitkotlindemo.dagger2

import javax.inject.Qualifier
import javax.inject.Scope


/**
 * Created by tianlu on 2017/11/24.
 */
@Qualifier
annotation class GithubTokenQualifier

@Qualifier
@Retention
annotation class ActivityContext

@Qualifier
@Retention
annotation class ApplicationContext

@Scope
@Retention
annotation class PerActivity