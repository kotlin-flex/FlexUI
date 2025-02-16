package cn.vividcode.multiplatform.flex.ui.sample.util

import android.content.ClipData
import androidx.compose.ui.platform.Clipboard

actual suspend fun Clipboard.setText(text: String) {
	this.nativeClipboard.setPrimaryClip(ClipData.newPlainText(null, text))
}

actual suspend fun Clipboard.getText(): String? {
	return this.nativeClipboard.primaryClip?.getItemAt(0)?.text?.toString()
}