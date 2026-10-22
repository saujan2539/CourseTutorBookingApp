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
import com.campustutor.app.databinding.FragmentBookingBinding
import com.campustutor.app.util.NavKeys
import kotlinx.coroutines.launch

class BookingFragment : Fragment(R.layout.fragment_booking) {

    private var _binding: FragmentBookingBinding? = null
    private val binding: FragmentBookingBinding
        get() = _binding!!

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
            tutor.maxBookingDays
        )

        val dayItems = (1..tutor.maxBookingDays).map { day ->
            getString(R.string.day_format, day)
        }
        binding.spinnerDays.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            dayItems
        )

        binding.btnSubmitBooking.setOnClickListener {
            val applicantName = binding.etApplicantName.text.toString().trim()
            val topic = binding.etTopic.text.toString().trim()

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

            val bookingDays = binding.spinnerDays.selectedItemPosition + 1

            val bundle = Bundle().apply {
                putInt(NavKeys.TUTOR_ID, tutor.id)
                putString(NavKeys.COURSE_NAME, courseName)
                putString(NavKeys.USER_NAME, applicantName)
                putString(NavKeys.TOPIC, topic)
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