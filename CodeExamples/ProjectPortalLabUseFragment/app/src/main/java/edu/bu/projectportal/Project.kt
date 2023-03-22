package edu.bu.projectportal

data class Project(val id: Int, var title: String, var description: String){
    companion object {
        val project = Project(0, "Project Portal",
            "Project portal is a simple Android application to  " +
                    "provide a centralized portal for all projects." +
                    "This app can help facilitate the information and " +
                    "sharing and collaboration among students and faculty " +
                    "across different programs within the college.")
    }
}