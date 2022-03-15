package edu.bu.projectportal

data class Project(val id: Int, var title: String, var description: String){
    companion object {
        var project = Project(0, "Weather Forecast", "Weather Forcast is an app ...")
    }

//        var projects = listOf(
//            Project(0, "Weather Forecast", "Weather Forcast is an app ..."),
//            Project(1, "Connect Me", "Connect Me is an app ... "),
//            Project(2, "What to Eat", "What to Eat is an app ..."),
//            Project(3, "Project Portal", "Project Portal is an app ..."))
//    }
}