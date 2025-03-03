package edu.bu.metcs.projectportal.services.API
import edu.bu.metcs.projectportal.data.Project
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class GithubService @Inject constructor(githubApiService: GithubAPI) {

    val projectsFlow: Flow<List<Repo>> = flow {
        emit(githubApiService.listRepos("yuditzh"))
    }
}
