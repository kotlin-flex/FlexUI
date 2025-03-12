package cn.vividcode.multiplatform.flex.ui.foundation.select

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.common.FlexOption
import cn.vividcode.multiplatform.flex.ui.config.FlexComposeDefaultConfig
import cn.vividcode.multiplatform.flex.ui.config.FlexDefaults
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.foundation.FlexSelectConfig
import cn.vividcode.multiplatform.flex.ui.graphics.FlexBrush
import cn.vividcode.multiplatform.flex.ui.layout.popup.FlexAnimatedPopup
import cn.vividcode.multiplatform.flex.ui.type.FlexBrushType
import cn.vividcode.multiplatform.flex.ui.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.utils.animateFlexBrushAsState
import cn.vividcode.multiplatform.flex.ui.utils.background
import cn.vividcode.multiplatform.flex.ui.utils.border
import cn.vividcode.multiplatform.flex.ui.utils.toSolidColor

/**
 * FlexSelect 下拉选择框
 */
@Composable
fun <Key> FlexSelect(
	selectedKey: Key,
	onSelectedKeyChanged: (Key) -> Unit,
	options: List<FlexOption<Key & Any>>,
	modifier: Modifier = Modifier,
	sizeType: FlexSizeType = FlexSelectDefaults.DefaultSizeType,
	brushType: FlexBrushType = FlexSelectDefaults.DefaultBrushType,
	cornerType: FlexCornerType = FlexSelectDefaults.DefaultCornerType,
	selectType: FlexSelectType = FlexSelectDefaults.DefaultSelectType,
	enabled: Boolean = true
) {
	FlexSelectImpl(
		selectedKeys = listOfNotNull(selectedKey),
		onSelectedKeysChanged = {
			onSelectedKeyChanged(if (it.isEmpty()) null as Key else it.first())
		},
		options = options,
		multiple = false,
		modifier = modifier,
		sizeType = sizeType,
		brushType = brushType,
		cornerType = cornerType,
		selectType = selectType,
		enabled = enabled
	)
}

@Composable
fun <Key : Any> FlexSelect(
	selectedKeys: List<Key>,
	onSelectedKeysChanged: (List<Key>) -> Unit,
	options: List<FlexOption<Key>>,
	modifier: Modifier = Modifier,
	sizeType: FlexSizeType = FlexSelectDefaults.DefaultSizeType,
	brushType: FlexBrushType = FlexSelectDefaults.DefaultBrushType,
	cornerType: FlexCornerType = FlexSelectDefaults.DefaultCornerType,
	selectType: FlexSelectType = FlexSelectDefaults.DefaultSelectType,
	enabled: Boolean = true
) {
	FlexSelectImpl(
		selectedKeys = selectedKeys,
		onSelectedKeysChanged = onSelectedKeysChanged,
		options = options,
		multiple = true,
		modifier = modifier,
		sizeType = sizeType,
		brushType = brushType,
		cornerType = cornerType,
		selectType = selectType,
		enabled = enabled
	)
}

@Composable
private fun <Key : Any> FlexSelectImpl(
	selectedKeys: List<Key>,
	onSelectedKeysChanged: (List<Key>) -> Unit,
	options: List<FlexOption<Key>>,
	multiple: Boolean,
	modifier: Modifier = Modifier,
	sizeType: FlexSizeType = FlexSelectDefaults.DefaultSizeType,
	brushType: FlexBrushType = FlexSelectDefaults.DefaultBrushType,
	cornerType: FlexCornerType = FlexSelectDefaults.DefaultCornerType,
	selectType: FlexSelectType = FlexSelectDefaults.DefaultSelectType,
	enabled: Boolean = true,
) {
	val config = LocalFlexConfig.current.select.getConfig(sizeType)
	val minWidth by animateDpAsState(config.minWidth)
	val height by animateDpAsState(config.height)
	val corner by animateDpAsState(config.height * cornerType.scale)
	val shape by remember(corner) {
		derivedStateOf {
			RoundedCornerShape(corner)
		}
	}
	var isPopupVisible by remember { mutableStateOf(false) }
	val borderWidth by animateDpAsState(config.borderWidth)
	val borderBrush by animateFlexBrushAsState(
		targetValue = if (isPopupVisible) brushType.brush else MaterialTheme.colorScheme.outlineVariant.toSolidColor()
	)
	var size by remember { mutableStateOf(IntSize.Zero) }
	val horizontalPadding by animateDpAsState(config.horizontalPadding)
	val interactionSource = remember { MutableInteractionSource() }
	Row(
		modifier = Modifier
			.widthIn(min = minWidth)
			.height(height)
			.then(modifier)
			.onGloballyPositioned {
				size = it.size
			}
			.clip(shape)
			.clickable(
				interactionSource = interactionSource,
				indication = null,
				onClick = {}
			)
			.border(
				width = borderWidth,
				brush = borderBrush,
				shape = shape
			)
			.padding(horizontal = horizontalPadding)
	) {
		if (multiple) {
			FlexSelectedOptions()
		}
		
		val isPressed by interactionSource.collectIsPressedAsState()
		var isIdle by remember { mutableStateOf(true) }
		LaunchedEffect(isPressed) {
			if (isPressed && isIdle) {
				isPopupVisible = true
			}
		}
		val intervalWithPopup by animateDpAsState(config.intervalWithPopup)
		val density = LocalDensity.current
		FlexSelectPopup(
			isPopupVisible = isPopupVisible,
			onDismissRequest = {
				if (isIdle) {
					isPopupVisible = false
				}
			},
			width = with(density) { size.width.toDp() },
			offset = with(density) {
				IntOffset(
					x = -horizontalPadding.roundToPx(),
					y = size.height + intervalWithPopup.roundToPx()
				)
			},
			onIdleChanged = { isIdle = it },
			config = config,
			selectedKeys = selectedKeys,
			onSelectedKeysChanged = onSelectedKeysChanged,
			options = options,
			brushType = brushType,
			cornerType = cornerType,
			multiple = multiple
		)
	}
}

