package net.wandroid.quantummovie.presentation.movie_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import net.wandroid.quantummovie.R

@Composable
fun MovieItem(
    title: String,
    yearOfRelease: String,
    posterUrl: String?,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            AsyncImage(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .fillMaxWidth(),
                placeholder = painterResource(R.drawable.placeholder),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(posterUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.movie_item_content_description_poster),
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = yearOfRelease,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            onClick = onClick
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                text = stringResource(R.string.movie_item_details)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewMovieItem() {
    MovieItem(
        title = "The Title if it was very long",
        yearOfRelease = "25 May 1977",
        posterUrl = null,
        onClick = {},
    )
}