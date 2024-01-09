package com.omidavz.project.ui.screens.firstscreen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.omidavz.project.R
import com.omidavz.project.ui.theme.ProjectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreenAppBar() {
    TopAppBar(title = {
        Text(text = stringResource(id = R.string.appbar_signup))
    },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White
        )
    )
}

@Preview
@Composable
fun FirstScreenAppBarPreview() {
    ProjectTheme {
        FirstScreenAppBar()
    }
}