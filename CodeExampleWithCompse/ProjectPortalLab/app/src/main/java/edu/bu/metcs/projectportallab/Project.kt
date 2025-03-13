package edu.bu.metcs.projectportallab

data class Project(var title: String, var description: String) {
    companion object {
        val project =
            Project("Weather Forecast", "Weather Forcast is an app ...")
    }
}
