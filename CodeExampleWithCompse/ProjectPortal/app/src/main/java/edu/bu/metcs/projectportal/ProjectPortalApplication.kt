package edu.bu.metcs.projectportal

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// need the annotation @HiltAndroid to use Hilt
// Also need to add the application name in the manifest file
@HiltAndroidApp
class ProjectPortalApplication: Application()
//{
//    override fun onCreate() {
//        super.onCreate()
//       // if (BuildConfig.DEBUG) Timber.plant(DebugTree())
//    }
//}