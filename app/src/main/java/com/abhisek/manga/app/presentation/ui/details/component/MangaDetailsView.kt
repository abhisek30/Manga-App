package com.abhisek.manga.app.presentation.ui.details.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.abhisek.manga.app.core.util.epochToReadableDate
import com.abhisek.manga.app.domain.model.Manga

@Composable
fun MangaDetailsView(
    modifier: Modifier = Modifier,
    manga: Manga,
    onFavoriteCta: () -> Unit,
    onReadCta: () -> Unit,
) {
    Column(modifier = modifier) {
        SubcomposeAsyncImage(
            model = manga.image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp)),
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

        Row(modifier = Modifier.padding(top = 4.dp, start = 12.dp, end = 12.dp)) {
            Text(
                text = "Category : ${manga.category}",
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Date : ${epochToReadableDate(manga.publishedChapterDate)}",
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Button(
                onClick = {
                    onFavoriteCta()
                }, modifier = Modifier.padding(end = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                ),
                border = BorderStroke(1.dp, Color.Black),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = if (manga.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    tint = Color.Red,
                    contentDescription = null,
                )
            }
            Button(
                onClick = {
                    onReadCta()
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                ),
                border = BorderStroke(1.dp, Color.Black),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Mark as ${if (manga.isRead) "Unread" else "Read"}")
            }
        }
    }
}

@Preview
@Composable
private fun MangaDetailsViewPreview() {
    MangaDetailsView(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        manga = Manga(
            id = "",
            image = "",
            score = 0.0,
            popularity = 0,
            title = "",
            publishedChapterDate = 0,
            category = "",
            isFavorite = false,
            isRead = true,
        ),
        {},
        {}
    )
}