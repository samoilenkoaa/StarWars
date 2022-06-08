package com.bignerdranch.android.starwars.ui.crew
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.starwars.databinding.FragmentCrewBinding
import com.bignerdranch.android.starwars.domain.entities.Cast
import com.bignerdranch.android.starwars.ui.details.DetailsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CrewFragment: Fragment() {

    private var _binding: FragmentCrewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CrewViewModel by viewModels()
    private val args: CrewFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCrewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMovieDetail(args.movieId)

        viewModel.movieLiveData.observe(viewLifecycleOwner) {
            setAdapter(it.casts)
        }
    }

    private fun setAdapter(listCasts: List<Cast>) {
        val adapter = CrewAdapter(listCasts)
        binding.crewRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.crewRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}