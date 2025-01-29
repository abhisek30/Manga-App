package com.abhisek.manga.app.presentation.ui.list.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.abhisek.manga.app.core.util.epochToYear
import com.abhisek.manga.app.domain.model.Manga

@Composable
fun MangaCard(
    modifier: Modifier = Modifier,
    manga: Manga,
    onClick: () -> Unit,
    onFavoriteCta: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black)
    ) {

        SubcomposeAsyncImage(
            model = manga.image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp)),
            contentScale = ContentScale.Crop,
            error = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Error Loading Image")
                }
            },
            loading = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
        )

        Text(
            text = manga.title,
            modifier = Modifier.padding(top = 12.dp, start = 12.dp),
            fontWeight = FontWeight.Bold
        )

        Row(modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp)) {
            Text(
                text = "Popularity : ${manga.popularity}",
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Score : ${manga.score}",
            )
        }

        Row(modifier = Modifier.padding(top = 4.dp, start = 12.dp, end = 12.dp, bottom = 12.dp)) {
            Icon(
                imageVector = if (manga.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                tint = Color.Red,
                contentDescription = null,
                modifier = Modifier.clickable {
                    onFavoriteCta()
                }
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "Year Published : ${epochToYear(manga.publishedChapterDate)}",
            )
        }

    }
}

@Preview
@Composable
private fun MangaCardPreview() {
    MangaCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.White)
            .padding(4.dp),
        manga = Manga(
            id = "",
            image = "",
            score = 0.0,
            popularity = 0,
            title = "",
            publishedChapterDate = 0,
            category = "",
            isFavorite = false,
            isRead = false
        ),
        onClick = {},
        onFavoriteCta = {},
    )
}