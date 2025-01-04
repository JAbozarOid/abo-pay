package com.jabozaroid.abopay.feature.first.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jabozaroid.abopay.core.designsystem.component.AppBackground
import com.jabozaroid.abopay.core.designsystem.component.AppButton
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.ui.navigation.ApplicationRoutes
import com.jabozaroid.abopay.core.ui.view.BaseScreen
import com.jabozaroid.abopay.feature.first.model.FirstAction
import com.jabozaroid.abopay.feature.first.model.FirstEvent
import com.jabozaroid.abopay.feature.first.model.FirstUiModel
import com.jabozaroid.abopay.feature.first.viewmodel.FirstViewModel


class FirstScreen : BaseScreen<FirstUiModel, FirstAction, FirstEvent>(
    route = ApplicationRoutes.firstScreenRoute, name = "first"
) {

    @Composable
    override fun viewModel(): FirstViewModel = hiltViewModel()

    @Composable
    override fun Content(state: FirstUiModel) {

        val viewModel = viewModel()

        FirstContent(
            isBtnEnable = state.isBtnEnable(),
            loading = state.loading,
            viewModel = viewModel
        )


    }
}

@Composable
private fun FirstContent(
    isBtnEnable: Boolean,
    loading: Boolean,
    viewModel: FirstViewModel

) {
    var text by remember {
        mutableStateOf("")
    }


    Column(
        modifier = Modifier
            .padding(Dimens.size_8)
            .fillMaxSize()
            .padding(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                color = Color.DarkGray,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        )

        AppButton(
            enabled = isBtnEnable,
            isLoading = loading,
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Submit", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        }
    }

}

@Preview(showBackground = false)
@Composable
fun PreviewFirstScreen() {
    AppTheme {
        AppBackground(modifier = Modifier) {

        }
        FirstContent(isBtnEnable = true, loading = false, viewModel = FirstViewModel())
    }
}