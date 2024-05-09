package net.wandroid.quantummovie.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.wandroid.quantummovie.data.remote.OmdbApi
import net.wandroid.quantummovie.data.repositories.MovieRepositoryImpl
import net.wandroid.quantummovie.domain.repositories.MovieRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindMovieRepository(repository: MovieRepositoryImpl): MovieRepository

    companion object {
        @Singleton
        @Provides
        fun provideOmdbApi(): OmdbApi {
            val client = OkHttpClient.Builder()
                .build()

            val retrofit = Retrofit
                .Builder()
                .baseUrl(OmdbApi.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build()
                .create<OmdbApi>()
            return retrofit
        }
    }
}