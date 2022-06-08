package com.bignerdranch.android.starwars.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.starwars.R
import com.bignerdranch.android.starwars.databinding.MovieItemBinding
import com.bignerdranch.android.starwars.domain.entities.Movie
import com.bignerdranch.android.starwars.utils.glide.loadImage

class MovieAdapter(
    private val dataCinema: List<Movie>,
    private val onClicked: (Movie) -> Unit = {}
) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(dataCinema[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding, onClicked)
    }

    override fun getItemCount(): Int {
        return dataCinema.size
    }

    inner class MovieHolder(
        private val binding: MovieItemBinding,
        private val onClicked: (Movie) -> Unit = {}
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Movie) {
            binding.textViewTitle.text = model.title
            binding.textViewVoteAverage.text = model.vote_average.toString()
            loadImage(
                itemView.context,
                URL_ADDRESS + model.poster_path,
                R.color.primaryColor,
                binding.imageViewPoster
            )

            binding.imageViewPoster.setOnClickListener() {
                onClicked(model)
            }
        }
    }

    companion object {
        private const val URL_ADDRESS = "https://image.tmdb.org/t/p/original"
    }
}
