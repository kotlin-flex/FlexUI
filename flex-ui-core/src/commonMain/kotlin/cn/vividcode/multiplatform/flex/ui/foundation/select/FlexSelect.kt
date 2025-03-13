package cn.vividcode.multiplatform.flex.ui.foundation.select

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.common.FlexOption
import cn.vividcode.multiplatform.flex.ui.config.FlexComposeDefaultConfig
import cn.vividcode.multiplatform.flex.ui.config.FlexDefaults
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.foundation.FlexSelectConfig
import cn.vividcode.multiplatform.flex.ui.foundation.icon.FlexIcon
import cn.vividcode.multiplatform.flex.ui.graphics.FlexBrush
import cn.vividcode.multiplatform.flex.ui.layout.popup.FlexAnimatedPopup
import cn.vividcode.multiplatform.flex.ui.type.FlexBrushType
import cn.vividcode.multiplatform.flex.ui.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.utils.animateFlexBrushAsState
import cn.vividcode.multiplatform.flex.ui.utils.animateTextUnitAsState
import cn.vividcode.multiplatform.flex.ui.utils.background
import cn.vividcode.multiplatform.flex.ui.utils.border
import cn.vividcode.multiplatform.flex.ui.utils.toSolidColor

/**
 * FlexSelect 下拉选择框
 */
@Composable
fun <Key : Any> FlexSelect(
	selectedKey: Key?,
	onSelectedKeyChanged: (Key?) -> Unit,
	options: List<FlexOption<Key>>,
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
			.padding(horizontal = horizontalPadding / 2)
	) {
		val density = LocalDensity.current
		if (multiple) {
			FlexSelectedOptions(
				selectedKeys = selectedKeys,
				options = options,
				config = config,
				horizontalPadding = horizontalPadding,
				maxWidth = remember(density, size, horizontalPadding) {
					with(density) {
						val width = size.width.dp + horizontalPadding * 2
						when (selectType) {
							FlexSelectType.Default -> width
							FlexSelectType.Search -> width / 2
						}
					}
				},
				brushType = brushType,
				cornerType = cornerType
			)
		}
		
		val isPressed by interactionSource.collectIsPressedAsState()
		var isIdle by remember { mutableStateOf(true) }
		LaunchedEffect(isPressed) {
			if (isPressed && isIdle) {
				isPopupVisible = true
			}
		}
		val intervalWithPopup by animateDpAsState(config.popupInterval)
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
					x = -(horizontalPadding / 2).roundToPx(),
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
		enter = DefaultEnterTransition,
		exit = DefaultExitTransition,
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
			corner = corner,
			multiple = multiple
		)
	}
}

private val DefaultEnterTransition = expandVertically(animationSpec = spring()) + fadeIn(animationSpec = spring(), initialAlpha = 0.8f)
private val DefaultExitTransition = shrinkVertically(animationSpec = spring()) + fadeOut(animationSpec = spring(), targetAlpha = 0.8f)

@Composable
private fun <Key : Any> FlexSelectedOptions(
	selectedKeys: List<Key>,
	options: List<FlexOption<Key>>,
	config: FlexSelectConfig,
	horizontalPadding: Dp,
	maxWidth: Dp,
	brushType: FlexBrushType,
	cornerType: FlexCornerType,
) {
	val horizontalScrollState = rememberScrollState()
	Row(
		modifier = Modifier
			.widthIn(max = maxWidth)
			.fillMaxHeight()
			.horizontalScroll(horizontalScrollState)
			.padding(horizontal = horizontalPadding / 2),
		verticalAlignment = Alignment.CenterVertically
	) {
		val tagHeight by animateDpAsState(config.tagHeight)
		val tagHorizontalPadding by animateDpAsState(config.tagHorizontalPadding)
		val tagFontSize by animateTextUnitAsState(config.tagFontSize)
		val tagLetterSpacing by animateTextUnitAsState(config.tagLetterSpacing)
		val tagInterval by animateDpAsState(config.tagInterval)
		val tagCorner by animateDpAsState(config.tagHeight * cornerType.scale)
		val tagShape by remember(tagCorner) {
			derivedStateOf { RoundedCornerShape(tagCorner) }
		}
		val tagIconSize by animateDpAsState(config.tagIconSize)
		val brushContainer by animateFlexBrushAsState(brushType.brushContainer)
		val onBrushContainer by animateFlexBrushAsState(brushType.onBrushContainer)
		val selectedOptions by remember(selectedKeys, options) {
			derivedStateOf {
				selectedKeys.map { key ->
					options.first { it.key == key }
				}
			}
		}
		selectedOptions.forEachIndexed { index, option ->
			Row(
				modifier = Modifier
					.height(tagHeight)
					.clip(tagShape)
					.background(
						brush = brushContainer,
						shape = tagShape
					)
					.padding(horizontal = tagHorizontalPadding),
				verticalAlignment = Alignment.CenterVertically
			) {
				Text(
					text = option.value,
					fontSize = tagFontSize,
					fontWeight = config.tagFontWeight,
					letterSpacing = tagLetterSpacing,
					style = LocalTextStyle.current.copy(
						brush = onBrushContainer.original
					)
				)
				Spacer(modifier = Modifier.width(tagHorizontalPadding))
				FlexIcon(
					imageVector = Icons.Rounded.Close,
					modifier = Modifier.size(tagIconSize),
					tint = onBrushContainer
				)
			}
			if (index < selectedOptions.size - 1) {
				Spacer(modifier = Modifier.width(tagInterval))
			}
		}
	}
}

