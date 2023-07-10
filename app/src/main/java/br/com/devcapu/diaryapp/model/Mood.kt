package br.com.devcapu.diaryapp.model

import androidx.compose.ui.graphics.Color
import br.com.devcapu.diaryapp.R
import br.com.devcapu.diaryapp.ui.theme.AngryColor
import br.com.devcapu.diaryapp.ui.theme.AwfulColor
import br.com.devcapu.diaryapp.ui.theme.BoredColor
import br.com.devcapu.diaryapp.ui.theme.CalmColor
import br.com.devcapu.diaryapp.ui.theme.DepressedColor
import br.com.devcapu.diaryapp.ui.theme.DisappointedColor
import br.com.devcapu.diaryapp.ui.theme.HappyColor
import br.com.devcapu.diaryapp.ui.theme.HumorousColor
import br.com.devcapu.diaryapp.ui.theme.LonelyColor
import br.com.devcapu.diaryapp.ui.theme.MysteriousColor
import br.com.devcapu.diaryapp.ui.theme.NeutralColor
import br.com.devcapu.diaryapp.ui.theme.RomanticColor
import br.com.devcapu.diaryapp.ui.theme.ShamefulColor
import br.com.devcapu.diaryapp.ui.theme.SurprisedColor
import br.com.devcapu.diaryapp.ui.theme.SuspiciousColor
import br.com.devcapu.diaryapp.ui.theme.TenseColor

enum class Mood(
    val icon: Int,
    val contentColor: Color,
    val containerColor: Color,
) {
    Neutral(
        icon = R.drawable.neutral,
        contentColor = Color.Black,
        containerColor = NeutralColor
    ),
    Happy(
        icon = R.drawable.happy,
        contentColor = Color.Black,
        containerColor = HappyColor
    ),
    Angry(
        icon = R.drawable.angry,
        contentColor = Color.White,
        containerColor = AngryColor
    ),
    Bored(
        icon = R.drawable.bored,
        contentColor = Color.Black,
        containerColor = BoredColor
    ),
    Calm(
        icon = R.drawable.calm,
        contentColor = Color.Black,
        containerColor = CalmColor
    ),
    Depressed(
        icon = R.drawable.depressed,
        contentColor = Color.Black,
        containerColor = DepressedColor
    ),
    Disappointed(
        icon = R.drawable.disappointed,
        contentColor = Color.White,
        containerColor = DisappointedColor
    ),
    Humorous(
        icon = R.drawable.humorous,
        contentColor = Color.Black,
        containerColor = HumorousColor
    ),
    Lonely(
        icon = R.drawable.lonely,
        contentColor = Color.White,
        containerColor = LonelyColor
    ),
    Mysterious(
        icon = R.drawable.mysterious,
        contentColor = Color.Black,
        containerColor = MysteriousColor
    ),
    Romantic(
        icon = R.drawable.romantic,
        contentColor = Color.White,
        containerColor = RomanticColor
    ),
    Shameful(
        icon = R.drawable.shameful,
        contentColor = Color.White,
        containerColor = ShamefulColor
    ),
    Awful(
        icon = R.drawable.awful,
        contentColor = Color.Black,
        containerColor = AwfulColor
    ),
    Surprised(
        icon = R.drawable.surprised,
        contentColor = Color.Black,
        containerColor = SurprisedColor
    ),
    Suspicious(
        icon = R.drawable.suspicious,
        contentColor = Color.Black,
        containerColor = SuspiciousColor
    ),
    Tense(
        icon = R.drawable.tense,
        contentColor = Color.Black,
        containerColor = TenseColor
    )
}