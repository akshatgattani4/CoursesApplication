package com.example.app_30_udemycoursesapp

import androidx.annotation.DrawableRes

data class Course(
    val rating : Float,
    val title : String,
    @DrawableRes
    val thumbnail : Int,
    val desc : String
)

val course1 = Course(
    4.5f,
    "Machine Learning A-Z™: AI, Python & R + ChatGPT Bonus [2023]",
    R.drawable.img,
    "Learn to create Machine Learning Algorithms in Python and R from two Data Science experts. Code templates included."
)

val course2 = Course(
    4.7f,
    "React Native: Mobile App Development (CLI) [Created in 2023]",
    R.drawable.img_1,
    "(All Levels) - Build iOS and Android Apps: Master Hooks, Redux Toolkit, Navigation, Authentication, Payment & App Stores"
)

val course3 = Course(
    4.7f,
    "Complete Web Design: from Figma to Webflow to Freelancing",
    R.drawable.img_2,
    "3 in 1 Course: Learn to design websites with Figma, build with Webflow, and make a living freelancing."
)

val course4 = Course(
    4.7f,
    "Mastering Android App Development with Kotlin [XML +COMPOSE]",
    R.drawable.img_3,
    "Build apps with XML & Jetpack Compose - Master MVVM, Retrofit, ROOM DB, Firebase, Jetpack, MySQL, JSON and Much More..."
)

val allCourses = listOf(
    course1, course2, course3, course4
)