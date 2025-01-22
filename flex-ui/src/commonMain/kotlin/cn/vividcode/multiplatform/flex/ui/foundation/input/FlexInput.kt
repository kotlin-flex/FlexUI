package cn.vividcode.multiplatform.flex.ui.foundation.input

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme

/**
 * Flex 输入框
 */
@Composable
fun FlexInput(
	value: String,
	onValueChange: (String) -> Unit,
	sizeType: FlexSizeType = FlexInputs.DefaultSizeType,
	colorType: FlexColorType = FlexInputs.DefaultColorType,
	cornerType: FlexCornerType = FlexInputs.DefaultCornerType,
	prefix: (@Composable () -> Unit)? = null,
	suffix: (@Composable () -> Unit)? = null,
) {
	val current = LocalFlexConfig.current
	val config = current.input.getConfig(sizeType)
	val darkTheme = LocalDarkTheme.current
	val textStyle by remember(
		darkTheme,
		config
	) {
		mutableStateOf(
			TextStyle(
				fontSize = config.fontSize,
				fontWeight = config.fontWeight,
				letterSpacing = config.letterSpacing,
				color = if (darkTheme) Color.LightGray else Color.DarkGray
			)
		)
	}
	Row(
		modifier = Modifier
			.height(config.height)
			.border(
				width = config.borderWidth,
				color = Color.Gray.copy(alpha = 0.3f),
				shape = CircleShape
			)
			.padding(
				horizontal = config.horizontalPadding
			),
		verticalAlignment = Alignment.CenterVertically
	) {
		BasicTextField(
			value = value,
			onValueChange = onValueChange,
			textStyle = textStyle
		)
	}
}

object FlexInputs {
	
	val DefaultSizeType: FlexSizeType
		@Composable
		get() = LocalFlexConfig.current.default.let {
			it.input?.sizeType ?: it.common.sizeType ?: FlexSizeType.Medium
		}
	
	val DefaultColorType: FlexColorType
		@Composable
		get() = LocalFlexConfig.current.default.let {
			it.input?.colorType ?: it.common.colorType ?: FlexColorType.Default
		}
	
	val DefaultCornerType: FlexCornerType
		@Composable
		get() = LocalFlexConfig.current.default.let {
			it.input?.cornerType ?: it.common.cornerType ?: FlexCornerType.Default
		}
}