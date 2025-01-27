package cn.vividcode.multiplatform.flex.ui.foundation.input

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType

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
	prefix: FlexInputIcon? = null,
	suffix: FlexInputIcon? = null,
) {
	val current = LocalFlexConfig.current
	val config = current.input.getConfig(sizeType)
	val targetColor = current.theme.colorScheme.current.getColor(colorType)
	val height by animateDpAsState(config.height)
	val borderWidth by animateDpAsState(config.borderWidth)
	val fontSize by animateFloatAsState(config.fontSize.value)
	val letterSpacing by animateFloatAsState(config.letterSpacing.value)
	val horizontalPadding by animateDpAsState(config.horizontalPadding)
	val color by animateColorAsState(targetColor)
	val corner by animateDpAsState(height * cornerType.percent)
	val cornerShape by remember(corner) {
		derivedStateOf {
			RoundedCornerShape(corner)
		}
	}
	Row(
		modifier = Modifier
			.height(height)
			.border(
				width = borderWidth,
				color = color,
				shape = cornerShape
			)
			.padding(horizontal = horizontalPadding / 2)
			.then(modifier),
		verticalAlignment = Alignment.CenterVertically
	) {
		if (prefix != null) {
			Icon(
				imageVector = prefix.icon,
				contentDescription = null,
				modifier = Modifier
					.size(config.iconSize),
				tint = color
			)
		}
		Spacer(modifier = Modifier.width(horizontalPadding / 2))
		BasicTextField(
			value = value,
			onValueChange = onValueChange,
			modifier = Modifier,
			textStyle = TextStyle(
				fontSize = fontSize.sp,
				fontWeight = config.fontWeight,
				letterSpacing = if (letterSpacing >= 0f) letterSpacing.sp else TextUnit.Unspecified,
				color = color
			),
			singleLine = true,
			cursorBrush = SolidColor(targetColor),
		)
		Spacer(modifier = Modifier.width(horizontalPadding / 2))
		if (suffix != null) {
			Icon(
				imageVector = suffix.icon,
				contentDescription = null,
				modifier = Modifier
					.size(config.iconSize),
				tint = color
			)
		}
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
	
	/**
	 * 按钮
	 */
	fun icon(
		icon: ImageVector,
		rotation: Float = 0f,
		onClick: (() -> Unit)? = null,
	): FlexInputIcon = FlexInputIcon(icon, rotation, onClick)
}

class FlexInputIcon internal constructor(
	val icon: ImageVector,
	val rotation: Float,
	val onClick: (() -> Unit)?,
)