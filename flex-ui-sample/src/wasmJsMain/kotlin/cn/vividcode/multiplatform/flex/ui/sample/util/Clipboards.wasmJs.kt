package cn.vividcode.multiplatform.flex.ui.sample.util

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard
import kotlinx.coroutines.await
import org.w3c.files.Blob
import kotlin.js.Promise

private const val MIME_TYPE_PLAIN_TEXT = "text/plain"
private val MIME_TYPE_PLAIN_TEXT_JS = MIME_TYPE_PLAIN_TEXT.toJsString()

actual suspend fun Clipboard.setText(text: String) {
	this.setClipEntry(ClipEntry.withPlainText(text))
}

@OptIn(ExperimentalComposeUiApi::class)
actual suspend fun Clipboard.getText(): String? {
	val clipEntry = this.getClipEntry() ?: return null
	if (!clipEntry.hasText()) return null
	val blob = clipEntry.clipboardItems[0]!!.getType(MIME_TYPE_PLAIN_TEXT_JS).await<Blob>()
	return getTextFromBlob(blob).await<JsString>().toString()
}

@OptIn(ExperimentalComposeUiApi::class)
private fun ClipEntry?.hasText(): Boolean {
	if (this == null) return false
	if (this.clipboardItems.length == 0) return false
	return includes(this.clipboardItems[0]!!.types, MIME_TYPE_PLAIN_TEXT_JS)
}

private fun includes(jsArray: JsArray<*>, value: JsAny): Boolean =
	js("jsArray.includes(value)")

private fun getTextFromBlob(blob: Blob): Promise<JsString> =
	js("blob.text()")