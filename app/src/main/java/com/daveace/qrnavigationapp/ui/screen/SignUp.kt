package com.daveace.qrnavigationapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.daveace.qrnavigationapp.R
import com.daveace.qrnavigationapp.ui.theme.background
import com.daveace.qrnavigationapp.ui.theme.buttonTheme
import com.daveace.qrnavigationapp.ui.theme.cardTheme
import com.daveace.qrnavigationapp.ui.theme.iconTint
import com.daveace.qrnavigationapp.ui.theme.textFieldTheme

@Composable
fun SignUp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    Column(modifier = modifier.background(color= background())) {
        TopSection(modifier)
        Spacer(modifier = modifier.height(2.dp))
        SignUpForm(modifier)
        Spacer(modifier = modifier.height(2.dp))
        AlternativeSignupSection(modifier)
    }
}

@Composable
private fun TopSection(modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = MaterialTheme.shapes.small,
        colors= cardTheme(),
        border= CardDefaults.outlinedCardBorder(true),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.qr_code_icon),
                contentDescription = stringResource(R.string.qr_code_image),
                tint = iconTint(),
                modifier = modifier
                    .width(60.dp)
                    .height(60.dp)
            )
            Spacer(modifier = modifier.width(8.dp))
            Text(
                text = stringResource(R.string.signUp_instruction),
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                modifier = modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
private fun ColumnScope.SignUpForm(modifier: Modifier) {
    Card(
        modifier = modifier
            .wrapContentHeight()
            .align(Alignment.CenterHorizontally)
            .padding(16.dp)
            .background(color = Color.White),
        shape = MaterialTheme.shapes.small,
        colors = cardTheme(),
        border= CardDefaults.outlinedCardBorder(true),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = stringResource(R.string.email_label)) },
                placeholder = { Text(text = stringResource(R.string.email_hint)) },
                colors = textFieldTheme(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = stringResource(
                            R.string.email_leading_icon
                        ),
                        tint = iconTint()
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = stringResource(R.string.password_label)) },
                colors = textFieldTheme(),
                placeholder = { Text(text = stringResource(R.string.password_hint)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = stringResource(R.string.lock_leading_icon),
                        tint = iconTint()
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )

            Spacer(modifier.padding(8.dp))

            Button(
                onClick = {},
                shape = MaterialTheme.shapes.small,
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp, pressedElevation = 4.dp
                ),
                colors = buttonTheme(),
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp)
            ) {
                Text(text = stringResource(R.string.signUp_label))
            }
            Spacer(modifier.padding(4.dp))
            Text(
                text = stringResource(R.string.login_link1),
                textDecoration = TextDecoration.Underline,
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp, bottom = 8.dp)
            )
        }
    }
}

@Composable
private fun ColumnScope.AlternativeSignupSection(modifier: Modifier) {
    Card(
        modifier = modifier
            .wrapContentWidth()
            .align(Alignment.CenterHorizontally)
            .padding(16.dp)
            .background(color = Color.White),
        shape = MaterialTheme.shapes.small,
        colors= cardTheme(),
        border= CardDefaults.outlinedCardBorder(true),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
        ) {

            val columnAlignment = modifier.align(Alignment.CenterHorizontally)

            Text(
                text = stringResource(R.string.alternatively_sign_up),
                modifier = modifier.align(Alignment.CenterHorizontally)
            )
            Row(modifier = modifier.align(Alignment.CenterHorizontally)) {
                Column() {
                    Icon(
                        painter = painterResource(id = R.drawable.google_icon),
                        contentDescription = stringResource(R.string.google_icon),
                        tint = iconTint(),
                        modifier = columnAlignment
                    )
                    Text(
                        text = stringResource(R.string.google),
                        modifier = columnAlignment
                    )
                }
                Spacer(modifier = modifier.width(10.dp))
                Column() {
                    Icon(
                        painter = painterResource(id = R.drawable.facebook_icon),
                        contentDescription = stringResource(R.string.facebook_icon),
                        tint= iconTint(),
                        modifier = columnAlignment
                    )
                    Text(
                        text = stringResource(R.string.facebook),
                        modifier = columnAlignment
                    )
                }
                Spacer(modifier = modifier.width(10.dp))
                Column() {
                    Icon(
                        painter = painterResource(id = R.drawable.linkedin_icon),
                        contentDescription = stringResource(R.string.linkedin_icon),
                        tint = iconTint(),
                        modifier = columnAlignment
                    )
                    Text(
                        text = stringResource(R.string.linkedin),
                        modifier = columnAlignment
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    SignUp()
}

