package edu.bu.metcs.projectportal.data.prefs

import android.content.Context
import java.util.Date

private const val ACCESSTIMEPREF = "access_time"
private const val ACCESSTIMEKEY = "last_access_time"

fun loadAccessTime(context: Context):String {
    // get the last access time from the shared preferences. The file name is accesstime.xml
    return context.getSharedPreferences(
        ACCESSTIMEPREF,
        Context.MODE_PRIVATE).getString(ACCESSTIMEKEY,"")?:""
}
// this is an example of writing data to shared preferences
fun saveAccessTime(context:Context){
    // get current time and write to the shared preferences file
    val curTime: String = Date().toString()
    context.getSharedPreferences(
        ACCESSTIMEPREF,
        Context.MODE_PRIVATE).edit()
        .putString(ACCESSTIMEKEY, curTime).apply()
}
