package edu.bu.projectportal

data class Project(val id: Int, var title: String, var description: String){
    companion object {
        var project = Project(0, "Project Portal",
            "Project portal is a simple Android application to  " +
                    "provide a centralized portal for all projects." +
                    "This app can help facilitate the information and " +
                    "sharing and collaboration among students and faculty " +
                    "across different programs within the college.")
    }

//        var projects = listOf(
//            Project(0, "Weather Forecast", "Weather Forcast is an app ..."),
//            Project(1, "Connect Me", "Connect Me is an app ... "),
//            Project(2, "What to Eat", "What to Eat is an app ..."),
//            Project(3, "Project Portal", "Project Portal is an app ..."))
//    }
}