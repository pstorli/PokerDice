package com.pstorli.pokerdice.ui.composeables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pstorli.pokerdice.R
import com.pstorli.pokerdice.conditional
import com.pstorli.pokerdice.util.Consts
import androidx.compose.material3.Text as TextMat

@Composable
fun LabeledRow (title: String, titleColor: Color, maxWidth: Boolean=true, content: @Composable () -> Unit) {
    Box() {
        Box(
            modifier = Modifier
                .conditional(maxWidth) {
                    fillMaxWidth()
                }
                .padding(4.dp,12.dp,4.dp,4.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .padding(4.dp,12.dp,4.dp,4.dp))
        {
            Row() {
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

@Preview
@Composable
fun LabeledRow() {
    LabeledRow(title = stringResource(id = R.string.player),
        Consts.color(Consts.COLOR_BORDER_NAME, LocalContext.current)
    ) {
        TextMat(
            modifier = Modifier.padding(horizontal = Consts.PADDING_DEFAULT_VAL),
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline
        )
    }
}
