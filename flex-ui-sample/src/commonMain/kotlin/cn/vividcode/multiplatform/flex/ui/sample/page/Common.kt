package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TitleRadio(
	title: String,
	content: @Composable () -> Unit,
) {
	Row(
		modifier = Modifier
			.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically,
	) {
		Text(
			text = title,
			modifier = Modifier.weight(1f),
			style = MaterialTheme.typography.titleMedium
		)
		content()
	}
}