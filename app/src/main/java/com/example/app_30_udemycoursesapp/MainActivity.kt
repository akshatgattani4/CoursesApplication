@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.app_30_udemycoursesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app_30_udemycoursesapp.ui.theme.APP30_UdemyCoursesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APP30_UdemyCoursesAppTheme {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {

                        composable("home"){
                            HomeScreen(onDetailsClick = { title -> navController.navigate("details/title=$title") },
                                onAboutClick = { navController.navigate("about") }
                                )
                        }

                        composable("about"){
                            AboutScreen(onNavigateUp = { navController.popBackStack() })
                        }

                        composable("details/title={title}",
                            arguments = listOf(navArgument("title"){
                                type = NavType.StringType
                                nullable = true})
                        ){
                            backStackEntry -> val arguments = requireNotNull(backStackEntry.arguments)

                            val id = arguments.getString("title")

                            if (id != null) {
                                DetailsScreen(title = id, onNavigateUp = {navController.popBackStack()})
                            }
                        }
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onDetailsClick: (title: String) -> Unit,
    onAboutClick: () -> Unit
) {
    Scaffold { padding ->
        LazyColumn(contentPadding = padding) {

            item {
                HomeAppBar(onAboutClick)
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }

            items(allCourses) { item ->
                CourseCard(
                    item,
                    onClick = { onDetailsClick(item.title) }
                )
            }
        }
    }
}

@Composable
fun CourseCard(item: Course, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Column() {
            Image(
                painter = painterResource(id = item.thumbnail),
                contentDescription = "Thumbnail",
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(16f / 9f),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(text = item.title)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = item.desc, maxLines = 1, style = MaterialTheme.typography.bodySmall)
            }

        }
    }
}

@Composable
fun HomeAppBar(onAboutClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(text = "Udemy Courses List", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.weight(1f))
        TextButton(onClick = onAboutClick ){
            Text(text = "About", fontSize = 24.sp)
        }
    }
}

@Composable
fun AboutScreen(onNavigateUp: () -> Unit) {
    Scaffold { padding ->
        Column(Modifier.padding(padding)) {
            AppBar(title = "About", onNavigateUp)
            Spacer(modifier = Modifier.height(20.dp))
            Column(Modifier.padding(16.dp)) {
                Text(text = "This app is a demonstration about about navigation using jetpack compose.")
                Spacer(modifier = Modifier.height(20.dp))

                val udemyLink = LocalUriHandler.current
                Button(onClick = { udemyLink.openUri("https://www.udemy.com/home/my-courses/wishlist/") }) {
                    Text(text = "Go To Official Website")
                }
            }
        }
    }
}

@Composable
fun AppBar(title: String, onNavigateUp: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 10.dp)
    ) {
        IconButton(onClick = onNavigateUp) {
            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back")
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = title, fontSize = 24.sp)
    }
}

@Composable
fun DetailsScreen(title: String, name: String? = null, onNavigateUp: () -> Unit) {
    val course = allCourses.first { it.title == title }

    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                IconButton(onClick = onNavigateUp) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back")
                }
            }

            Image(
                painter = painterResource(id = course.thumbnail),
                contentDescription = "Thumbnail",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = course.title, fontSize = 36.sp, lineHeight = 40.sp)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = course.desc,
                    modifier = Modifier.fillMaxSize(), fontSize = 24.sp
                )
            }
        }

    }
}