@Composable
private fun <Key> FlexSelectOptionList(
	selectedKeys: List<Key>,
	onSelectedKeysChanged: (List<Key>) -> Unit,
	options: List<FlexOption<Key & Any>>,
	config: FlexSelectConfig,
	brushType: FlexBrushType,
	width: Dp,
	corner: Dp,
	multiple: Boolean
) {
	val paddingWithPopup by animateDpAsState(config.popupPadding)
	val maxHeightWithPopup by animateDpAsState(config.popupMaxHeight)
	val verticalScrollState = rememberScrollState()
	Column(
		modifier = Modifier
			.width(width)
			.then(
				if (options.isEmpty()) {
					val heightWhenOptionEmptyWithPopup by animateDpAsState(config.popupHeightWithEmpty)
					Modifier.height(heightWhenOptionEmptyWithPopup)
				} else {
					Modifier.heightIn(
						max = maxHeightWithPopup
					)
				}
			)
			.background(MaterialTheme.colorScheme.surface)
			.padding(paddingWithPopup / 2)
			.verticalScroll(verticalScrollState)
			.padding(paddingWithPopup / 2)
	) {
		val fontSize by animateFloatAsState(config.fontSize.value)
		val letterSpacing by animateFloatAsState(config.letterSpacing.value)
		val heightWithPopup by animateDpAsState(config.popupHeight)
		val brush by animateFlexBrushAsState(brushType.brush)
		val brushContainer by animateFlexBrushAsState(brushType.brushContainer)
		val onBrushContainer by animateFlexBrushAsState(brushType.onBrushContainer)
		
		val selectedList by remember(options, selectedKeys) {
			derivedStateOf {
				options.map { it.key in selectedKeys }
			}
		}
		val iconSize by animateDpAsState(config.iconSize)
		
		options.forEachIndexed { index, option ->
			val selected = selectedList[index]
			val topSelected = index > 0 && selectedList[index - 1]
			val bottomSelected = index < options.size - 1 && selectedList[index + 1]
			val topCorner = if (topSelected) Dp.Hairline else corner
			val bottomCorner = if (bottomSelected) Dp.Hairline else corner
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.height(heightWithPopup)
					.background(
						brush = if (selected) brushContainer else FlexBrush.Transparent,
						shape = RoundedCornerShape(
							topStart = topCorner,
							topEnd = topCorner,
							bottomEnd = bottomCorner,
							bottomStart = bottomCorner
						).also { println(it) }
					)
					.clip(RoundedCornerShape(corner))
					.clickable(
						enabled = option.enabled
					) {
						if (multiple) {
							val newSelectedKeys = if (selected) selectedKeys - option.key else selectedKeys + option.key
							onSelectedKeysChanged(newSelectedKeys)
						} else {
							onSelectedKeysChanged(listOf(option.key))
						}
					}
					.padding(horizontal = paddingWithPopup),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically,
			) {
				Text(
					text = option.value,
					fontSize = fontSize.sp,
					fontWeight = config.fontWeight,
					letterSpacing = if (letterSpacing >= 0f) letterSpacing.sp else TextUnit.Unspecified,
					style = LocalTextStyle.current.copy(
						brush = (if (selected) onBrushContainer else brush).original
					)
				)
				if (selected && multiple) {
					FlexIcon(
						imageVector = Icons.Rounded.Check,
						modifier = Modifier.size(iconSize),
						tint = onBrushContainer
					)
				}
			}
		}
	}
}