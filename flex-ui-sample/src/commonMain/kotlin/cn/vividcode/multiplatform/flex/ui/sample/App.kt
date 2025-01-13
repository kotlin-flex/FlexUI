package cn.vividcode.multiplatform.flex.ui.sample

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.foundation.ButtonIconDirection
import cn.vividcode.multiplatform.flex.ui.foundation.ButtonType
import cn.vividcode.multiplatform.flex.ui.foundation.FlexButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
	Scaffold(
		modifier = Modifier.fillMaxSize(),
		topBar = {
			TopAppBar(
				title = {},
				actions = {
					FlexButton(
						icon = Icons.Outlined.Settings
					) {
					
					}
				}
			)
		}
	) {
		val verticalScrollState = rememberScrollState()
		Column(
			modifier = Modifier
				.padding(it)
				.fillMaxSize()
				.verticalScroll(verticalScrollState)
				.padding(vertical = 16.dp)
		) {
			FlexColorType.entries.forEach { colorType ->
				FlexSizeType.entries.forEach { sizeType ->
					Spacer(modifier = Modifier.height(16.dp))
					val scrollState = rememberScrollState()
					Row(
						modifier = Modifier
							.fillMaxWidth()
							.horizontalScroll(scrollState)
							.padding(horizontal = 16.dp)
					) {
						ButtonType.entries.forEachIndexed { index, buttonType ->
							if (index != 0) {
								Spacer(modifier = Modifier.width(16.dp))
							}
							FlexButton(
								text = "$colorType $buttonType",
								sizeType = sizeType,
								colorType = colorType,
								buttonType = buttonType,
								icon = if (index % 2 == 0) Icons.Outlined.Search else null,
								iconDirection = if (index % 3 == 0) ButtonIconDirection.Start else ButtonIconDirection.End
							) {
							
							}
						}
					}
				}
			}
		}
	}
}