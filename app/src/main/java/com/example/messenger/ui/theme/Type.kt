package com.example.messenger.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h3 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W800,
        fontSize = 28.sp
    ),
    button = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 16.sp,
        fontWeight = FontWeight.W600
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

val Time = TextStyle(
    fontFamily = FontFamily.Default,
    fontSize = 12.sp,
    fontWeight = FontWeight.Normal,
    color = DarkGray
)

val ChatNameStyle = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontSize = 18.sp,
    fontWeight = FontWeight.W600,
    color = White
)

val MembersCountStyle = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontSize = 14.sp,
    fontWeight = FontWeight.Normal,
    color = LightGray
)

val HintStyle = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontSize = 14.sp,
    fontWeight = FontWeight.W600,
    color = DarkGray, textAlign = TextAlign.Center
)

val SystemMessageStyle = TextStyle(
    color = White,
    fontSize = 14.sp,
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.W600
)