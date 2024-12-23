package com.example.sumitwork.component

import com.example.sumitwork.ui.theme.spacingMedium
import com.example.sumitwork.ui.theme.spacingSmall
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun ProgressDialog(
    message: String,
    onDismiss: () -> Unit
) {

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacingMedium),
            shape = RoundedCornerShape(spacingMedium),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = spacingSmall
        ) {
            Row(
                modifier = Modifier.padding(horizontal = spacingMedium, vertical = spacingMedium),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = message,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
            }
        }
    }
}


@Composable
fun ProgressDialogPreview() {
        var dialogOpen by remember { mutableStateOf(true) }

        ProgressDialog(
            message = "Processing...",
            onDismiss = { dialogOpen = false }
        )
}