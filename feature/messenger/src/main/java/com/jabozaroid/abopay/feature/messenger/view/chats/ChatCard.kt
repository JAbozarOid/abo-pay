package com.jabozaroid.abopay.feature.messenger.view.chats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.jabozaroid.abopay.R
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

@Composable
fun ChatCard(
    profileImageUrl: String? = null,
    name: String = "John Doe",
    role: String = "Software Engineer",
    statText: String = "Score",
    statNumber: String = "10" // Number inside the small green circle
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.size_4),
        shape = RoundedCornerShape(Dimens.size_12)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Circular Profile Image
            Image(
                painter = profileImageUrl?.let { rememberAsyncImagePainter(it) }
                    ?: painterResource(id = R.drawable.ic_person), // Replace with your placeholder
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentScale = ContentScale.Crop
            )

            // Name & Role Column (Centered Vertically & Horizontally with the Image)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = role, color = Color.Gray, fontSize = 14.sp)
            }

            // Text & Small Circular Number
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = statText, fontWeight = FontWeight.Bold, fontSize = 16.sp)

                // Small Circle with Number
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(Color.Green),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = statNumber,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewChatCard() {
    AppTheme {
        ChatCard()
    }
}