object FlexSelectDefaults : FlexDefaults() {
	
	override val FlexComposeDefaultConfig.defaultConfig
		get() = this.select
	
	val DefaultSelectType = FlexSelectType.Default
}

enum class FlexSelectType {
	
	Default,
	
	Search
}

@Composable
private fun <Key> FlexSelectPopup(
	isPopupVisible: Boolean,
	onDismissRequest: () -> Unit,
	offset: IntOffset,
	width: Dp,
	onIdleChanged: (idle: Boolean) -> Unit,
	config: FlexSelectConfig,
	selectedKeys: List<Key>,
	onSelectedKeysChanged: (List<Key>) -> Unit,
	options: List<FlexOption<Key & Any>>,
	brushType: FlexBrushType,
	cornerType: FlexCornerType,
	multiple: Boolean
) {
	val corner by animateDpAsState(config.height * cornerType.scale)
	val shape by remember(corner) {
		derivedStateOf { RoundedCornerShape(corner) }
	}
	FlexAnimatedPopup(
		visible = isPopupVisible,
		offset = offset,
		onDismissRequest = onDismissRequest,
		enter = expandVertically(animationSpec = spring()),
		exit = shrinkVertically(animationSpec = spring()),
		shape = shape,
		onIdleChanged = onIdleChanged,
	) {
		FlexSelectOptionList(
			selectedKeys = selectedKeys,
			onSelectedKeysChanged = {
				if (!multiple) {
					onDismissRequest()
				}
				onSelectedKeysChanged(it)
			},
			options = options,
			config = config,
			brushType = brushType,
			width = width,
			shape = shape,
			multiple = multiple
		)
	}
}

@Composable
private fun FlexSelectedOptions(

) {

}

@Composable
private fun <Key> FlexSelectOptionList(
	selectedKeys: List<Key>,
	onSelectedKeysChanged: (List<Key>) -> Unit,
	options: List<FlexOption<Key & Any>>,
	config: FlexSelectConfig,
	brushType: FlexBrushType,
	width: Dp,
	shape: Shape,
	multiple: Boolean
) {
	val paddingWithPopup by animateDpAsState(config.paddingWithPopup)
	val maxHeightWithPopup by animateDpAsState(config.maxHeightWithPopup)
	val verticalScrollState = rememberScrollState()
	Column(
		modifier = Modifier
			.width(width)
			.then(
				if (options.isEmpty()) {
					val heightWhenOptionEmptyWithPopup by animateDpAsState(config.heightWhenOptionEmptyWithPopup)
					Modifier.height(heightWhenOptionEmptyWithPopup)
				} else {
					Modifier.heightIn(
						max = maxHeightWithPopup
					)
				}
			)
			.background(MaterialTheme.colorScheme.surface)
			.verticalScroll(verticalScrollState)
			.padding(paddingWithPopup)
	) {
		val fontSize by animateFloatAsState(config.fontSize.value)
		val letterSpacing by animateFloatAsState(config.letterSpacing.value)
		val heightWithPopup by animateDpAsState(config.heightWithPopup)
		val brush by animateFlexBrushAsState(brushType.brush)
		val brushContainer by animateFlexBrushAsState(brushType.brushContainer)
		val onBrushContainer by animateFlexBrushAsState(brushType.onBrushContainer)
		options.forEach {
			val selected = it.key in selectedKeys
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.height(heightWithPopup)
					.clip(shape)
					.background(
						brush = if (selected) brushContainer else FlexBrush.Transparent,
						shape = shape
					)
					.clickable(
						enabled = it.enabled
					) {
						if (multiple) {
							val newSelectedKeys = if (selected) selectedKeys - it.key else selectedKeys + it.key
							onSelectedKeysChanged(newSelectedKeys)
						} else {
							onSelectedKeysChanged(listOf(it.key))
						}
					}
					.padding(horizontal = paddingWithPopup),
				contentAlignment = Alignment.CenterStart
			) {
				Text(
					text = it.value,
					fontSize = fontSize.sp,
					fontWeight = config.fontWeight,
					letterSpacing = if (letterSpacing >= 0f) letterSpacing.sp else TextUnit.Unspecified,
					style = LocalTextStyle.current.copy(
						brush = (if (selected) onBrushContainer else brush).original
					)
				)
			}
		}
	}
}