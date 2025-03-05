package com.example.aroundegyptmini.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.aroundegyptmini.R
import com.example.aroundegyptmini.model.Experience

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ExperienceItem(
    experience: Experience,
    onExperienceClick: () -> Unit,
    onLikeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(
                top = dimensionResource(R.dimen.padding_medium),
                bottom = dimensionResource(R.dimen.padding_medium),
                start = dimensionResource(R.dimen.padding_small),
                end = dimensionResource(R.dimen.padding_small)
            )
    ) {
        Box(
            modifier = modifier
        ) {
            Card(
                modifier = modifier,
                elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.padding_small)),
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(experience.coverPhoto)
                        .crossfade(true)
                        .build(),
                    error = painterResource(R.drawable.broken_image),
                    placeholder = painterResource(R.drawable.loading_img),
                    contentDescription = stringResource(R.string.experience_photo),
                    contentScale = ContentScale.FillBounds,
                    modifier = modifier
                        .height(dimensionResource(R.dimen.experience_photo_height))
                        .width(
                            dimensionResource(R.dimen.experience_photo_width)
                        )
                        .clickable { onExperienceClick() }
                )
            }

            Image(
                painter = painterResource(R.drawable._60),
                contentDescription = null,
                alignment = Alignment.Center,
                modifier = modifier
                    .align(Alignment.Center)
                    .size(dimensionResource(R.dimen.icon_360_size)),
            )

            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Top Right Icon",
                tint = Color.White,
                modifier = modifier
                    .padding(dimensionResource(R.dimen.padding_extra_small))
                    .align(Alignment.TopEnd)
            )

            Icon(
                imageVector = Icons.Default.PhotoLibrary,
                contentDescription = "Bottom Right Icon",
                tint = Color.White,
                modifier = modifier
                    .align(Alignment.BottomEnd)
                    .padding(dimensionResource(R.dimen.padding_extra_small))
            )

            Icon(
                imageVector = Icons.Default.RemoveRedEye,
                contentDescription = "Bottom Left Icon",
                tint = Color.White,
                modifier = modifier
                    .align(Alignment.BottomStart)
                    .padding(dimensionResource(R.dimen.padding_extra_small))
            )

            Text(
                text = if (experience.viewsNo > 1000) "${experience.viewsNo / 1000}k" else experience.viewsNo.toString(),
                color = Color.White,
                modifier = modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        start = dimensionResource(R.dimen.padding_extra_large),
                        bottom = dimensionResource(R.dimen.padding_extra_small),
                    )
            )
        }


        Row(
            modifier = modifier
        ) {
            Text(
                experience.title.trimEnd(),
                style = MaterialTheme.typography.displayMediumEmphasized,
                modifier = modifier
                    .padding(
                        top = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.experience_item_gap)

                        )
                    .width(dimensionResource(R.dimen.experience_title_width))
            )


            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = if (experience.likesNo > 1000) "${experience.likesNo / 1000}k" else experience.likesNo.toString(),
                    style = MaterialTheme.typography.displayMedium,
                    textAlign = TextAlign.End,
                    modifier = modifier
                        .padding(top = dimensionResource(R.dimen.padding_medium))
                )


                IconButton(
                    modifier = modifier,
                    onClick = { onLikeClick(experience.id) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Bottom Left Icon",
                        tint = Color(0xfff18757),
                        modifier = modifier

                    )
                }


            }


        }


    }
}


@Preview(showBackground = true, widthDp = 412, heightDp = 915, showSystemUi = true)
@Composable
fun ExperienceItemPreview() {
    ExperienceItem(
        experience = Experience(
            id = "123",
            title = "Great Pyramid of Giza",
            coverPhoto = "https://example.com/image.jpg",
            description = "Ancient wonder",
            viewsNo = 1000,
            likesNo = 100,
            recommended = 1,
            detailedDescription = "The last standing wonder of the ancient world",
            address = "Giza, Egypt",
            isLiked = false
        ),
        onExperienceClick = {},
        onLikeClick = {}
    )
}

