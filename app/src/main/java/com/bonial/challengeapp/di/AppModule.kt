package com.bonial.challengeapp.di

import com.bonial.challengeapp.brochure.data.networking.RemoteBrochureDataSource
import com.bonial.challengeapp.brochure.domain.BrochureDataSource
import com.bonial.challengeapp.brochure.presentation.brochure_list.BrochureViewModel
import com.bonial.challengeapp.core.data.networking.HttpClientFactory
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteBrochureDataSource).bind<BrochureDataSource>()

    viewModelOf(::BrochureViewModel)
}