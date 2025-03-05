package com.example.aroundegyptmini.ui.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.aroundegyptmini.model.Experience

@Composable
fun SearchExperiences(
    searchExperiences: List<Experience>,
    onExperienceClick: (Experience) -> Unit,
    onLikeClick: (String) -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier
    ) {
        for (experience in searchExperiences) {
            ExperienceItem(experience,
                modifier = modifier,
                onLikeClick = onLikeClick,
                onExperienceClick = { onExperienceClick(experience) })

        }
    }
}