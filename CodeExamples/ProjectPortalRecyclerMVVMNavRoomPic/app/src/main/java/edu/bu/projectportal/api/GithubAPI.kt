package edu.bu.projectportal.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import edu.bu.projectportal.datalayer.Project
import edu.bu.projectportal.datalayer.Repo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class GithubAPI {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private val githubApiService by lazy {
        retrofit.create(GithubService::class.java) }


    val projectsLiveData: MutableLiveData<List<Project>> = MutableLiveData()

    fun loadProjects() {
        val call = githubApiService.listRepos("yuditzh")
        call.enqueue(object : Callback<List<Repo>> {
            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                Log.e("MainActivity", "Failed to get search results", t)
            }

            override fun onResponse(
                call: Call<List<Repo>>,
                response: Response<List<Repo>>
            ) {
                if (response.isSuccessful) {
                    val repos = response.body()

                    val projects = mutableListOf<Project>()
                    repos?.forEach{repo->
                        var project = Project(repo.id,repo.title?:"", repo.desc?:"")
                       projects.add(project)
                    }
                    projectsLiveData.setValue(projects)
                } else {
                    Log.e(
                        "MainActivity",
                        "Cannot find any projects\n${response.errorBody()?.string() ?: ""}"
                    )
                }
            }
        })
    }
}
