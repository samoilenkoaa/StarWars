package com.bignerdranch.android.starwars.ui.home
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.starwars.R
import com.bignerdranch.android.starwars.databinding.HomeFragmentBinding
import com.bignerdranch.android.starwars.domain.entities.Movie
import com.bignerdranch.android.starwars.utils.SpacesItemDecoration
import com.bignerdranch.android.starwars.utils.subscribe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refreshCinema()

        subscribe(viewModel.listPopularMoviesLiveData) {
            setAdapter(it)
        }

        binding.recyclerView.addItemDecoration(
            SpacesItemDecoration(
                resources.getDimensionPixelSize(R.dimen.test),
                2
            )
        )

        subscribe(viewModel.isLoadingLiveData) {
            if (it) showProgressBar()
            else hideProgressBar()
        }
    }

    private fun setAdapter(listMovie: List<Movie>) {
        val adapter = MovieAdapter(listMovie) {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it.id)
            findNavController().navigate(action)
        }

        binding.recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}