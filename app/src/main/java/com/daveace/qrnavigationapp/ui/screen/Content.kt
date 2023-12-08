package com.daveace.qrnavigationapp.ui.screen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.daveace.qrnavigationapp.R
import com.daveace.qrnavigationapp.data.Space
import com.daveace.qrnavigationapp.data.Subspace
import com.daveace.qrnavigationapp.model.QRNavigationViewModel
import com.daveace.qrnavigationapp.ui.theme.cardTheme
import java.lang.String.format
import java.time.Duration
import java.time.format.DateTimeFormatter

@Composable
fun Content(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(all = 8.dp)
    ) {
        SpacesSection(modifier)
        Spacer(modifier = modifier.height(8.dp))
        EventsSection(modifier)
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun SpacesSection(modifier: Modifier, viewModel: QRNavigationViewModel = QRNavigationViewModel()) {
    //Todo: get the selected space and subspace from spaces explorer screen
    val space: Space? = viewModel.spaces().find { it.id == 2 }
    val subspace: Subspace? = space?.subspaces?.find { it.id == 2 }
    val photoURLs = subspace?.photoURL?.split(",")
    val address = viewModel.addresses().find { it.id == space?.addressId }
    val carouselPagerState = rememberPagerState(pageCount = {
        photoURLs?.size ?: 1
    })

    LaunchedEffect(Unit) {
        val currentPage = (carouselPagerState.currentPage + 1) % carouselPagerState.pageCount
        carouselPagerState.animateScrollToPage(
            currentPage, animationSpec = tween(durationMillis = 5000)
        )
    }

    Card(
        shape = MaterialTheme.shapes.small,
        border = CardDefaults.outlinedCardBorder(true),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = cardTheme(),
        modifier = modifier.fillMaxWidth()
    ) {
        val border = ContentBorder()
        Column(
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = border.width,
                    color = colorResource(id = border.color),
                    shape = border.roundedTopShape
                )
        ) {
            Text(
                text = space?.name ?: "",
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )
        }
        Spacer(modifier = modifier.height(4.dp))
        Column(modifier = modifier.padding(8.dp)) {
            HorizontalPager(
                state = carouselPagerState, modifier = modifier.padding(all = 4.dp)
            ) { page ->
                GlideImage(
                    model = photoURLs?.get(page),
                    contentDescription = "subspace photo",
                    modifier = modifier.fillMaxWidth()
                )
            }
            ContentDetails(
                modifier = modifier,
                contentTitle = stringResource(id = R.string.description),
                contentDetails = listOf(space?.description ?: "")
            )
            Spacer(modifier = modifier.height(4.dp))
            ContentDetails(
                modifier = modifier, contentTitle = "", contentDetails = listOf(
                    String.format(stringResource(R.string.address), address?.description),
                    String.format(stringResource(R.string.postcode), address?.postcode)
                )
            )
        }
    }
}


@Composable
fun EventsSection(modifier: Modifier, viewModel: QRNavigationViewModel = QRNavigationViewModel()) {
    //Todo: get events associated with the current space
    val event = viewModel.events().find { it.id == 2 }
    val venueStrings: List<String>? = event?.venues?.map { venue ->
        val space = viewModel.spaces().find { it.id == venue.spaceId }
        val subspace = space?.subspaces?.find { it.id == venue.subspaceId }
        "${space?.name}/${subspace?.name}"
    }
    Card(
        shape = MaterialTheme.shapes.small,
        border = CardDefaults.outlinedCardBorder(true),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = cardTheme(),
        modifier = modifier.fillMaxWidth()
    ) {

        val border = ContentBorder()
        Column(
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = border.width,
                    color = colorResource(id = border.color),
                    shape = border.roundedTopShape
                )
        ) {
            Text(
                text = event?.name ?: "",
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )
        }
        Spacer(modifier = modifier.height(4.dp))
        Column(modifier = modifier.padding(8.dp)) {
            event?.apply {
                val datePattern = "H:mm EEE, d MMM yyyy"
                val startTime = start.format(DateTimeFormatter.ofPattern(datePattern))
                val endTime = end.format(DateTimeFormatter.ofPattern(datePattern))
                val duration = "${Duration.between(start, end).toHours()}"
                ContentDetails(
                    modifier = modifier,
                    contentTitle = stringResource(id = R.string.description),
                    contentDetails = listOf(
                        description,
                        format(stringResource(R.string.start_time_format), startTime),
                        format(stringResource(R.string.end_time_format), endTime),
                        format(stringResource(R.string.duration_format), duration),
                    )
                )
            }
            Spacer(modifier = modifier.height(4.dp))
            ContentDetails(
                modifier = modifier,
                contentTitle = stringResource(R.string.venues),
                contentDetails = venueStrings ?: listOf()
            )
        }
    }

}

@Composable
fun ContentDetails(
    modifier: Modifier,
    contentTitle: String,
    contentDetails: List<String> = listOf(),
    border: ContentBorder = ContentBorder()
) {
    val borderColor = colorResource(id = R.color.md_theme_light_onSecondaryContainer)
    val roundedTopShape =
        RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp, bottomStart = 0.dp, bottomEnd = 0.dp)
    val borderWidth = 1.dp
    Column(
        modifier = modifier
            .padding(all = 4.dp)
            .border(
                width = border.width,
                color = colorResource(border.color),
                shape = RoundedCornerShape(size = 5.dp)
            )
    ) {
        if (contentTitle.isNotEmpty()) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .border(width = borderWidth, color = borderColor, shape = roundedTopShape)
            ) {
                Text(
                    text = contentTitle,
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(4.dp)
                )

            }
        }
        contentDetails.forEach {
            Text(
                text = it,
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )
            Spacer(modifier = modifier.height(4.dp))
        }
    }
}

class ContentBorder {
    var color = R.color.md_theme_light_onSecondaryContainer
    var roundedTopShape = RoundedCornerShape(
        topStart = 5.dp,
        topEnd = 5.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )
    var width = 1.dp
}

@Preview(showBackground = true)
@Composable
fun PreviewContent() {
    Content()
}
