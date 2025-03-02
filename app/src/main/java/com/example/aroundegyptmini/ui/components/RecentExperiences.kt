package com.example.aroundegyptmini.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.aroundegyptmini.R
import com.example.aroundegyptmini.model.Experience

@Composable
fun RecentExperiences(
    recentExperiences: List<Experience>,
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier
    ){
        Text(stringResource(R.string.most_recent))
        LazyColumn (
            modifier = modifier
        ){
            items(recentExperiences){ experience ->
                ExperienceItem(experience)
            }
        }
    }

}