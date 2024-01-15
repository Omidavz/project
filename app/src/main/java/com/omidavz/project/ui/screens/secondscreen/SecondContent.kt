package com.omidavz.project.ui.screens.secondscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.omidavz.project.R
import com.omidavz.project.ui.theme.ProjectTheme
import com.omidavz.project.data.repository.DataStoreRepository
import com.omidavz.project.util.Constants.FIRST_SCREEN
import com.omidavz.project.util.Constants.LARGE_DP
import com.omidavz.project.util.Constants.MEDIUM_DP
import com.omidavz.project.util.Constants.SECOND_SCREEN
import com.omidavz.project.util.Constants.SMALL_DP
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SecondContent(
    navController: NavController,

) {

    val mContext = navController.context
    val storeRepository = DataStoreRepository(mContext)

    val name = remember {
        mutableStateOf("")
    }

    val lastName = remember {
        mutableStateOf("")
    }

    val dateOfBirth = remember {
        mutableStateOf("")
    }
    val identityNumber= remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = true) {
        storeRepository.readFromDataStore().collect {
            name.value = it.name
            lastName.value = it.lastName
            dateOfBirth.value = it.dateOfBirth.toString()
            identityNumber.value = it.identityNumber.toString()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextsRow(textA = "Name : ", textB =name.value)
        TextsRow(textA = "Last Name : ", textB = lastName.value)
        TextsRow(textA = "Date Of Birth : ", textB = dateOfBirth.value)
        TextsRow(textA = "Identity Number : ", textB = identityNumber.value)

        Button(onClick = {
            navController.navigate(FIRST_SCREEN){
                popUpTo(SECOND_SCREEN){inclusive = true}
            }

            CoroutineScope(Dispatchers.IO).launch {
                storeRepository.deleteDataFromDataStore()
                storeRepository.setIsUserLogin(false)
            }

        }) {
            Text(text = stringResource(id = R.string.sign_out))
        }
    }

}


@Composable
fun TextsRow(textA: String, textB: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = SMALL_DP, end = SMALL_DP)
            .border(1.dp, color = Color.Black, shape = CutCornerShape(SMALL_DP))
            .height(LARGE_DP)
            .background(color = Color.Transparent, shape = CutCornerShape(SMALL_DP)),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = textA,
            modifier = Modifier.fillMaxWidth(0.5f)
        )
        Text(
            text = textB,
            modifier = Modifier.fillMaxWidth(0.5f),
            maxLines = 1,

        )
    }
    Spacer(modifier = Modifier.height(MEDIUM_DP))
}

@Preview
@Composable
fun SecondContentPreview() {
    ProjectTheme {
        SecondContent(rememberNavController())
    }

}