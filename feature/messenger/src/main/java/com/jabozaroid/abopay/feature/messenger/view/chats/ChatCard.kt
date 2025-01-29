package com.jabozaroid.abopay.feature.messenger.view.chats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.jabozaroid.abopay.R
import com.jabozaroid.abopay.core.designsystem.theme.AppTheme
import com.jabozaroid.abopay.core.designsystem.theme.designsystem.Dimens

// done all icon
@Composable
fun ChatCard(
    chatUiModel: ChatUiModel,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimens.size_4, bottom = Dimens.size_4)
            .height(Dimens.size_100),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.size_4),
        shape = RoundedCornerShape(Dimens.size_12)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.size_8)
                .fillMaxHeight()
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (profileRef, contactRef, detailRef) = createRefs()

                // Circular Profile Image
                Image(painter = chatUiModel.profileImageUrl?.let { rememberAsyncImagePainter(it) }
                    ?: painterResource(id = R.drawable.ic_person), // Replace with your placeholder
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .constrainAs(profileRef) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop)

                // Name & Role Column (Centered Vertically & Horizontally with the Image)
                Column(
                    modifier = Modifier
                        .padding(start = Dimens.size_8)
                        .constrainAs(contactRef) {
                            start.linkTo(profileRef.end)
                            top.linkTo(profileRef.top)
                            bottom.linkTo(profileRef.bottom)
                        }
                        .fillMaxHeight(), verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = chatUiModel.username,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(text = chatUiModel.lastMsg, color = Color.Gray, fontSize = 14.sp)
                }

                // Text & Small Circular Number
                Column(
                    modifier = Modifier.constrainAs(detailRef) {
                        end.linkTo(parent.end)
                        top.linkTo(profileRef.top)
                        bottom.linkTo(profileRef.bottom)
                    },
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = chatUiModel.msgDate, fontWeight = FontWeight.Bold, fontSize = 16.sp)

                    // Small Circle with Number
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(Color.Green),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = chatUiModel.msgNumber,
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun PreviewChatCard() {
    AppTheme {
        ChatCard(
            ChatUiModel(
                profileImageUrl = null,
                username = "Abozar rbdt",
                lastMsg = "Please send me your id",
                msgDate = "Yesterday",
                msgNumber = "10" // Number inside the small green circle
            )
        )
    }
}
