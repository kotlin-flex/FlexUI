package cn.vividcode.multiplatform.flex.ui.sample.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TitleLayout(
	title: String,
	content: @Composable RowScope.() -> Unit,
) {
	Row(
		modifier = Modifier
			.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically,
	) {
		Text(
			text = title,
			style = MaterialTheme.typography.titleSmall
		)
		Spacer(modifier = Modifier.width(24.dp))
		Spacer(modifier = Modifier.weight(1f))
		content()
	}
}