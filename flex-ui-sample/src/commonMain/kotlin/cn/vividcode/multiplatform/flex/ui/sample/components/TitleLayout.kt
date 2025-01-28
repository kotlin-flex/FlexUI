package cn.vividcode.multiplatform.flex.ui.sample.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TitleLayout(
	modifier: Modifier = Modifier,
	title: @Composable () -> Unit,
	content: @Composable RowScope.() -> Unit,
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.then(modifier),
		verticalAlignment = Alignment.CenterVertically,
	) {
		CompositionLocalProvider(
			LocalTextStyle provides MaterialTheme.typography.titleMedium
		) {
			Box(
				modifier = Modifier.weight(1f)
			) {
				title()
			}
		}
		content()
	}
}