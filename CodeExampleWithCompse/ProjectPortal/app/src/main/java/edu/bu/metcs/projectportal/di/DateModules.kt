package edu.bu.metcs.projectportal.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.bu.metcs.projectportal.data.LocalProjectRepository
import edu.bu.metcs.projectportal.data.ProjectRepository
import edu.bu.metcs.projectportal.data.db.ProjectDao
import edu.bu.metcs.projectportal.data.db.ProjectPortalDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindProjectRepository(repository: LocalProjectRepository): ProjectRepository
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
