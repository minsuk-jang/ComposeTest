package com.example.composetest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.composetest.ui.theme.ComposeTestTheme

const val TAG = "jms"


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()

                    //Component(title = "제목")
                }
            }
        }
    }
}


@Composable
private fun MainScreen() {
    Log.e(TAG, "Composition")
    val list = remember {
        listOf("A", "B", "C")
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(list.size) {
            Component(
                title = list[it]
            )
        }
    }

}

@Composable
private fun Component(
    title: String
) {
    var visible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .background(
                color = Color.LightGray
            )
            .clickable { visible = !visible }
            .onSizeChanged {
                Log.e(TAG, "Parent Layout ")
            }
            .drawWithContent {
                Log.e(TAG, "Parent Draw")
                drawContent()
            }
    ) {
        Text(
            text = title,
            modifier = Modifier
                .aspectRatio(1f)
                .background(color = Color.Green)
                .onSizeChanged {
                    Log.e(TAG, "First Child Layout")
                }
                .drawWithContent {
                    Log.e(TAG, "First Child Draw")
                    drawContent()
                },
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        if (visible) {
            Text(
                text = "Second Child",
                color = Color.Black,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Magenta)
                    .layout { measurable, constraints ->
                        Log.e(TAG, "Second Child: $constraints")
                        layout(width = constraints.maxWidth, height = constraints.maxHeight) {
                        }
                    }
                    .onSizeChanged {
                        Log.e(TAG, "Second Child Layout")
                    }
                    .drawWithContent {
                        Log.e(TAG, "Second Child Draw")
                        drawContent()
                    },
                textAlign = TextAlign.Center
            )
        }
    }
}