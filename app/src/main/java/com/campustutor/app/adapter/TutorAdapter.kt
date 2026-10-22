package com.campustutor.app.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.campustutor.app.R
import com.campustutor.app.databinding.ItemCourseBinding
import com.campustutor.app.model.Tutor
import kotlin.collections.List

/**
 * Adapter that binds a Tutor list to the item_course layout.
 */
class TutorAdapter(
    private val items: List<Tutor>,
    private val onItemClick: (Tutor) -> Unit
) : RecyclerView.Adapter<TutorAdapter.TutorViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TutorViewHolder {
        val binding = ItemCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TutorViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TutorViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class TutorViewHolder(
        private val binding: ItemCourseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tutor: Tutor) {
            binding.tvIcon.text = "🧑‍🏫"
            binding.tvTitle.text = tutor.name
            binding.tvSubtitle.text = tutor.subject
            binding.root.setOnClickListener {
                onItemClick(tutor)
            }
        }
    }
}