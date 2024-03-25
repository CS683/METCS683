package edu.bu.metcs.projectportal.data

data class Project(val id: String, var title: String, var description: String){
    companion object {
        val projects = mutableListOf(
            Project("1", "Weather Forecast", "Weather Forcast is an app ..."),
            Project("2", "Connect Me", "Connect Me is an app ... "),
            Project("3", "What to Eat", "What to Eat is an app ..."),
            Project("4", "Project Portal", "Project Portal is an app ...")
        )
    }
}