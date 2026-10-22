package com.campustutor.app.ui.complete

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.campustutor.app.R
import com.campustutor.app.data.CourseRepository
import com.campustutor.app.databinding.FragmentCompleteBinding
import com.campustutor.app.util.NavKeys

class CompleteFragment : Fragment(R.layout.fragment_complete) {

    private var _binding: FragmentCompleteBinding? = null
    private val binding: FragmentCompleteBinding
        get() = _binding!!

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCompleteBinding.bind(view)

        val tutorId = arguments?.getInt(NavKeys.TUTOR_ID, -1) ?: -1
        val tutor = CourseRepository.findTutorById(tutorId)
        val courseName = arguments?.getString(NavKeys.COURSE_NAME).orEmpty()
        val applicantName = arguments?.getString(NavKeys.USER_NAME).orEmpty()
        val topic = arguments?.getString(NavKeys.TOPIC).orEmpty()
        val bookingDays = arguments?.getInt(NavKeys.BOOKING_DAYS, 1) ?: 1

        binding.tvCompleteTutor.text = getString(
            R.string.complete_tutor_format,
            courseName,
            tutor?.name ?: "Tutor"
        )
        binding.tvCompleteApplicant.text = getString(
            R.string.complete_applicant_format,
            applicantName
        )
        binding.tvCompleteTopic.text = getString(
            R.string.complete_topic_format,
            topic
        )
        binding.tvCompletePeriod.text = getString(
            R.string.complete_period_format,
            bookingDays
        )

        binding.btnGoCourses.setOnClickListener {
            findNavController().popBackStack(
                R.id.courseListFragment,
                false
            )
        }
        binding.btnGoHome.setOnClickListener {
            findNavController().popBackStack(
                R.id.homeFragment,
                false
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}