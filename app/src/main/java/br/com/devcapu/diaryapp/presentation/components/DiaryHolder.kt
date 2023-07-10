package br.com.devcapu.diaryapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.devcapu.diaryapp.model.Diary
import br.com.devcapu.diaryapp.model.Mood
import br.com.devcapu.diaryapp.ui.theme.Elevation
import br.com.devcapu.diaryapp.util.toInstant
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

@Composable
fun DiaryHolder(diary: Diary, onClick: (String) -> Unit) {
    val localDensity = LocalDensity.current
    var componentHeight by remember { mutableStateOf(0.dp) }
    Row(
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember {
                MutableInteractionSource()
            }
        ) { onClick(diary._id.toString()) }
    ) {
        Spacer(Modifier.width(16.dp))
        Surface(
            Modifier
                .width(2.dp)
                .height(componentHeight + 16.dp),
            tonalElevation = Elevation.Level1
        ) {}
        Spacer(Modifier.width(16.dp))
        Surface(
            modifier = Modifier
                .clip(shape = Shapes().medium)
                .onGloballyPositioned {
                    componentHeight = with(localDensity) { it.size.height.toDp() }
                },
            tonalElevation = Elevation.Level1
        ) {
            Column(Modifier.fillMaxWidth()) {
                DiaryHeader(moodName = diary.mood, time = diary.date.toInstant())
                Text(
                    modifier = Modifier.padding(14.dp),
                    text = diary.description,
                    maxLines = 5,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
            }
        }
    }
}

@Composable
fun DiaryHeader(moodName: String, time: Instant) {
    val mood by remember { mutableStateOf(Mood.valueOf(moodName)) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(mood.containerColor)
            .padding(horizontal = 14.dp, vertical = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier.size(18.dp), painter = painterResource(
                    id = mood.icon
                ),
                contentDescription = "Mood icon"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = mood.name,
                color = mood.contentColor,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )
        }
        Text(
            text = SimpleDateFormat("hh:mm a", Locale.US).format(Date.from(time)),
            color = mood.contentColor,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize
        )
    }
}

@Preview
@Composable
fun HolderPreview() {
    DiaryHolder(diary = Diary().apply {
        title = "My diary"
        description = "lorem ipsum dolor sit amet."
        mood = Mood.Happy.name
    }, onClick = { })
}