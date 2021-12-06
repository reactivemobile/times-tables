package com.reactivemobile.timestables.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeadlineText(@StringRes stringRes: Int) {
    Text(
        text = stringResource(id = stringRes),
        fontSize = 32.sp,
        modifier = Modifier.padding(all = 8.dp)
    )
}

@Composable
fun HeadlineText(string :String) {
    Text(
        text = string,
        fontSize = 32.sp,
        modifier = Modifier.padding(all = 8.dp)
    )
}

@Composable
fun BodyText(@StringRes stringRes: Int) {
    Text(text = stringResource(id = stringRes), modifier = Modifier.padding(all = 8.dp), fontSize = 24.sp)
}