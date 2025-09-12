package com.stanleymesa.detail_presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.stanleymesa.core.R
import com.stanleymesa.core.ui.component.compose.DefaultSpacer
import com.stanleymesa.core.ui.theme.LocalDimen
import com.stanleymesa.core.util.extentions.toFormattedCount
import com.stanleymesa.detail_domain.model.UserRepos

@Composable
fun RepositoryCard(modifier: Modifier = Modifier, userRepos: UserRepos) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalDimen.current.regular),
        ) {
            Text(userRepos.name, style = MaterialTheme.typography.labelMedium)
            if (userRepos.description.isNotBlank()) {
                DefaultSpacer(height = LocalDimen.current.default)
                Text(
                    userRepos.description,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            DefaultSpacer(height = LocalDimen.current.medium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = "",
                    )
                    DefaultSpacer(width = LocalDimen.current.small)
                    Text(
                        userRepos.stargazersCount.toFormattedCount(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                DefaultSpacer(width = LocalDimen.current.regular)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_fork),
                        contentDescription = "",
                    )
                    DefaultSpacer(width = LocalDimen.current.small)
                    Text(
                        userRepos.forksCount.toFormattedCount(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}