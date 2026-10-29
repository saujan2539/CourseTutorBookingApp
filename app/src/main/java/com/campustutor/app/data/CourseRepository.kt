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
        Course(1, "Front-end Framework", "WEB301", "🎨", "Building interactive user interfaces with modern front-end frameworks."),
        Course(2, "Full Stack Application", "FS401", "🧩", "Developing complete web applications covering both front-end and back-end."),
        Course(3, "Kubernetes", "K8S310", "☸️", "Container orchestration and managing cloud deployments with Kubernetes."),
        Course(4, "Capstone", "CAP400", "🚀", "Team-based project integrating everything learned into a working product."),
        Course(5, "AI App", "AI301", "🤖", "Building Android apps powered by AI features and models."),
        Course(6, "Deep Learning Framework Advanced", "DL450", "🧠", "Hands-on work with advanced deep learning frameworks and models.")
    )


    fun findById(id: Int): Course? = courseList.find { course -> course.id == id }

    val tutorList: List<Tutor> = listOf(
        Tutor(1, 1, "Sang Hun Chun", "Front-end Framework", "Software Engineering", 180, "Front-end development and interactive UI specialist."),
        Tutor(2, 2, "Jang YongMi", "Full Stack Application", "Full Stack Development", 180, "Full stack web application expert covering client and server."),
        Tutor(3, 3, "Sang Hun Chun", "Kubernetes", "Cloud Engineering", 180, "Kubernetes and container orchestration coach."),
        Tutor(4, 4, "Hongsub An", "Capstone", "Software Engineering", 180, "Guides capstone teams to plan and build their final project."),
        Tutor(5, 5, "Seo Youn Kyoung", "AI App", "AI & Mobile Engineering", 180, "Teaches how to add AI features to Android apps."),
        Tutor(6, 6, "Hongsub An", "Deep Learning Framework Advanced", "AI Engineering", 180, "Explains advanced deep learning frameworks and models.")
    )

    fun findTutorsByCourse(courseId: Int): List<Tutor> =
        tutorList.filter { tutor -> tutor.courseId == courseId }

    fun findTutorById(id: Int): Tutor? = tutorList.find { tutor -> tutor.id == id }
}