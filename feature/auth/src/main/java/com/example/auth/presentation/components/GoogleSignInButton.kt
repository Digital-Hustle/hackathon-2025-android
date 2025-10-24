package com.example.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.auth.R

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {



    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(60.dp)
            .clip(RoundedCornerShape(20))
            .background(
                color = MaterialTheme.colorScheme.tertiary
            )
//            .border(1.dp, Color.LightGray, CircleShape)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_google_logo),
            contentDescription = "Sign in with Google",
            tint = Color.Unspecified,
            modifier = Modifier.size(30.dp)
        )
    }
}