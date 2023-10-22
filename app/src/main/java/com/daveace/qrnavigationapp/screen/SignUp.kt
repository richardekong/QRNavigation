package com.daveace.qrnavigationapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.daveace.qrnavigationapp.R

@Composable
fun SignUp(modifier: Modifier = Modifier) {
    Column {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(color = Color.White),
            shape = MaterialTheme.shapes.small,
            elevation = 4.dp
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.qr_code_icon),
                    contentDescription = stringResource(R.string.qr_code_image),
                    modifier = modifier
                        .width(60.dp)
                        .height(60.dp)
                )
                Spacer(modifier = modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.signUp_instruction),
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    modifier = modifier.align(Alignment.CenterVertically)
                )
            }
        }
        Spacer(modifier = modifier.height(2.dp))
        Card(
            modifier = modifier
                .wrapContentHeight()
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .background(color = Color.White),
            shape = MaterialTheme.shapes.small,
            elevation = 4.dp
        ) {
            Column {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text(text = stringResource(R.string.email_label)) },
                    placeholder = { Text(text = stringResource(R.string.email_hint)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = stringResource(
                                R.string.email_leading_icon
                            ),
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
                    placeholder = { Text(text = stringResource(R.string.password_hint)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = stringResource(R.string.lock_leading_icon)
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
        Spacer(modifier = modifier.height(2.dp))
        Card(
            modifier = modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .background(color = Color.White),
            shape = MaterialTheme.shapes.small,
            elevation = 4.dp
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
                        Image(
                            painter = painterResource(id = R.drawable.google_icon),
                            contentDescription = stringResource(R.string.google_icon),
                            modifier = columnAlignment
                        )
                        Text(
                            text = stringResource(R.string.google),
                            modifier = columnAlignment
                        )
                    }
                    Spacer(modifier = modifier.width(10.dp))
                    Column() {
                        Image(
                            painter = painterResource(id = R.drawable.facebook_icon),
                            contentDescription = stringResource(R.string.facebook_icon),
                            modifier = columnAlignment
                        )
                        Text(
                            text = stringResource(R.string.facebook),
                            modifier = columnAlignment
                        )
                    }
                    Spacer(modifier = modifier.width(10.dp))
                    Column() {
                        Image(
                            painter = painterResource(id = R.drawable.linkedin_icon),
                            contentDescription = stringResource(R.string.linkedin_icon),
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
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    SignUp()
}