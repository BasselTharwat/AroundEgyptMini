package com.example.aroundegyptmini.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.aroundegyptmini.R
import com.example.aroundegyptmini.model.Experience
import kotlin.math.exp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ExperienceItem(
    experience: Experience,
    onExperienceClick: () -> Unit,
    onLikeClick: (String) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .padding(
                top = dimensionResource(R.dimen.padding_medium),
                bottom = dimensionResource(R.dimen.padding_medium),
                start = dimensionResource(R.dimen.padding_small),
                end = dimensionResource(R.dimen.padding_small)
            )
    ) {
        Box (
            modifier = modifier
        ){
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
                    contentScale = ContentScale.Crop,
                    modifier = modifier.fillMaxWidth()
                        .height(dimensionResource(R.dimen.experience_photo_height))
                        .clickable { onExperienceClick() }
                )
            }

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
               text = if(experience.viewsNo > 1000) "${experience.viewsNo/1000}k" else experience.viewsNo.toString(),
                color = Color.White,
                modifier = modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        start = dimensionResource(R.dimen.padding_extra_large),
                        bottom = dimensionResource(R.dimen.padding_extra_small),
                        )
            )
        }

        Row (
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween

        ){
            Text(experience.title,
                style = MaterialTheme.typography.displayMediumEmphasized,
                modifier = modifier
                    .padding(top = dimensionResource(R.dimen.padding_medium))
            )

            Row (
                modifier = modifier
            ) {
                Text(text = if(experience.likesNo > 1000) "${experience.likesNo/1000}k" else experience.likesNo.toString(),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = modifier
                        .padding(top = dimensionResource(R.dimen.padding_medium),
                            end = dimensionResource(R.dimen.padding_small))
                )

                IconButton(
                    modifier = modifier,
                    onClick = { onLikeClick(experience.id) }
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Bottom Left Icon",
                        tint = Color.Black,
                        modifier = modifier
                            .padding(top = dimensionResource(R.dimen.padding_small))
                    )
                }

            }


        }



    }



}


