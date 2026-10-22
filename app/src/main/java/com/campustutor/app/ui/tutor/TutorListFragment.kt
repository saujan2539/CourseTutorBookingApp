package com.campustutor.app.ui.tutor

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.campustutor.app.R
import com.campustutor.app.adapter.TutorAdapter
import com.campustutor.app.data.CourseRepository
import com.campustutor.app.databinding.FragmentTutorListBinding
import com.campustutor.app.util.NavKeys

class TutorListFragment : Fragment(R.layout.fragment_tutor_list) {

    private var _binding: FragmentTutorListBinding? = null
    private val binding: FragmentTutorListBinding
        get() = _binding!!

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentTutorListBinding.bind(view)

        val courseId = arguments?.getInt(NavKeys.COURSE_ID, -1) ?: -1
        val courseName = arguments?.getString(NavKeys.COURSE_NAME).orEmpty()

        binding.tvTitle.text = getString(
            R.string.tutor_list_title,
            courseName
        )

        val tutorAdapter = TutorAdapter(
            items = CourseRepository.findTutorsByCourse(courseId),
            onItemClick = { tutor ->
                val bundle = Bundle().apply {
                    putInt(NavKeys.TUTOR_ID, tutor.id)
                    putString(NavKeys.COURSE_NAME, courseName)
                }
                findNavController().navigate(
                    R.id.action_tutorListFragment_to_bookingFragment,
                    bundle
                )
            }
        )

        binding.recyclerTutors.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tutorAdapter
            setHasFixedSize(true)
        }

        binding.btnBackCourses.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        binding.recyclerTutors.adapter = null
        super.onDestroyView()
        _binding = null
    }
}