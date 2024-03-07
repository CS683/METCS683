package com.example.widgetsexplore

data class Profile(var name:String = "",
                   var country: String = "",
                   var male:Boolean = true,
                   var adult:Boolean = true,
                   var isPublic:Boolean = true,
                   var comments: String = "I love programming")
