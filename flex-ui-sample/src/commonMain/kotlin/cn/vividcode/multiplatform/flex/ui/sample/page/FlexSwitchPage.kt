package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.sample.components.Code

/**
 * 按钮展示页
 */
@Composable
fun ColumnScope.FlexSwitchPage() {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.weight(1f)
			.padding(12.dp),
	) {
		Box(
			modifier = Modifier
				.weight(1f)
				.fillMaxHeight(),
			contentAlignment = Alignment.Center
		) {
//			FlexButton(
//				text = buttonText,
//				icon = iconType.icon,
//				sizeType = sizeType,
//				colorType = colorType,
//				cornerType = cornerType,
//				buttonType = buttonType,
//				iconPosition = iconPosition,
//				iconRotation = 0f,
//				scaleEffect = scaleEffect,
//				enabled = enabled
//			)
		}
		Spacer(Modifier.width(12.dp))
		val code = """
		
		""".trimIndent()
		Code(code)
	}
	HorizontalDivider()
	val verticalScrollState = rememberScrollState()
	Column(
		modifier = Modifier
			.height(276.dp)
			.padding(4.dp)
			.verticalScroll(verticalScrollState)
			.padding(8.dp)
	) {
	
	}
}