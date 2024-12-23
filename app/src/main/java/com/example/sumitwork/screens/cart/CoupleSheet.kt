package com.example.sumitwork.screens.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sumitwork.R
import com.example.sumitwork.ui.theme.OrangeColor
import com.example.sumitwork.ui.theme.TextColor
import com.example.sumitwork.ui.theme.spacingMedium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoupleSheet(
    sheetState: SheetState,
    onCancelBottomSheet: () -> Unit = {}
) {

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {

        }
    ) {
        val text by remember { mutableStateOf("") }
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacingMedium)
                .padding(bottom = spacingMedium)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(spacingMedium)
        ) {

            Text(
                text = stringResource(id = R.string.couple_code),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(500),
                    color = TextColor,
                )
            )

            TextField(
                value = text,
                onValueChange = { newText ->
                                },
                label = { Text(text= stringResource(id = R.string.couple_code)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                onClick = {

                },
                border = BorderStroke(1.dp, OrangeColor)
            ) {
                Text(
                    text = stringResource(id = R.string.complete_order),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(500),
                        color = OrangeColor,
                    )
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoupleSheet() {
    CoupleSheet(
            sheetState = SheetState(
                skipPartiallyExpanded = false,
                initialValue = SheetValue.Expanded
            )
        )
}