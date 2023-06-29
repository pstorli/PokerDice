package com.pstorli.pokerdice.ui.composeables.core

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pstorli.pokerdice.conditional
import com.pstorli.pokerdice.util.Consts.POKER_BORDER_SIZE_DP
import androidx.compose.material3.Text as TextMat

@Composable
fun LabeledRow (title: String, titleColor: Color, maxWidth: Boolean=false, maxHeight: Boolean=false, minWidth: Dp?=null, content: @Composable () -> Unit) {
    Box() {
        Box(
            modifier = Modifier
                .conditional(maxWidth) {
                    fillMaxWidth()
                }
                .conditional(maxHeight) {
                    fillMaxHeight()
                }
                .conditional(null != minWidth) {
                    requiredWidthIn(min = minWidth!!)
                    widthIn(min = minWidth)
                }
                .padding(4.dp,12.dp,4.dp,4.dp)
                .border(
                    width = POKER_BORDER_SIZE_DP,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .padding(4.dp,12.dp,4.dp,4.dp))
        {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                content()
            }
        }
        Row () {
            Spacer(modifier = Modifier.width(10.dp))
            TextMat(
                text = title,
                color = titleColor,
                modifier = Modifier.padding(2.dp).background(color = MaterialTheme.colorScheme.background)
            )
        }
    }
}
