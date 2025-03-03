package edu.bu.metcs.projectportal.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.bu.metcs.projectportal.data.FirebaseProjectRepository
import edu.bu.metcs.projectportal.data.LocalProjectRepository
import edu.bu.metcs.projectportal.data.ProjectRepository
import edu.bu.metcs.projectportal.data.db.ProjectDao
import edu.bu.metcs.projectportal.data.db.ProjectPortalDatabase
import edu.bu.metcs.projectportal.services.API.GithubAPI
import edu.bu.metcs.projectportal.services.API.GithubService
import edu.bu.metcs.projectportal.services.auth.AuthService
import edu.bu.metcs.projectportal.services.auth.AuthServiceFirebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
   // abstract fun bindProjectRepository(repository: LocalProjectRepository): ProjectRepository
    abstract fun bindProjectRepository(repository: FirebaseProjectRepository): ProjectRepository

}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): ProjectPortalDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ProjectPortalDatabase::class.java,
            "Projects-db"
        ).build()
    }

    @Provides
    fun provideProjectDao(database: ProjectPortalDatabase): ProjectDao = database.projectDao()
}

private const val USER_PREFERENCES = "user_preferences"

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext,USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds abstract fun provideAuthService(impl: AuthServiceFirebase): AuthService
}

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides fun provideAuth(): FirebaseAuth = Firebase.auth

    @Provides fun provideFirestore(): FirebaseFirestore = Firebase.firestore

//    @Provides
//    fun provideProjectRepository(firestore: FirebaseFirestore): ProjectRepository {
//        return FirebaseProjectRepository(firestore)
//    }
}

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideGithubAPI(): GithubAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        return retrofit.create(GithubAPI::class.java)
    }
}

