package com.example.messenger.ui.screens.main.chat.bar.top

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.messenger.R
import com.example.messenger.`typealias`.Fun
import com.example.messenger.ui.theme.ChatNameStyle
import com.example.messenger.ui.theme.MembersCountStyle
import com.example.messenger.ui.theme.TelegramBlue
import com.example.messenger.ui.theme.White

@Composable
fun TopBar(countMembers: Int, openDrawer: Fun) {
    TopAppBar(modifier = Modifier.fillMaxWidth(), backgroundColor = TelegramBlue) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {

            val (menuIcon, infoIcon, chatIcon, chatName, charCountMembers) = createRefs()

            IconButton(onClick = openDrawer, modifier = Modifier.constrainAs(menuIcon) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "Открыть меню",
                    tint = White
                )
            }

            Image(
                painter = painterResource(id = R.drawable.door),
                contentDescription = "Иконка беседы",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .constrainAs(chatIcon) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(menuIcon.end, 16.dp)
                    }
            )

            Text(
                text = "Общая беседа",
                style = ChatNameStyle,
                modifier = Modifier.constrainAs(chatName) {
                    top.linkTo(parent.top, 6.dp)
                    start.linkTo(chatIcon.end, 16.dp)
                })

            Text(
                text = "$countMembers участников",
                style = MembersCountStyle,
                modifier = Modifier.constrainAs(charCountMembers) {
                    bottom.linkTo(parent.bottom, 8.dp)
                    start.linkTo(chatIcon.end, 16.dp)
                })

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.constrainAs(infoIcon) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }) {
                Icon(
                    imageVector = Icons.Rounded.Info,
                    contentDescription = "Информация о беседе",
                    tint = White
                )
            }
        }
    }
}