package com.example.aroundegyptmini.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.aroundegyptmini.R
import com.example.aroundegyptmini.model.Experience

@Composable
fun RecommendedExperiences(
    recommendedExperiences: List<Experience>,
    onExperienceClick: (Experience) -> Unit,
    onLikeClick: (String) -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier
    ) {
        Text(stringResource(R.string.recommended_experiences),
            style = MaterialTheme.typography.displayLarge)
        LazyRow (
            modifier = modifier
            .fillMaxWidth()) {
            items(recommendedExperiences) { experience ->
                ExperienceItem(experience,
                    modifier = modifier,
                    onLikeClick = onLikeClick,
                    onExperienceClick = { onExperienceClick(experience) })
            }
        }
    }


}