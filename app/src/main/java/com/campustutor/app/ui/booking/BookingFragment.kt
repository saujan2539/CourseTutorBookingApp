package com.campustutor.app.ui.booking

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.lifecycleScope
import com.campustutor.app.R
import com.campustutor.app.data.CourseRepository
import com.campustutor.app.data.local.AppDatabase
import com.campustutor.app.data.local.BookingEntity
import com.campustutor.app.databinding.FragmentBookingBinding
import com.campustutor.app.util.NavKeys
import kotlinx.coroutines.launch

class BookingFragment : Fragment(R.layout.fragment_booking) {

    private var _binding: FragmentBookingBinding? = null
    private val binding: FragmentBookingBinding
        get() = _binding!!

    /**
     * Parses a yyyy-MM-dd date and returns true if it is before today.
     */
    private fun isDateInPast(date: String): Boolean {
        return try {
            val parts = date.split("-")
            val y = parts[0].toInt()
            val m = parts[1].toInt()
            val d = parts[2].toInt()
            val now = java.time.LocalDate.now()
            val chosen = java.time.LocalDate.of(y, m, d)
            chosen.isBefore(now)
        } catch (e: Exception) {
            // If the date can't be parsed, treat it as invalid (reject).
            true
        }
    }

    /**
     * Formats a duration in minutes as a friendly label,
     * e.g. 30 -> "30 min", 60 -> "1 hr", 90 -> "1 hr 30 min", 180 -> "3 hr".
     */
    private fun formatMinutes(totalMinutes: Int): String {
        val hours = totalMinutes / 60
        val mins = totalMinutes % 60
        return when {
            hours == 0 -> "$mins min"
            mins == 0 -> "$hours hr"
            else -> "$hours hr $mins min"
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentBookingBinding.bind(view)

        val tutorId = arguments?.getInt(NavKeys.TUTOR_ID, -1) ?: -1
        val tutor = CourseRepository.findTutorById(tutorId)

        if (tutor == null) {
            Toast.makeText(
                requireContext(),
                getString(R.string.tutor_not_found),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().popBackStack()
            return
        }

        val courseName = arguments?.getString(NavKeys.COURSE_NAME).orEmpty()
        binding.tvSelectedTutor.text = getString(
            R.string.selected_tutor_format,
            courseName,
            tutor.name,
            tutor.subject
        )
        binding.tvBookingRule.text = getString(
            R.string.booking_rule_format,
            tutor.maxBookingMinutes
        )

        // Duration options from 30 minutes up to a max of 3 hours (180 min),
        // in 30-minute steps: 30, 60, 90, 120, 150, 180.
        val step = 30
        val maxMinutes = tutor.maxBookingMinutes
        val durationLabels = mutableListOf<String>()
        var minutes = step
        while (minutes <= maxMinutes) {
            durationLabels.add(formatMinutes(minutes))
            minutes += step
        }
        binding.spinnerDays.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            durationLabels
        )

        binding.btnSubmitBooking.setOnClickListener {
            val applicantName = binding.etApplicantName.text.toString().trim()
            val topic = binding.etTopic.text.toString().trim()
            val bookingDate = binding.etBookingDate.text.toString().trim()
            val bookingTime = binding.etBookingTime.text.toString().trim()

            if (applicantName.isEmpty()) {
                binding.etApplicantName.error = getString(R.string.enter_name)
                binding.etApplicantName.requestFocus()
                return@setOnClickListener
            }
            if (topic.isEmpty()) {
                binding.etTopic.error = getString(R.string.enter_topic)
                binding.etTopic.requestFocus()
                return@setOnClickListener
            }
            if (bookingDate.isEmpty()) {
                binding.etBookingDate.error = getString(R.string.enter_date)
                binding.etBookingDate.requestFocus()
                return@setOnClickListener
            }
            // Reject a date that is in the past.
            if (isDateInPast(bookingDate)) {
                androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Invalid date")
                    .setMessage("You can't book for a past date.")
                    .setPositiveButton("OK", null)
                    .show()
                return@setOnClickListener
            }
            if (bookingTime.isEmpty()) {
                binding.etBookingTime.error = getString(R.string.enter_time)
                binding.etBookingTime.requestFocus()
                return@setOnClickListener
            }

            val bookingDays = (binding.spinnerDays.selectedItemPosition + 1) * 30

            // Save the booking to the Room database so it shows under "My Bookings".
            val bookingEntity = BookingEntity(
                tutorId = tutor.id,
                studentName = applicantName,
                topic = topic,
                bookingDate = bookingDate,
                bookingTime = bookingTime,
                bookingDays = bookingDays,
                status = "Booked"
            )
            viewLifecycleOwner.lifecycleScope.launch {
                AppDatabase.getDatabase(requireContext()).bookingDao().insert(bookingEntity)
            }

            val bundle = Bundle().apply {
                putInt(NavKeys.TUTOR_ID, tutor.id)
                putString(NavKeys.COURSE_NAME, courseName)
                putString(NavKeys.USER_NAME, applicantName)
                putString(NavKeys.TOPIC, topic)
                putString(NavKeys.BOOKING_DATE, bookingDate)
                putString(NavKeys.BOOKING_TIME, bookingTime)
                putInt(NavKeys.BOOKING_DAYS, bookingDays)
            }

            findNavController().navigate(
                R.id.action_bookingFragment_to_completeFragment,
                bundle
            )
        }

        binding.btnBackTutors.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}