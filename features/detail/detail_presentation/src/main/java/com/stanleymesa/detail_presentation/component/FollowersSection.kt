package com.stanleymesa.detail_presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stanleymesa.core.ui.component.compose.DefaultSpacer

@Composable
fun FollowersSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            FollowerCard(
                modifier = Modifier.weight(1f),
                title = "100",
                value = "Followers"
            )
            DefaultSpacer()
            FollowerCard(
                modifier = Modifier.weight(1f),
                title = "100",
                value = "Followers"
            )
        }
        DefaultSpacer()
        Row(modifier = Modifier.fillMaxWidth()) {
            FollowerCard(
                modifier = Modifier.weight(1f),
                title = "100",
                value = "Followers"
            )
            DefaultSpacer()
            FollowerCard(
                modifier = Modifier.weight(1f),
                title = "100",
                value = "Followers"
            )
        }
    }
}

@Composable
fun FollowerCard(modifier: Modifier, title: String, value: String) {
    Card(
        modifier = modifier.height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp))
            Text(value, style = MaterialTheme.typography.bodyMedium)
        }
    }
}