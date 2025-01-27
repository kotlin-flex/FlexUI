package cn.vividcode.multiplatform.flex.ui.foundation.input

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
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
	modifier: Modifier = Modifier,
	sizeType: FlexSizeType = FlexInputs.DefaultSizeType,
	colorType: FlexColorType = FlexInputs.DefaultColorType,
	cornerType: FlexCornerType = FlexInputs.DefaultCornerType,
	prefix: (@Composable () -> Unit)? = null,
	suffix: (@Composable () -> Unit)? = null,
) {
	val current = LocalFlexConfig.current
	val config = current.input.getConfig(sizeType)
	val color = current.theme.colorScheme.current.getColor(colorType)
	val darkTheme = LocalDarkTheme.current
	val height by animateDpAsState(config.height)
	val borderWidth by animateDpAsState(config.borderWidth)
	val fontSize by animateFloatAsState(config.fontSize.value)
	val letterSpacing by animateFloatAsState(config.letterSpacing.value)
	val fontColor by animateColorAsState(
		targetValue = if (darkTheme) Color.LightGray else Color.DarkGray
	)
	val borderColor by animateColorAsState(color)
	val corner by animateDpAsState(height * cornerType.percent)
	val cornerShape by remember(corner) {
		derivedStateOf {
			RoundedCornerShape(corner)
		}
	}
	Row(
		modifier = Modifier
			.heightIn(min = height)
			.border(
				width = borderWidth,
				color = borderColor,
				shape = cornerShape
			),
		verticalAlignment = Alignment.CenterVertically
	) {
		BasicTextField(
			value = value,
			onValueChange = onValueChange,
			modifier = Modifier
				.padding(
					horizontal = config.horizontalPadding
				)
				.then(modifier),
			textStyle = TextStyle(
				fontSize = fontSize.sp,
				fontWeight = config.fontWeight,
				letterSpacing = if (letterSpacing >= 0f) letterSpacing.sp else TextUnit.Unspecified,
				color = fontColor
			),
			cursorBrush = SolidColor(color),
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