package com.campustutor.app.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.campustutor.app.R
import com.campustutor.app.adapter.CourseAdapter
import com.campustutor.app.data.CourseRepository
import com.campustutor.app.databinding.FragmentCourseListBinding
import com.campustutor.app.util.NavKeys

class CourseListFragment : Fragment(R.layout.fragment_course_list) {

    private var _binding: FragmentCourseListBinding? = null
    private val binding: FragmentCourseListBinding
        get() = _binding!!

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCourseListBinding.bind(view)

        val courseAdapter = CourseAdapter(
            items = CourseRepository.courseList,
            onItemClick = { course ->
                val bundle = Bundle().apply {
                    putInt(NavKeys.COURSE_ID, course.id)
                    putString(NavKeys.COURSE_NAME, course.name)
                }
                findNavController().navigate(
                    R.id.action_courseListFragment_to_tutorListFragment,
                    bundle
                )
            }
        )

        binding.recyclerCourses.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = courseAdapter
            setHasFixedSize(true)
        }

        binding.btnBackHome.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        binding.recyclerCourses.adapter = null
        super.onDestroyView()
        _binding = null
    }
}