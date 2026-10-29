package com.campustutor.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.campustutor.app.data.local.BookingEntity
import com.campustutor.app.databinding.ItemCourseBinding
import kotlin.collections.List

/**
 * Adapter that binds saved bookings to the item_course layout.
 * The titleOf lambda returns the display title for each booking.
 */
class BookingListAdapter(
    private val items: List<BookingEntity>,
    private val titleOf: (BookingEntity) -> String
) : RecyclerView.Adapter<BookingListAdapter.BookingViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookingViewHolder {
        val binding = ItemCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookingViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BookingViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class BookingViewHolder(
        private val binding: ItemCourseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(booking: BookingEntity) {
            binding.tvIcon.text = "📅"
            binding.tvTitle.text = titleOf(booking)
            binding.tvSubtitle.text =
                "${booking.studentName} · ${booking.topic} · ${booking.bookingDate} at ${booking.bookingTime} · ${formatMinutes(booking.bookingDays)} · ${booking.status}"
            binding.root.isClickable = false
        }

        /** Formats a duration in minutes as a friendly label, e.g. 150 -> "2 hr 30 min". */
        private fun formatMinutes(totalMinutes: Int): String {
            val hours = totalMinutes / 60
            val mins = totalMinutes % 60
            return when {
                hours == 0 -> "$mins min"
                mins == 0 -> "$hours hr"
                else -> "$hours hr $mins min"
            }
        }
    }
}