package cn.vividcode.multiplatform.flex.ui.sample.util

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.awtClipboard
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection

@OptIn(ExperimentalComposeUiApi::class)
actual suspend fun Clipboard.setText(text: String) {
	this.awtClipboard?.setContents(StringSelection(text), null)
}

@OptIn(ExperimentalComposeUiApi::class)
actual suspend fun Clipboard.getText(): String? {
	return this.awtClipboard?.getData(DataFlavor.stringFlavor) as? String
}