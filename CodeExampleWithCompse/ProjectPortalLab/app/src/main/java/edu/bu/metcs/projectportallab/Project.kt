package edu.bu.metcs.projectportallab

data class Project(var title: String, var description: String) {
    companion object {
        var project =
            Project("Weather Forecast", "Weather Forcast is an app ...")
    }
}
