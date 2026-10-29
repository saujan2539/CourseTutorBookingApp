package com.campustutor.app.ui.bookings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.campustutor.app.R
import com.campustutor.app.adapter.BookingListAdapter
import com.campustutor.app.data.CourseRepository
import com.campustutor.app.data.local.AppDatabase
import com.campustutor.app.databinding.FragmentBookingListBinding
import kotlinx.coroutines.launch
import kotlin.collections.List

/**
 * Screen that shows the bookings the user has made, loaded from Room.
 */
class BookingListFragment : Fragment(R.layout.fragment_booking_list) {

    private var _binding: FragmentBookingListBinding? = null
    private val binding: FragmentBookingListBinding
        get() = _binding!!

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentBookingListBinding.bind(view)

        binding.btnBackHomeBookings.setOnClickListener {
            findNavController().popBackStack()
        }

        val bookings: List<com.campustutor.app.data.local.BookingEntity>
        viewLifecycleOwner.lifecycleScope.launch {
            val list = AppDatabase.getDatabase(requireContext()).bookingDao().getAll()

            binding.recyclerBookings.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = BookingListAdapter(list) { booking ->
                    // Show the subject/course name for the booked tutor.
                    CourseRepository.findTutorById(booking.tutorId)?.subject
                        ?: "Course #${booking.tutorId}"
                }
            }

            binding.tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}