package com.quantam.it.assignment

import android.app.Application
import com.quantam.it.assignment.repository.AuthRepository
import com.quantam.it.assignment.ui.auth.viewModel.AuthVMF
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class Application : Application(), KodeinAware {
    override val kodein= Kodein.lazy {
        import(androidXModule(this@Application))

        bind() from singleton { AuthRepository(instance()) }
        bind() from provider { AuthVMF(instance()) }

    }
}