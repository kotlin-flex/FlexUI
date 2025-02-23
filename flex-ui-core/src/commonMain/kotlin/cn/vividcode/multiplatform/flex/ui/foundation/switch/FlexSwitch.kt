package cn.vividcode.multiplatform.flex.ui.foundation.switch

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.FlexComposeDefaultConfig
import cn.vividcode.multiplatform.flex.ui.config.FlexDefaults
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.foundation.icon.FlexIcon
import cn.vividcode.multiplatform.flex.ui.graphics.FlexBrush
import cn.vividcode.multiplatform.flex.ui.type.*
import cn.vividcode.multiplatform.flex.ui.utils.*

@Composable
fun FlexSwitch(
	checked: Boolean,
	onCheckedChange: (Boolean) -> Unit,
	modifier: Modifier = Modifier,
	sizeType: FlexSizeType = FlexSwitchDefaults.DefaultSizeType,
	brushType: FlexBrushType = FlexSwitchDefaults.DefaultBrushType,
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
	
	val backgroundBrush by animateFlexBrushAsState(
		targetValue = if (checked) {
			when {
				!enabled -> brushType.disabledBrush
				isPressed -> brushType.darkenBrush
				isHovered -> brushType.lightenBrush
				else -> brushType.brush
			}
		} else {
			when {
				!enabled -> FlexBrush.Gray.disabledWithBrush
				isPressed -> FlexBrush.Gray.darkenWithBrush
				isHovered -> FlexBrush.Gray.lightenWithBrush
				else -> FlexBrush.Gray
			}
		}
	)
	
	Box(
		modifier = modifier
			.width(height * 2)
			.height(height)
			.clip(cornerShape)
			.background(
				brush = backgroundBrush,
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
		val brush by animateFlexBrushAsState(
			targetValue = run {
				val brush = if (checked) brushType.onBrush else FlexBrush.White
				if (enabled) brush else brush.disabledWithOnBrush
			}
		)
		Box(
			modifier = Modifier
				.offset(x = offsetX)
				.width(width)
				.height(height - padding * 2)
				.clip(checkBoxCornerShape)
				.background(
					brush = brush,
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
							fontSize = textSize.sp,
							lineHeight = textSize.sp,
							maxLines = 1,
							style = LocalTextStyle.current.copy(
								brush = brush.original
							)
						)
					}
					
					is FlexSwitchIconLabel -> {
						val iconSize by animateDpAsState(config.iconLabelSize)
						FlexIcon(
							imageVector = label.checked,
							contentDescription = null,
							modifier = Modifier.size(iconSize),
							tint = brush,
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
							fontSize = textSize.sp,
							lineHeight = textSize.sp,
							maxLines = 1,
							style = LocalTextStyle.current.copy(
								brush = brush.original
							)
						)
					}
					
					is FlexSwitchIconLabel -> {
						val iconSize by animateDpAsState(config.iconLabelSize)
						FlexIcon(
							imageVector = label.unchecked,
							contentDescription = null,
							modifier = Modifier.size(iconSize),
							tint = brush
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