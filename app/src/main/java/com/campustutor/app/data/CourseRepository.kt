package com.campustutor.app.data

import com.campustutor.app.model.Course
import com.campustutor.app.model.Tutor
import kotlin.collections.filter
import kotlin.collections.find

/**
 * Sample data repository used in place of a database.
 */
object CourseRepository {

    val courseList: List<Course> = listOf(
        Course(1, "Java Programming", "CS101", "☕", "Learn core Java: syntax, classes, and programming flow."),
        Course(2, "Web Development", "WEB201", "🌐", "Build modern websites with HTML, CSS, and JavaScript."),
        Course(3, "Android App Development", "APP301", "📱", "Create Android apps with Kotlin, Fragments, and Room."),
        Course(4, "Linux Essentials", "LIN110", "🐧", "Get comfortable with the Linux command line and shell scripts."),
        Course(5, "Database Design", "DB100", "🗄️", "Model data and write SQL with MySQL and Room SQLite.")
    )

    fun findById(id: Int): Course? = courseList.find { course -> course.id == id }

    val tutorList: List<Tutor> = listOf(
        Tutor(1, 1, "Prof. Han", "Java Programming", "Software Engineering", 3, "Specialist in Java fundamentals and object-oriented design."),
        Tutor(2, 2, "Prof. Yoon", "Web Development", "Web Engineering", 5, "Front-end and full-stack web tutor with hands-on projects."),
        Tutor(3, 3, "Prof. Kwon", "Android App Development", "Mobile Engineering", 4, "Kotlin, Jetpack Navigation, and Room database expert."),
        Tutor(4, 4, "Prof. Seo", "Linux Essentials", "Systems Engineering", 6, "Command-line and shell scripting coach for beginners."),
        Tutor(5, 5, "Prof. Moon", "Database Design", "Data Engineering", 4, "SQL, data modeling, and Room ORM tutoring.")
    )

    fun findTutorsByCourse(courseId: Int): List<Tutor> =
        tutorList.filter { tutor -> tutor.courseId == courseId }

    fun findTutorById(id: Int): Tutor? = tutorList.find { tutor -> tutor.id == id }
}