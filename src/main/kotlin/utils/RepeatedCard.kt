package utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.CardColor
import theme.textColor

@Composable
fun RepeatedCard(
    modifier: Modifier = Modifier,
    icon: String,
    description: String,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 19.dp,
                end = 19.dp,
                top = 7.dp,
                bottom = 7.dp
            ),
        backgroundColor = CardColor,
        shape = RoundedCornerShape(40.dp),
        elevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage(
                imageUrl = icon,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .size(35.dp)

            )
            Text(
                text = description,
                color = textColor,
                fontSize = 15.sp,
                softWrap = true
            )

        }

    }
}