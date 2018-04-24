package di

import bot.Bot
import data.repositories.CredentialsRepository
import data.repositories.StarRepository
import network.StarService
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private val module: Module = applicationContext {

    factory { Bot(get()) }

    bean { CredentialsRepository() }
    bean { StarRepository(get()) }

    bean { provideRetrofit().create(StarService::class.java) as StarService }

}

fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
                .baseUrl("https://chat.stackoverflow.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

val modules = listOf(module)
