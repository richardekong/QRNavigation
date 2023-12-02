package com.daveace.qrnavigationapp.ui.screen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.daveace.qrnavigationapp.R


@Composable
fun Splash(modifier: Modifier = Modifier) {

    val navy = R.color.md_theme_light_onSecondaryContainer
    Column(
        modifier = modifier
            .background(colorResource(id = navy))
    ) {
        TopSection(modifier, navy)
        MidSection()
        BottomSection(modifier)
    }

}

@Composable
private fun ColumnScope.BottomSection(modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
            .weight(1F)
            .fillMaxWidth()
    ) {

        Image(
            painter = painterResource(id = R.drawable.splash_wave),
            contentDescription = stringResource(R.string.splash_wave_background),
        )
    }
}

@Composable
private fun TopSection(modifier: Modifier, navy: Int) {
    Column(
        modifier = modifier.padding(all = 10.dp)
    ) {
        Row(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .background(
                    color = colorResource(id = R.color.md_theme_light_secondaryContainer),
                    shape = MaterialTheme.shapes.small
                )
                .fillMaxWidth()
                .padding(all = 40.dp)
        ) {
            Text(
                text = stringResource(R.string.app_name_text),
                modifier = modifier.padding(start = 2.dp, end = 2.dp),
                color = colorResource(id = navy),
                fontSize = TextUnit(40F, TextUnitType.Sp),
                fontWeight = FontWeight.Bold
            )

            Icon(
                painter = painterResource(id = R.drawable.loc_marker),
                contentDescription = stringResource(R.string.location_marker_icon),
                tint = Color.Red,
                modifier = modifier
                    .size(40.dp)
                    .padding(end = 2.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
private fun ColumnScope.MidSection() {

    var rotationState by remember { mutableFloatStateOf(0F) }
    val rotation by rotation()
    rotationState = rotation

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .weight(2F)
            .rotate(rotationState)
    ) {

        Icon(
            painter = painterResource(id = R.drawable.three_dqr_code),
            contentDescription = stringResource(R.string.threeD_qr_code),
            tint = colorResource(id = R.color.md_theme_light_secondaryContainer),
            modifier = Modifier
                .size(width = 200.dp, height = 200.dp)
                .align(Alignment.CenterHorizontally)
        )

    }
}

@Composable
private fun rotation() = rememberInfiniteTransition(label = "")
    .animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )


@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    Splash()
}