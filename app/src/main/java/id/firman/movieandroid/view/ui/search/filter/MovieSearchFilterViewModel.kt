package id.firman.movieandroid.view.ui.search.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import id.firman.movieandroid.models.Resource
import id.firman.movieandroid.models.entity.Movie
import id.firman.movieandroid.repository.DiscoverRepository
import id.firman.movieandroid.utils.AbsentLiveData
import javax.inject.Inject

class MovieSearchFilterViewModel @Inject constructor(
    private val discoverRepository: DiscoverRepository
) : ViewModel() {

    private var pageFiltersNumber = 1
    private var sort: String? = "popularity.desc"
    private var year: Int? = null
    private var keyword: String? = null
    private var genres: String? = null
    private var language: String? = null
    private var runtime: Int? = null
    private var region: String? = null
    private var rating: Int? = null

    private val searchMovieFilterPageLiveData: MutableLiveData<Int> = MutableLiveData()

    val searchMovieListFilterLiveData: LiveData<Resource<List<Movie>>> = Transformations
        .switchMap(searchMovieFilterPageLiveData) {
            if (it == null) {
                AbsentLiveData.create()
            } else {
                discoverRepository.loadFilteredMovies(
                    rating,
                    sort,
                    year,
                    keyword,
                    genres,
                    language,
                    runtime,
                    region,
                    it
                )
            }
        }

    fun setFilters(
        rating: Int?,
        sort: String?,
        year: Int?,
        genres: String?,
        keywords: String?,
        language: String?,
        runtime: Int?,
        region: String?,
        page: Int?
    ) {
        this.sort = sort
        this.year = year
        this.language = language
        this.keyword = keywords
        this.runtime = runtime
        this.genres = genres
        this.region = region
        this.rating = rating
        searchMovieFilterPageLiveData.value = page
    }

    fun setPage(page: Int?) {
        searchMovieFilterPageLiveData.value = page
    }

    fun loadMoreFilters() {
        pageFiltersNumber++
        searchMovieFilterPageLiveData.value = pageFiltersNumber
    }

    fun resetFilterValues() {
        this.rating = null
        this.region = null
        this.genres = null
        this.keyword = null
        this.language = null
        this.runtime = null
        this.year = null
        this.pageFiltersNumber = 1
    }

    val totalFilterResult = Transformations.switchMap(searchMovieFilterPageLiveData) {
        it?.let {
            discoverRepository.getTotalFilteredResults()
        } ?: AbsentLiveData.create()
    }

    fun refresh() {
        searchMovieFilterPageLiveData.value?.let {
            searchMovieFilterPageLiveData.value = it
        }
    }

}