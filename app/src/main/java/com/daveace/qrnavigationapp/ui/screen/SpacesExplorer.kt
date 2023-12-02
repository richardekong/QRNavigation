package com.daveace.qrnavigationapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.daveace.qrnavigationapp.R
import com.daveace.qrnavigationapp.data.Organization
import com.daveace.qrnavigationapp.ui.theme.buttonTheme
import com.daveace.qrnavigationapp.ui.theme.cardTheme
import com.daveace.qrnavigationapp.ui.theme.iconTint
import com.daveace.qrnavigationapp.ui.widget.DropDownMenu
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.daveace.qrnavigationapp.model.QRNavigationViewModel


@Composable
fun SpacesExplorer(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    val viewModel = QRNavigationViewModel()
    val currentOrganization by remember {
        mutableStateOf(viewModel.organizations()[1])
    }

    Column() {
//        TopSection(modifier, currentOrganization)
        MidSection(modifier)
        BottomSection(modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSpacesExplorer() {
    SpacesExplorer()
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun TopSection(modifier: Modifier, org: Organization) {
    val background = R.color.md_theme_light_onSecondaryContainer
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = colorResource(id = background))
            .padding(all = 4.dp)
    ) {
        if (org.logoURL.isEmpty()) {
            //set default image if current organization's logo is not present
            Image(
                painter = painterResource(id = R.drawable.baseline_business_24),
                contentDescription = stringResource(id = R.string.org_logo)
            )
        } else {
            //get current organization logo
            GlideImage(
                model = org.logoURL,
                contentDescription = "${org.name}'s logo",
                modifier = modifier.size(60.dp)
            ) { it.load(org.logoURL) }
        }
        //current organization name and trailing text
        val alignment = Alignment.CenterVertically
        val fontSize = TextUnit(20f, TextUnitType.Sp)
        val overflow = TextOverflow.Ellipsis
        val fontWeight = FontWeight.Bold
        val maxLines = 1
        if (org.name.isEmpty()) {
            Text(
                text = stringResource(R.string.loading),
                fontSize = fontSize,
                fontWeight = fontWeight,
                maxLines = maxLines,
                overflow = overflow,
                modifier = modifier
                    .align(alignment)
                    .padding(all = 4.dp)
            )
        } else {
            Text(
                text = stringResource(R.string.explore, org.name),
                fontSize = fontSize,
                fontWeight = fontWeight,
                maxLines = maxLines,
                overflow = overflow,
                color = colorResource(id = R.color.md_theme_light_secondaryContainer),
                modifier = modifier
                    .align(alignment)
                    .padding(all = 4.dp)
            )
        }
    }
}

@Composable
private fun ColumnScope.MidSection(modifier: Modifier) {
    //map coordinates
    val coordinates = LatLng(0.0, 0.0)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(coordinates, 10f)
    }
    val uiSettings by remember {
        mutableStateOf(MapUiSettings())
    }

    val mapProperties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    GoogleMap(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(1f),
        cameraPositionState = cameraPositionState,
        uiSettings = uiSettings,
        properties = mapProperties
    ) {
        Marker(
            state = MarkerState(position = coordinates),
            //other properties will be incorporated soon
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun BottomSection(modifier: Modifier) {
    //The main spaces and subspaces will be retrieved from a viewModel
    val mainSpaces = listOf("HQ")
    val subSpaces = listOf("6.01")

    val qrCodeURL = ""
    val imageWeight = 1f
    val dropDownWeight = 2f
    val size = 100
    val padding = 4
    val contentDescription = stringResource(id = R.string.qr_code_image)
    Card(
        shape = MaterialTheme.shapes.small,
        border = CardDefaults.outlinedCardBorder(true),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = cardTheme(),
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
    ) {


        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(padding.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                tint = iconTint(),
                contentDescription = stringResource(R.string.info_icon),
                modifier = modifier.padding(padding.dp)
            )
            Text(
                text = stringResource(R.string.pick_spaces_and_read_qr_code),
                fontSize = TextUnit(15f, TextUnitType.Sp),
                modifier = modifier
                    .padding(all = padding.dp)
                    .align(Alignment.CenterVertically)
            )
        }

        Row(modifier = modifier.fillMaxWidth()) {
            Column(
                modifier = modifier
                    .wrapContentHeight()
                    .weight(dropDownWeight)
            ) {
                //dropdown menu for filtering a list of main spaces belonging to an organization
                DropDownMenu(
                    options = mainSpaces,
                    label = stringResource(id = R.string.main_spaces),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(all = padding.dp)
                )
                //dropdown menu for filtering a list subspaces within a main space
                DropDownMenu(
                    options = subSpaces,
                    label = stringResource(id = R.string.sub_spaces),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(all = padding.dp)
                )
            }
            Column(
                modifier = modifier
                    .wrapContentHeight()
                    .weight(imageWeight)
            ) {
                //QR code associated with the current main space and a given subspace
                if (qrCodeURL.isNotEmpty()) {
                    GlideImage(
                        model = qrCodeURL,
                        contentDescription = contentDescription,
                        modifier = modifier
                            .size(size.dp)
                            .padding(all = padding.dp)
                    ) { it.load(qrCodeURL) }
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.qr_code_icon),
                        contentDescription = contentDescription,
                        modifier = modifier
                            .size(size.dp)
                            .padding(all = padding.dp)
                    )
                }
                //button to read QR code associated with a subspace within a space
                Button(
                    onClick = { /*TODO*/ },
                    shape = MaterialTheme.shapes.small,
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 2.dp,
                        pressedElevation = 4.dp
                    ),
                    colors = buttonTheme(),
                    modifier = modifier
                        .wrapContentWidth()
                        .padding(all = 4.dp)
                ) {
                    Text(text = stringResource(R.string.read_qr))
                }
            }

        }

    }
    Spacer(modifier.height(20.dp))

}
