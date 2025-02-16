package cn.vividcode.multiplatform.flex.ui.sample.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ColumnScope.AdaptiveLayout(
	code: @Composable BoxScope.() -> Unit,
	preview: @Composable BoxScope.() -> Unit,
	options: @Composable OptionsScope.() -> Unit,
) {
	var width by remember { mutableStateOf(Dp.Unspecified) }
	val density = LocalDensity.current
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.weight(1f)
			.onGloballyPositioned {
				width = with(density) { it.size.width.toDp() }
			}
			.padding(vertical = 12.dp)
	) {
		if (width == Dp.Unspecified) return@Box
		val isVertical by remember(width) {
			derivedStateOf { width >= 600.dp }
		}
		if (isVertical) {
			Row(
				modifier = Modifier
					.fillMaxSize()
			) {
				Box(
					modifier = Modifier
						.weight(1f)
						.fillMaxHeight()
						.padding(12.dp),
					contentAlignment = Alignment.Center
				) {
					preview()
				}
				Spacer(modifier = Modifier.width(12.dp))
				Box(
					modifier = Modifier
						.weight(0.8f)
						.fillMaxHeight()
				) {
					code()
				}
			}
		} else {
			Column(
				modifier = Modifier
					.fillMaxSize()
			) {
				Box(
					modifier = Modifier
						.fillMaxWidth()
						.weight(1f)
				) {
					code()
				}
				Spacer(modifier = Modifier.height(12.dp))
				Box(
					modifier = Modifier
						.fillMaxWidth()
						.weight(0.6f)
						.padding(12.dp),
					contentAlignment = Alignment.Center
				) {
					preview()
				}
			}
		}
	}
	HorizontalDivider()
	val verticalScrollState = rememberScrollState()
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.height(252.dp)
			.padding(vertical = 4.dp)
			.verticalScroll(verticalScrollState)
			.padding(vertical = 2.dp)
	) {
		with(OptionsScopeImpl) {
			options()
		}
	}
}

interface OptionsScope {
	
	@Composable
	fun item(
		title: String,
		content: @Composable RowScope.() -> Unit,
	)
}

private object OptionsScopeImpl : OptionsScope {
	
	@Composable
	override fun item(
		title: String,
		content: @Composable RowScope.() -> Unit,
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(vertical = 6.dp),
			verticalAlignment = Alignment.CenterVertically,
		) {
			Text(
				text = title,
				style = MaterialTheme.typography.labelMedium,
			)
			Spacer(modifier = Modifier.width(12.dp))
			Spacer(modifier = Modifier.weight(1f))
			content()
		}
	}
}