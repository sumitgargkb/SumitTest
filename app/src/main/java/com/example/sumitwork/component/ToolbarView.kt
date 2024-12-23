package com.example.sumitwork.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sumitwork.R
import com.example.sumitwork.ui.theme.TextColor
import com.example.sumitwork.ui.theme.spacingMedium
import com.example.sumitwork.ui.theme.spacingSmall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarView(
    title: String,
    isNavEnabled: Boolean = true,
    actions: List<Int>? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onBackPressed: () -> Unit = {},
    onActionItemClick: (Int) -> Unit = {},
    topBarColor: Color = MaterialTheme.colorScheme.surface,
) {


    Surface(shadowElevation = 1.dp) {
        TopAppBar(
            scrollBehavior = scrollBehavior,
            modifier = Modifier.background(MaterialTheme.colorScheme.primary),
            title = {
                Text(
                    modifier = Modifier.padding(start = spacingMedium),
                    text = title,
                    fontSize = 16.sp,
                    style = TextStyle(
                        fontSize = 24.sp,
                        lineHeight = 32.sp,
                        fontWeight = FontWeight(500),
                        color = Color.White,

                        ),
                )
            },
            navigationIcon = {
                if (isNavEnabled) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.background(Color.White, RoundedCornerShape(spacingMedium))) {
                        Icon(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .clickable(onClick = { onBackPressed() })
                                .padding(horizontal = 12.dp, vertical = 12.dp)
                        )
                        Text(
                            modifier = Modifier.padding(start = spacingSmall, end = spacingSmall),
                            text = stringResource(id = R.string.go_back),
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 21.sp,
                                fontWeight = FontWeight(400),
                                color = TextColor,

                                )
                        )
                    }

                }
            },
            actions = {
                actions?.forEach { action ->
                    Icon(
                        painter = painterResource(id = action),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .clickable(onClick = { onActionItemClick(action) })
                            .padding(horizontal = 12.dp, vertical = 12.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = topBarColor,
                scrolledContainerColor = topBarColor
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewMyScreen() {
        ToolbarView(
            title = "Test",
            actions = mutableListOf<Int>().apply {
            }

        )
}