package com.omidavz.project.ui.screens.firstscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import com.omidavz.project.util.convertMillisToDate
import com.omidavz.project.util.dataValidator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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

    val showDataPickerDialog = remember {
        mutableStateOf(false)
    }

    val validateDate = dataValidator(
        userName.value,
        userLastName.value,
        userBirthday.value,
        userIdNumber.value
    )

    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker
    )

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
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
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(MEDIUM_DP))

        OutlinedTextField(
            value = userLastName.value,
            onValueChange = { if (it.length <= 20) userLastName.value = it },
            label = {
                Text(text = stringResource(id = R.string.lastName))
            },
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(MEDIUM_DP))

        OutlinedTextField(
            value = userBirthday.value,
            onValueChange = { },

            placeholder = {
                Text(text = stringResource(id = R.string.date_of_birth))
            },
            trailingIcon = {
                IconButton(modifier = Modifier,
                    onClick = {
                        showDataPickerDialog.value = true
                    }) {
                    Icon(
                        Icons.Filled.DateRange,
                        contentDescription = ""
                    )

                }
            },
            singleLine = true,
            readOnly = true,
        )

        Spacer(modifier = Modifier.height(MEDIUM_DP))

        OutlinedTextField(
            value = userIdNumber.value,
            onValueChange = {
                if (it.length <= 10) userIdNumber.value = it

            },
            label = {
                Text(text = stringResource(id = R.string.identity_number))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(LARGE_DP))

        Button(
            enabled = validateDate,
            onClick = {
                    navController.navigate(SECOND_SCREEN) {
                        popUpTo(FIRST_SCREEN) { inclusive = true }


                    CoroutineScope(Dispatchers.IO).launch {
                        storeRepository.saveDataToDataStore(
                            UserModel(
                                userName.value,
                                userLastName.value,
                                userBirthday.value,
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

    if (showDataPickerDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDataPickerDialog.value = false
            },
            title = {
                DatePicker(state = datePickerState)
            },

            confirmButton = {
                TextButton(onClick = {
                    userBirthday.value = selectedDate.toString()
                    showDataPickerDialog.value = false
                }) {
                    Text(text = stringResource(id = R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDataPickerDialog.value = false }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            }
        )
    }
}


@Preview
@Composable
fun FirsScreenContentPreview() {
    ProjectTheme {
        FirstContent(navController = rememberNavController())
    }
}

