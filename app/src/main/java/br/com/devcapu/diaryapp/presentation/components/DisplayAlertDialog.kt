package br.com.devcapu.diaryapp.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    dialogOpened: Boolean,
    onCloseDialog: () -> Unit,
    onYesClick: () -> Unit
) {
    if (dialogOpened) {
        AlertDialog(
            onDismissRequest = onCloseDialog,
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onYesClick()
                        onCloseDialog()
                    }
                ) {
                    Text(
                        text = "Yes"
                    )
                }
            },
            dismissButton = {
                OutlinedButton(onClick = onCloseDialog) {
                    Text("No")
                }
            }
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DisplayAlertDialogPreview() {
    DisplayAlertDialog(
        title = "Título",
        message = "Mensagem",
        dialogOpened = true,
        onCloseDialog = { }
    ) { }
}