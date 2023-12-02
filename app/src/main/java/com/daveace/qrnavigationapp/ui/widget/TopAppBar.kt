package com.daveace.qrnavigationapp.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.daveace.qrnavigationapp.R
import com.daveace.qrnavigationapp.ui.theme.topAppBarTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun QRNavigationTopAppBar(
    title: String = stringResource(id = R.string.app_name_text),
    navigationIcon: AppBarIcon,
    actionsIcon: AppBarIcon? = null
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    CenterAlignedTopAppBar(
        colors = topAppBarTheme(),
        title = {
            Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis)
        },
        navigationIcon = {
            navigationIcon.apply {
                if (logoURL.isNotEmpty()) {
                    GlideImage(
                        model = logoURL,
                        contentDescription = "description",
                        modifier = Modifier
                            .clickable(enabled = true, onClick = onClick)
                            .size(50.dp)
                    ) { request ->
                        request.load(logoURL)

                    }
                } else {
                    IconButton(onClick = onClick) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = description,
                            tint = colorResource(id = tint)
                        )
                    }
                }
            }
        },
        actions = {
            actionsIcon?.let {
                it.apply {
                    IconButton(onClick = onClick) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = description,
                            tint = colorResource(id = tint)
                        )
                    }
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}


class AppBarIcon(
    val icon: Int = R.drawable.three_dqr_code,
    val logoURL: String = "",
    val description: String = "",
    val tint: Int = R.color.md_theme_light_onPrimary,
    val onClick: () -> Unit = {},
)


