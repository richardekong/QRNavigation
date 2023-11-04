package com.daveace.qrnavigationapp.screen

import android.content.Context
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bumptech.glide.RequestManager
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.RequestBuilderTransform
import com.daveace.qrnavigationapp.R
import com.daveace.qrnavigationapp.model.Organization
import com.daveace.qrnavigationapp.model.WelcomeNote
import com.daveace.qrnavigationapp.model.organizations
import com.daveace.qrnavigationapp.model.welcomeNotes
import com.daveace.qrnavigationapp.ui.theme.background
import com.daveace.qrnavigationapp.ui.theme.buttonTheme
import com.daveace.qrnavigationapp.ui.theme.cardTheme
import com.daveace.qrnavigationapp.ui.theme.iconTint
import kotlinx.coroutines.delay

@Composable
fun Home(modifier: Modifier = Modifier, context: Context = LocalContext.current) {
    Column {
        HomeTopSection(modifier = modifier)
        WelcomeNotesPager(modifier = modifier, welcomeNotes(context))
        OrganizationsSection(modifier = modifier, organizations = organizations())
    }
}

@Composable
fun HomeTopSection(modifier: Modifier) {
    Column {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp),
            shape = MaterialTheme.shapes.extraSmall,
            colors = cardTheme(),
            elevation = CardDefaults.cardElevation(4.dp),
            border = CardDefaults.outlinedCardBorder(enabled = true)
        ) {

            Text(
                text = stringResource(R.string.welecome_text),
                modifier = modifier.padding(4.dp),
                fontWeight = MaterialTheme.typography.titleMedium.fontWeight
            )
            Button(
                onClick = { /*TODO*/ },
                colors = buttonTheme(),
                shape = MaterialTheme.shapes.extraSmall,
                contentPadding = PaddingValues(0.dp),
                modifier = modifier
                    .padding(end = 4.dp, bottom = 4.dp)
                    .size(width = 40.dp, height = 20.dp)
                    .align(Alignment.End)
            ) {
                Text(
                    text = stringResource(id = R.string.login_label),
                    fontSize = TextUnit(8F, TextUnitType.Sp)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeNotesPager(modifier: Modifier, notes: List<WelcomeNote> = listOf()) {

    val pagerState = rememberPagerState(pageCount = {
        notes.size
    })
    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            val currentPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.animateScrollToPage(currentPage)
        }
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = MaterialTheme.shapes.extraSmall,
        colors = cardTheme(),
        elevation = CardDefaults.cardElevation(4.dp),
        border = CardDefaults.outlinedCardBorder(enabled = true)
    ) {
        HorizontalPager(state = pagerState) { page ->
            WelcomeNoteUI(modifier = modifier, welcomeNote = notes[page])
        }
    }

}

@Composable
fun WelcomeNoteUI(modifier: Modifier, welcomeNote: WelcomeNote) {
    Column(modifier = modifier.height(120.dp)) {
        val style = ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))

        Icon(
            painter = painterResource(id = welcomeNote.icon),
            contentDescription = stringResource(R.string.welcome_note_icon),
            tint = iconTint(),
            modifier = modifier.padding(4.dp)
        )

        Text(
            buildAnnotatedString {
                welcomeNote.texts.forEach { text ->
                    val bullet = "\u2217"
                    withStyle(style = style) {
                        append(bullet)
                        append("\t")
                        append(text)
                    }
                }
            },
            modifier = modifier.padding(8.dp)
        )

    }
}

@Composable
fun OrganizationsSection(
    modifier: Modifier,
    organizations: List<Organization> = listOf()
) {
    Card(
        modifier = modifier
            .wrapContentWidth()
            .padding(4.dp),
        shape = MaterialTheme.shapes.extraSmall,
        colors = cardTheme(),
        elevation = CardDefaults.cardElevation(4.dp),
        border = CardDefaults.outlinedCardBorder(enabled = true)) {

        Row(modifier = modifier
            .align(Alignment.Start)
            .padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_business_24),
                contentDescription = "org_icon",
                tint = iconTint(),
                modifier = modifier.padding(4.dp)
            )

            Text(
                text = stringResource(R.string.pick_an_organization),
                fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                modifier= modifier.align(Alignment.CenterVertically))
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp),
            content = {
                items(organizations) { org ->
                    OrganizationItemUI(modifier, org)
                }
            },
            modifier = modifier.padding(4.dp)
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun OrganizationItemUI(modifier: Modifier, org: Organization) {
    Card(
        modifier = modifier
            .wrapContentWidth()
            .padding(4.dp),
        shape = MaterialTheme.shapes.extraSmall,
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.md_theme_light_primary),
            contentColor = colorResource(id = R.color.md_theme_light_onPrimary)
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        border = CardDefaults.outlinedCardBorder(enabled = true)
    ) {
        GlideImage(
            model = org.logo,
            contentDescription = "${org.name} logo",
            modifier = modifier.size(width = 120.dp, height = 120.dp)
        ) { request ->
            request.load(org.logo)

        }
        Text(
            text = org.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(2.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    Home()
}