package com.stanleymesa.detail_presentation.bri.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.stanleymesa.core.ui.component.compose.DefaultSpacer
import com.stanleymesa.core.ui.theme.LocalDimen
import com.stanleymesa.core.ui.theme.LocalShapes

@Composable
fun BriCard(modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalDimen.current.regular)
        ) {
            val (card, bank, status) = createRefs()

            /** Card Image */
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(LocalShapes.medium)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer
                    )
                    .constrainAs(card) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
            )

            /** Bank */
            Column(
                modifier = Modifier.constrainAs(status) {
                    top.linkTo(parent.top)
                    start.linkTo(card.end, margin = 12.dp)
                    end.linkTo(status.start, margin = 12.dp)
                },
            ) {
                Text("Kartu Debit BRI")
                DefaultSpacer(height = LocalDimen.current.small)
                Text("238746983764983724")
                DefaultSpacer(height = LocalDimen.current.small)
                Text("Rp 100.000")
            }

            /** Status */
            Column(
                modifier = Modifier.constrainAs(bank) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
            ) {
                Text("31 Nov 2022")
                DefaultSpacer(height = LocalDimen.current.small)
                Text("23:59:59")
                DefaultSpacer(height = LocalDimen.current.small)
                Text("Purchase")
            }
        }
    }
}