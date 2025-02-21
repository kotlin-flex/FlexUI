package cn.vividcode.multiplatform.flex.ui.foundation.switch

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.type.*
import cn.vividcode.multiplatform.flex.ui.utils.darkenWithColor
import cn.vividcode.multiplatform.flex.ui.utils.disabledWithColor
import cn.vividcode.multiplatform.flex.ui.utils.disabledWithContent
import cn.vividcode.multiplatform.flex.ui.utils.lightenWithColor

@Composable
fun FlexSwitch(
	checked: Boolean,
	onCheckedChange: (Boolean) -> Unit,
	modifier: Modifier = Modifier,
	sizeType: FlexSizeType = FlexSwitchDefaults.DefaultSizeType,
	colorType: FlexColorType = FlexSwitchDefaults.DefaultColorType,
	cornerType: FlexCornerType = FlexSwitchDefaults.DefaultCornerType,
	label: FlexSwitchLabel? = null,
	enabled: Boolean = true,
) {
	val config = LocalFlexConfig.current.switch.getConfig(sizeType)
	val height by animateDpAsState(config.height)
	val padding by animateDpAsState(config.padding)
	val corner by animateDpAsState(config.height * cornerType.scale)
	val cornerShape by remember(corner) {
		derivedStateOf { RoundedCornerShape(corner) }
	}
	val interactionSource = remember { MutableInteractionSource() }
	val isHovered by interactionSource.collectIsHoveredAsState()
	val isPressed by interactionSource.collectIsPressedAsState()
	val backgroundColor by animateColorAsState(
		targetValue = run {
			val color = when {
				checked -> colorType.color
				else -> Color.Gray
			}
			when {
				!enabled -> color.disabledWithColor
				isPressed -> color.darkenWithColor
				isHovered -> color.lightenWithColor
				else -> color
			}
		}
	)
	Box(
		modifier = modifier
			.width(height * 2)
			.height(height)
			.clip(cornerShape)
			.background(
				color = backgroundColor,
				shape = cornerShape
			)
			.clickable(
				interactionSource = interactionSource,
				indication = null,
				enabled = enabled,
				onClick = { onCheckedChange(!checked) }
			)
			.padding(
				vertical = padding
			),
		contentAlignment = Alignment.CenterStart,
	) {
		val isPressed by interactionSource.collectIsPressedAsState()
		val offsetX by animateDpAsState(
			targetValue = when {
				!checked -> config.padding
				isPressed -> config.height - config.padding
				else -> config.height + config.padding
			}
		)
		val checkBoxCorner by animateDpAsState(
			targetValue = (config.height - config.padding * 2) * cornerType.scale
		)
		val checkBoxCornerShape by remember(checkBoxCorner) {
			derivedStateOf { RoundedCornerShape(checkBoxCorner) }
		}
		val width by animateDpAsState(
			targetValue = when (isPressed) {
				true -> config.height
				false -> config.height - config.padding * 2
			},
		)
		val contentColor by animateColorAsState(
			targetValue = run {
				val color = if (checked) colorType.onColor else Color.White
				if (enabled) color else color.disabledWithContent
			}
		)
		Box(
			modifier = Modifier
				.offset(x = offsetX)
				.width(width)
				.height(height - padding * 2)
				.clip(checkBoxCornerShape)
				.background(
					color = contentColor,
					shape = checkBoxCornerShape
				)
		)
		if (label != null) {
			val checkedOffsetX by animateDpAsState(
				targetValue = if (checked) config.padding else -(height - config.padding)
			)
			val checkedWidth by animateDpAsState(
				targetValue = if (isPressed) config.height - config.padding * 2 else config.height
			)
			Box(
				modifier = Modifier
					.offset(x = checkedOffsetX)
					.width(checkedWidth)
					.height(height - padding * 2),
				contentAlignment = Alignment.Center,
			) {
				when (label) {
					is FlexSwitchTextLabel -> {
						val textSize by animateFloatAsState(config.textLabelSize.value)
						Text(
							text = label.checked,
							color = contentColor,
							fontSize = textSize.sp,
							lineHeight = textSize.sp,
							maxLines = 1
						)
					}
					
					is FlexSwitchIconLabel -> {
						val iconSize by animateDpAsState(config.iconLabelSize)
						Icon(
							imageVector = label.checked,
							contentDescription = null,
							modifier = Modifier.size(iconSize),
							tint = contentColor,
						)
					}
				}
			}
			val uncheckedOffsetX by animateDpAsState(
				targetValue = when {
					checked -> config.height * 2
					isPressed -> config.height + config.padding
					else -> config.height - config.padding
				}
			)
			val uncheckedWidth by animateDpAsState(
				targetValue = if (isPressed) config.height - config.padding * 2 else config.height
			)
			Box(
				modifier = Modifier
					.offset(x = uncheckedOffsetX)
					.width(uncheckedWidth)
					.height(height - padding * 2),
				contentAlignment = Alignment.Center,
			) {
				when (label) {
					is FlexSwitchTextLabel -> {
						val textSize by animateFloatAsState(config.textLabelSize.value)
						Text(
							text = label.unchecked,
							color = contentColor,
							fontSize = textSize.sp,
							lineHeight = textSize.sp,
							maxLines = 1
						)
					}
					
					is FlexSwitchIconLabel -> {
						val iconSize by animateDpAsState(config.iconLabelSize)
						Icon(
							imageVector = label.unchecked,
							contentDescription = null,
							modifier = Modifier.size(iconSize),
							tint = contentColor
						)
					}
				}
			}
		}
	}
}

object FlexSwitchDefaults : FlexDefaults(
	defaultCornerType = FlexCornerType.Circle
) {
	
	override val FlexComposeDefaultConfig.defaultConfig
		get() = this.switch
}

sealed interface FlexSwitchLabel

object FlexSwitchLabels {
	
	val DefaultTextLabel = FlexSwitchTextLabel(
		checked = "On",
		unchecked = "Off"
	)
	
	val DefaultIconLabel = FlexSwitchIconLabel(
		checked = Icons.Rounded.Check,
		unchecked = Icons.Outlined.Close
	)
	
	fun textLabel(
		checked: String,
		unchecked: String,
	): FlexSwitchLabel = FlexSwitchTextLabel(checked, unchecked)
	
	fun iconLabel(
		checked: ImageVector,
		unchecked: ImageVector,
	): FlexSwitchLabel = FlexSwitchIconLabel(checked, unchecked)
}

class FlexSwitchTextLabel internal constructor(
	val checked: String,
	val unchecked: String,
) : FlexSwitchLabel

class FlexSwitchIconLabel internal constructor(
	val checked: ImageVector,
	val unchecked: ImageVector,
) : FlexSwitchLabel