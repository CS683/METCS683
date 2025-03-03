package edu.bu.metcs.projectportal.services.API

import retrofit2.http.GET
import retrofit2.http.Path

interface GithubAPI {
    @GET("users/{user}/repos")
    suspend fun listRepos(
        @Path("user") user: String
    ): List<Repo>
}
