package com.omidavz.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.omidavz.project.navigations.MyNavigations
import com.omidavz.project.ui.theme.ProjectTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectTheme {
                MyNavigations()
            }
        }
    }



}



