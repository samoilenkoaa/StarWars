package com.bignerdranch.android.starwars.ui.details
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.starwars.R
import com.bignerdranch.android.starwars.databinding.DetailItemBinding
import com.bignerdranch.android.starwars.databinding.MovieItemBinding
import com.bignerdranch.android.starwars.domain.entities.Cast
import com.bignerdranch.android.starwars.domain.entities.Movie
import com.bignerdranch.android.starwars.ui.home.MovieAdapter
import com.bignerdranch.android.starwars.utils.glide.loadImage

class DetailsAdapter(
    val list: List<Cast>,
    private val onClicked: (Cast) -> Unit = {}
) : RecyclerView.Adapter<DetailsAdapter.DetailHolder>() {

    private val limit = 4

    override fun onBindViewHolder(holder: DetailHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailHolder {
        val binding = DetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailHolder(binding, onClicked)
    }

    override fun getItemCount(): Int {
        return if (list.size > limit) {
            limit
        } else {
            list.size
        }
    }

    inner class DetailHolder(
        private val binding: DetailItemBinding,
        private val onClicked: (Cast) -> Unit = {}
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(cast: Cast) {
            binding.nameTextView.text = cast.name
            if (cast.profile_path != null) {
                loadImage(itemView.context,
                    URL_ADDRESS + cast.profile_path,
                    binding.profilePathImageView)
            } else {
                loadImage(itemView.context,
                    R.drawable.ic_baseline_empty_cast_avatar_24,
                    binding.profilePathImageView)
            }

            binding.root.setOnClickListener() {
                onClicked(cast)
            }
        }
    }

    companion object {
        private const val URL_ADDRESS = "https://image.tmdb.org/t/p/original"
    }
}