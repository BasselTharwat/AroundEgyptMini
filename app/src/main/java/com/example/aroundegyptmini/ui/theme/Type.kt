package com.example.aroundegyptmini.ui.theme

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aroundegyptmini.R

val Gotham = FontFamily(
    Font(R.font.gothambook),
    Font(R.font.gothammedium, FontWeight.Medium),
    Font(R.font.gothambold, FontWeight.Bold)
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Gotham,
        fontWeight = FontWeight(700),
        fontSize = 22.sp,
        lineHeight = 26.sp,
    ),

    displayMedium = TextStyle(
        fontFamily = Gotham,
        fontWeight = FontWeight(500) ,
        fontSize = 14.sp,
        lineHeight = 16.sp,
    ),

    displayMediumEmphasized = TextStyle(
        fontFamily = Gotham,
        fontWeight = FontWeight(700) ,
        fontSize = 14.sp,
        lineHeight = 16.sp,
    )

)