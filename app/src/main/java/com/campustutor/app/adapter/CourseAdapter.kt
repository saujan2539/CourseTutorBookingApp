package com.campustutor.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.campustutor.app.R
import com.campustutor.app.databinding.ItemCourseBinding
import com.campustutor.app.model.Course
import kotlin.collections.List

/**
 * Adapter that binds a Course list to the item_course layout.
 */
class CourseAdapter(
    private val items: List<Course>,
    private val onItemClick: (Course) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CourseViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class CourseViewHolder(
        private val binding: ItemCourseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course) {
            binding.tvIcon.text = course.icon
            binding.tvTitle.text = course.name
            binding.tvSubtitle.text = course.code
            binding.root.setOnClickListener {
                onItemClick(course)
            }
        }
    }
}