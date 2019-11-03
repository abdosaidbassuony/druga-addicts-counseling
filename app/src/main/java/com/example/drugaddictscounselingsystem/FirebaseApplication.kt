package com.example.drugaddictscounselingsystem

import android.app.Application
import com.example.drugaddictscounselingsystem.ui.auth.AuthViewModelFactory
import com.example.drugaddictscounselingsystem.ui.home.HomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class FirebaseApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@FirebaseApplication))

        //    bind() from singleton(FireBaseSource())
        //  bind() from singleton { UserRepsitory(instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }

    }
}