package com.sd.demo.compose.inputgroup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sd.demo.compose.inputgroup.theme.AppTheme
import com.sd.lib.compose.inputgroup.FInputGroup
import com.sd.lib.compose.inputgroup.rememberInputGroupState

class SampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                ContentView()
            }
        }
    }
}

@Composable
private fun ContentView(
    modifier: Modifier = Modifier,
) {
    val inputGroupState = rememberInputGroupState(count = 4)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FInputGroup(
            state = inputGroupState,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
            )
        )
    }
}
