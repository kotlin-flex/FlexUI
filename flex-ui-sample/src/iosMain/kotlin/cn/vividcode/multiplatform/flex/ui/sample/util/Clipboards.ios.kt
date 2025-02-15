package cn.vividcode.multiplatform.flex.ui.sample.util

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard

@OptIn(ExperimentalComposeUiApi::class)
actual suspend fun Clipboard.setText(text: String) {
	this.setClipEntry(ClipEntry.withPlainText(text))
}

@OptIn(ExperimentalComposeUiApi::class)
actual suspend fun Clipboard.getText(): String? {
	return this.getClipEntry()?.getPlainText()
}