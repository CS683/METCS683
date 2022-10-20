package edu.bu.projectportal

data class Project(val id: Int, var title: String, var description: String){
    companion object {
         // val project = Project(0, "Weather Forecast", "Weather Forcast is an app ...")
        val projects = mutableListOf(
            Project(0, "Weather Forecast", "Weather Forcast is an app ..."),
            Project(1, "Connect Me", "Connect Me is an app ... "),
            Project(2, "What to Eat", "What to Eat is an app ..."),
            Project(3, "Project Portal", "Project Portal is an app ..."),
            Project(4, "Smart Sense", "Project Portal is an app ..."))


    }
}