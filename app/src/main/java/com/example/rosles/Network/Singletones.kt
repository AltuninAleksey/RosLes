package com.example.rosles.Network

import android.content.Context
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Const {
    /**
     * Use this URL if you launch android app on the emulator while boxes-server
     * is launched locally on 127.0.0.1:12345
     */
    const val BASE_URL = "http://10.0.2.2:8000"
}

interface SourcesProvider {
    fun getAccountsSource(): AccountsSource
}



object Singletons {

    private lateinit var appContext: Context

    private val sourcesProvider: SourcesProvider by lazy {
        SourceProviderHolder.sourcesProvider
    }

    // --- sources

    private val accountsSource: AccountsSource by lazy {
        sourcesProvider.getAccountsSource()
    }
    val accountsRepository: AccountsRepository by lazy {
        AccountsRepository(
            accountsSource = accountsSource,
        )
    }


    // --- repositories



    fun init(appContext: Context) {
        Singletons.appContext = appContext
    }
}

class RetrofitSourcesProvider(
    private val config: RetrofitConfig
) : SourcesProvider {

    override fun getAccountsSource(): AccountsSource {
        return RetrofitAccountsSource(config)
    }

}

object SourceProviderHolder {

    val sourcesProvider by lazy {
        val moshi = Moshi.Builder().build()
        val config = RetrofitConfig(
            retrofit = createRetrofit(moshi),
            moshi = moshi
        )
        RetrofitSourcesProvider(config)
    }



    /**
     * Create an instance of Retrofit client.
     */
    private fun createRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .client(createOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    /**
     * Create an instance of OkHttpClient with interceptors for authorization
     * and logging (see [createAuthorizationInterceptor] and [createLoggingInterceptor]).
     */
    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            //.addInterceptor(createAuthorizationInterceptor(Singletons.appSettings))
            .addInterceptor(createLoggingInterceptor())
            .build()
    }

    /**
     * Add Authorization header to each request if JWT-token exists.
     */


    /**
     * Log requests and responses to LogCat.
     */
    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

}
