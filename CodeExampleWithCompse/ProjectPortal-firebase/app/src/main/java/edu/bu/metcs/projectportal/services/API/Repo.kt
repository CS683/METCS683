package edu.bu.metcs.projectportal.services.API

import com.squareup.moshi.Json

data class Repo(
    @field:Json(name = "id")
    val id:Int,
    @field:Json(name = "name")
    val title: String,
    @field:Json(name = "description")
    val desc: String,
    @field:Json(name = "html_url")
    val url:String
)