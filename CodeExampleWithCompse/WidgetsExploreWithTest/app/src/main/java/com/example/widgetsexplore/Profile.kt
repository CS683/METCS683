package com.example.widgetsexplore

data class Profile(var name:String = "",
                   var country: String = "",
                   var gender: String = "",
                   var adult:Boolean = true,
                   var isPublic:Boolean = true,
                   var comments: String = "I love programming",
                   var keywords: List<String> = listOf()

){
    companion object {
        val profile = Profile()
    }
}
