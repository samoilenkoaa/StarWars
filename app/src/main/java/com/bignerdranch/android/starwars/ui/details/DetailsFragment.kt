package com.bignerdranch.android.starwars.ui.details
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.starwars.R
import com.bignerdranch.android.starwars.databinding.DetailsFragmentBinding
import com.bignerdranch.android.starwars.domain.entities.Cast
import com.bignerdranch.android.starwars.domain.entities.Movie
import com.bignerdranch.android.starwars.domain.entities.MovieDetails
import com.bignerdranch.android.starwars.ui.crew.CrewFragment
import com.bignerdranch.android.starwars.ui.home.HomeFragmentDirections
import com.bignerdranch.android.starwars.ui.home.MovieAdapter
import com.bignerdranch.android.starwars.utils.subscribe
import com.bignerdranch.android.starwars.utils.youtubeVideo.YoutubeLoader
import com.bignerdranch.android.starwars.utils.youtubeVideo.addOnCloseListener
import com.bignerdranch.android.starwars.utils.youtubeVideo.pause
import com.bignerdranch.android.starwars.utils.youtubeVideo.play
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var youtubeLoader: YoutubeLoader
    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        youtubeLoader = YoutubeLoader(lifecycle, binding.youtubePlayerView, binding.youtubeView)
        viewModel.getVideo(args.movieId)
        viewModel.getMovieDetail(args.movieId)

        subscribe(viewModel.videoLiveData) {
            youtubeLoader.loadVideo(it)
        }

        subscribe(viewModel.movieLiveData) { it ->
            setAllFields(it)
            stopPlayer()
            setAdapter(it.casts)
        }

        binding.viewAll.setOnClickListener { it ->
            val action = DetailsFragmentDirections.actionDetailsFragmentToCrewFragment(args.movieId)
            findNavController().navigate(action)
        }
    }

    private fun setAllFields(movie: MovieDetails) {
        binding.collapsingToolbar.title = movie.original_title
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.originalTitleTextView.text = movie.original_title
        binding.releaseDateTextView.text = "Release date:  ${movie.release_date}"
        if (movie.runtime != null) {
            binding.runtimeTextView.text = "Runtime: ${movie.runtime.toString()}"
        } else {
            binding.runtimeTextView.text = "--"
        }

        binding.overviewTextView.text = movie.overview
    }

    private fun setAdapter(listCasts: List<Cast>) {
        val adapter = DetailsAdapter(listCasts) {
            val action = DetailsFragmentDirections.actionDetailsFragmentToCrewFragment(it.id)
            findNavController().navigate(action)
        }
        binding.detailsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.detailsRecyclerView.adapter = adapter
    }

    private fun stopPlayer() {
        binding.appbar.addOnCloseListener(
            onClose = {
                binding.youtubePlayerView.pause()
            },
            onOpen = {
                binding.youtubePlayerView.play()
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}