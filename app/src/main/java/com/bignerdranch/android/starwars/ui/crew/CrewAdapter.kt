package com.bignerdranch.android.starwars.ui.crew
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.starwars.R
import com.bignerdranch.android.starwars.databinding.DetailItemBinding
import com.bignerdranch.android.starwars.domain.entities.Cast
import com.bignerdranch.android.starwars.utils.glide.loadImage

class CrewAdapter(
    val list: List<Cast>,
) : RecyclerView.Adapter<CrewAdapter.CrewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewHolder {
        val binding = DetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CrewHolder(binding)
    }

    override fun onBindViewHolder(holder: CrewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CrewHolder(
        private val binding: DetailItemBinding,
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
        }
    }

    companion object {
        private const val URL_ADDRESS = "https://image.tmdb.org/t/p/original"
    }
}