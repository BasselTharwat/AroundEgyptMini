package com.example.aroundegyptmini.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.aroundegyptmini.R
import com.example.aroundegyptmini.model.Experience

@Composable
fun ExperienceScreen(experience: Experience,
                     onLikeClick: () -> Unit,
                     modifier: Modifier = Modifier) {
    Column (modifier) {
        ExperienceItemCard(experience = experience,
            modifier = modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.experience_photo_details_height)))
        ExperienceTitle(experience = experience,
            onLikeClick = onLikeClick,
            modifier = modifier)
        HorizontalDivider(modifier = modifier
            .padding(top = dimensionResource(R.dimen.padding_medium),
                bottom = dimensionResource(R.dimen.padding_medium),
                start = dimensionResource(R.dimen.padding_small),
                end = dimensionResource(R.dimen.padding_small)
            ),
            thickness = dimensionResource(R.dimen.line_thickness)
        )
        Text(stringResource(R.string.description),
            style = MaterialTheme.typography.displayLarge,
            modifier = modifier
                .padding(dimensionResource(R.dimen.padding_medium))
        )
        Text(experience.description,
            style = MaterialTheme.typography.displayMedium,
            modifier = modifier
                .padding(dimensionResource(R.dimen.padding_medium)))
    }


}


@Composable
fun ExperienceItemCard(experience: Experience,
                       modifier: Modifier = Modifier){
    Box (
        modifier = modifier,
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
                modifier = Modifier.fillMaxWidth()
            )
        }

        Icon(
            imageVector = Icons.Default.PhotoLibrary,
            contentDescription = "Bottom Right Icon",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(dimensionResource(R.dimen.padding_medium))
        )

        Icon(
            imageVector = Icons.Default.RemoveRedEye,
            contentDescription = "Bottom Left Icon",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ExperienceTitle(
    experience: Experience,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Row (modifier = modifier
        .fillMaxWidth()
        .padding(dimensionResource(R.dimen.padding_small)),
        horizontalArrangement = Arrangement.SpaceBetween
        ) {
        Column (modifier = modifier){
            Text(experience.title,
                style = MaterialTheme.typography.displayMediumEmphasized,
                modifier = modifier
                    .padding(top = dimensionResource(R.dimen.padding_medium))
                        )
            Text(experience.address,
                style = MaterialTheme.typography.displayMedium,
                modifier = modifier
                    .padding(
                        top = dimensionResource(R.dimen.padding_small)))
        }

        Icon(
            imageVector = Icons.Default.Upload,
            contentDescription = "Bottom Left Icon",
            tint = Color.Black,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
        )

        IconButton(
            onClick = onLikeClick,
            modifier = modifier
        ) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Bottom Left Icon",
                tint = Color.Black,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
            )
        }



    }

}


