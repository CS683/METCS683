package edu.bu.projectportal.api


import edu.bu.projectportal.datalayer.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("users/{user}/repos")
    fun listRepos(
        @Path("user") user: String
    ) : Call<List<Repo>>
}

