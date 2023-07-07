package com.pstorli.pokerdice.ui.composeables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.pstorli.pokerdice.color
import com.pstorli.pokerdice.ui.theme.Colors

@Composable
fun SideDrawer () {
    Column(
        modifier = Modifier.background(LocalContext.current.color(Colors.Back))
    ) {
        Suits()
        Scoring()
    }
}