package com.omidavz.project.navigations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.omidavz.project.data.repository.DataStoreRepository
import com.omidavz.project.ui.screens.firstscreen.FirstScreen
import com.omidavz.project.ui.screens.secondscreen.SecondScreen
import com.omidavz.project.util.Constants.FIRST_SCREEN
import com.omidavz.project.util.Constants.SECOND_SCREEN

@Composable
fun MyNavigations() {
    val navController = rememberNavController()

    val mContext = navController.context
    val dataStore = DataStoreRepository(context = mContext)

    val isUserLogin = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        dataStore.getIsUserLogin.collect {
            isUserLogin.value = it ?: false
        }
    }

    NavHost(
        navController = navController,
        startDestination =
        when (isUserLogin.value) {
            false -> FIRST_SCREEN
            true -> SECOND_SCREEN
        }
    ) {
        composable(
            route = FIRST_SCREEN,
        ) {
            FirstScreen(navController = navController)
        }
        composable(route = SECOND_SCREEN) {
            SecondScreen(navController = navController)
        }
    }

}
