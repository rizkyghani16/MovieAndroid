package id.firman.movieandroid.view.ui.search.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.firman.movieandroid.R
import id.firman.movieandroid.bind.FragmentDataBindingComponent
import id.firman.movieandroid.databinding.FragmentMovieSearchResultBinding
import id.firman.movieandroid.di.Injectable
import id.firman.movieandroid.extension.hideKeyboard
import id.firman.movieandroid.models.Status
import id.firman.movieandroid.utils.autoCleared
import id.firman.movieandroid.view.ui.adapter.MovieSearchListAdapter
import id.firman.movieandroid.view.ui.common.AppExecutors
import id.firman.movieandroid.view.ui.common.RetryCallback
import id.firman.movieandroid.view.ui.search.MovieSearchViewModel
import kotlinx.android.synthetic.main.fragment_movie_search_result.*
import kotlinx.android.synthetic.main.fragment_movie_search_result.view.*
import kotlinx.android.synthetic.main.toolbar_search_result.*
import javax.inject.Inject

class MovieSearchResultFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private val viewModel by viewModels<MovieSearchViewModel> { viewModelFactory }
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<FragmentMovieSearchResultBinding>()
    var adapter by autoCleared<MovieSearchListAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_search_result,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeUI()
        subscribers()

        viewModel.setSearchMovieQueryAndPage(getQuerySafeArgs(), 1)

        with(binding) {
            lifecycleOwner = this@MovieSearchResultFragment
            searchResult = viewModel.searchMovieListLiveData
            query = viewModel.queryMovieLiveData
            callback = object : RetryCallback {
                override fun retry() {
                    viewModel.refresh()
                }
            }
        }
    }

    private fun subscribers() {
        viewModel.searchMovieListLiveData.observe(viewLifecycleOwner, Observer {
            if (it.data != null && it.data.isNotEmpty()) {
                adapter.submitList(it.data)
            }
        })
    }


    private fun getQuerySafeArgs(): String? {
        val params =
            MovieSearchResultFragmentArgs.fromBundle(
                requireArguments()
            )
        return params.query
    }

    private fun initializeUI() {
        adapter = MovieSearchListAdapter(
            appExecutors,
            dataBindingComponent
        ) {
            findNavController().navigate(
                MovieSearchResultFragmentDirections.actionMovieSearchFragmentResultToMovieDetail(
                    it
                )
            )
        }

        hideKeyboard()
        binding.root.recyclerView_search_result_movies.adapter = adapter

        recyclerView_search_result_movies.layoutManager = LinearLayoutManager(context)

        recyclerView_search_result_movies.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == adapter.itemCount - 1
                    && viewModel.searchMovieListLiveData.value?.status != Status.LOADING
                ) {
                    if (viewModel.searchMovieListLiveData.value?.hasNextPage!!) {
                        viewModel.loadMore()
                    }
                }
            }
        })

        search_view.setOnSearchClickListener {
            findNavController().navigate(MovieSearchResultFragmentDirections.actionMovieSearchFragmentResultToMovieSearchFragment())
        }

        arrow_back.setOnClickListener {
            findNavController().navigate(MovieSearchResultFragmentDirections.actionMovieSearchFragmentResultToMovieSearchFragment())
        }
    }

}