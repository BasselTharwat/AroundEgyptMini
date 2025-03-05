package com.example.aroundegyptmini


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.aroundegyptmini.ui.AroundEgyptMiniApp
import com.example.aroundegyptmini.ui.theme.AroundEgyptMiniTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AroundEgyptMiniTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    AroundEgyptMiniApp()
                }
            }
        }
    }
}
