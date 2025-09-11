package com.stanleymesa.search_presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.stanleymesa.core.R
import com.stanleymesa.core.shared_data.model.User
import com.stanleymesa.core.ui.component.compose.DefaultSpacer
import com.stanleymesa.core.ui.theme.LocalDimen

@Composable
fun UserCard(modifier: Modifier = Modifier, user: User) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {}
                .padding(
                    horizontal = LocalDimen.current.regular,
                    vertical = LocalDimen.current.medium
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(LocalDimen.current.extraLarge)
                    .clip(CircleShape),
                model = user.avatarUrl,
                placeholder = painterResource(R.drawable.ic_github),
                contentDescription = "",
            )
            DefaultSpacer(width = LocalDimen.current.medium)
            Text(
                modifier = Modifier.weight(1f),
                text = user.login,
                style = MaterialTheme.typography.labelMedium
            )
            DefaultSpacer(width = LocalDimen.current.medium)
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = ""
            )
        }
    }
}