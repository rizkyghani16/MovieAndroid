package id.firman.movieandroid.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.firman.movieandroid.factory.AppViewModelFactory
import id.firman.movieandroid.view.ui.movie.MovieDetailViewModel
import id.firman.movieandroid.view.ui.movie.MovieListViewModel
import id.firman.movieandroid.view.ui.search.MovieSearchViewModel
import id.firman.movieandroid.view.ui.search.TvSearchViewModel
import id.firman.movieandroid.view.ui.search.filter.MovieSearchFilterViewModel
import id.firman.movieandroid.view.ui.search.filter.TvSearchFilterViewModel
import id.firman.movieandroid.view.ui.tv.tvdetail.TvDetailViewModel
import id.firman.movieandroid.view.ui.tv.tvlist.TvListViewModel

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel::class)
    abstract fun bindMovieListFragmentViewModel(movieListViewModel: MovieListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun bindMovieDetailViewModel(movieDetailViewModel: MovieDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TvDetailViewModel::class)
    abstract fun bindTvDetailViewModel(tvDetailViewModel: TvDetailViewModel): ViewModel



    @Binds
    @IntoMap
    @ViewModelKey(TvListViewModel::class)
    abstract fun bindTvListViewModel(tvListViewModel: TvListViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(MovieSearchViewModel::class)
    abstract fun bindMovieSearchViewModel(movieSearchViewModel: MovieSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TvSearchViewModel::class)
    abstract fun bindTvSearchViewModel(tvSearchViewModel: TvSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieSearchFilterViewModel::class)
    abstract fun bindMovieSearchFilterViewModel(movieSearchFilterViewModel: MovieSearchFilterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TvSearchFilterViewModel::class)
    abstract fun bindTvSearchFilterViewModel(tvSearchFilterViewModel: TvSearchFilterViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}