package com.omidavz.project.ui.screens.secondscreen

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.omidavz.project.ui.theme.ProjectTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SecondScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            SecondAppBar()

        },
        content = {
            SecondContent(
                navController = navController
            )
        }
    )
}

@Preview
@Composable
fun SecondScreenPreview() {

    ProjectTheme {
        SecondContent(rememberNavController())
    }


}