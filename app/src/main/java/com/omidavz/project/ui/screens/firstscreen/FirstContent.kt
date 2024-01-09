package com.omidavz.project.ui.screens.firstscreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.omidavz.project.R
import com.omidavz.project.data.UserModel
import com.omidavz.project.ui.theme.ProjectTheme
import com.omidavz.project.data.repository.DataStoreRepository
import com.omidavz.project.util.Constants.FIRST_SCREEN
import com.omidavz.project.util.Constants.LARGE_DP
import com.omidavz.project.util.Constants.MEDIUM_DP
import com.omidavz.project.util.Constants.SECOND_SCREEN
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FirstContent(navController: NavController) {

    val mContext = navController.context

    val storeRepository = DataStoreRepository(mContext)


    val userName = remember {
        mutableStateOf("")
    }
    val userLastName = remember {
        mutableStateOf("")
    }
    val userBirthday = remember {
        mutableStateOf("")
    }
    val userIdNumber = remember {
        mutableStateOf("")
    }


    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        OutlinedTextField(
            value = userName.value,
            onValueChange = { if (it.length <= 20) userName.value = it },
            label = {
                Text(text = stringResource(id = R.string.name))
            },
            maxLines = 1,
        )

        Spacer(modifier = Modifier.height(MEDIUM_DP))

        OutlinedTextField(
            value = userLastName.value,
            onValueChange = { if (it.length <= 20) userLastName.value = it },
            label = {
                Text(text = stringResource(id = R.string.lastName))
            },
            maxLines = 1,
        )

        Spacer(modifier = Modifier.height(MEDIUM_DP))

        OutlinedTextField(
            value = userBirthday.value,
            onValueChange = { if (it.length <= 4 ) userBirthday.value = it },
            label = {
                Text(text = stringResource(id = R.string.date_of_birth))
            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(MEDIUM_DP))

        OutlinedTextField(
            value = userIdNumber.value,
            onValueChange = {
                if (it.length <= 11) userIdNumber.value = it

            },
            label = {
                Text(text = stringResource(id = R.string.identity_number))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = 1,
        )

        Spacer(modifier = Modifier.height(LARGE_DP))

        Button(onClick = {
            if (userName.value.isEmpty() ||
                userLastName.value.isEmpty() ||
                userBirthday.value.isEmpty() ||
                userIdNumber.value.isEmpty()
            ) {
                Toast.makeText(
                    mContext, "Please Enter All Data", Toast.LENGTH_SHORT
                )
                    .show()

            } else if (userBirthday.value.length < 4) {
                Toast.makeText(
                    mContext,
                    "Make Sure You Entered Your Birthday Correctly",
                    Toast.LENGTH_SHORT
                )
                    .show()

            } else {
                navController.navigate(SECOND_SCREEN) {
                    popUpTo(FIRST_SCREEN) { inclusive = true }

                }

                CoroutineScope(Dispatchers.IO).launch {
                    storeRepository.saveDataToDataStore(
                        UserModel(
                            userName.value,
                            userLastName.value,
                            userBirthday.value.toInt(),
                            userIdNumber.value.toLong()
                        )
                    )
                    storeRepository.setIsUserLogin(true)

                }
            }
        }) {
            Text(text = stringResource(id = R.string.register))
        }
    }
}

@Preview
@Composable
fun FirsScreenContentPreview() {
    ProjectTheme {
        FirstContent(navController = rememberNavController())
    }
}

