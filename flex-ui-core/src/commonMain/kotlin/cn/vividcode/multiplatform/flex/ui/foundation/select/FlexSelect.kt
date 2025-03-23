package cn.vividcode.multiplatform.flex.ui.foundation.select

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
import cn.vividcode.multiplatform.flex.ui.utils.lightenWithBrush
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
	placeholder: @Composable () -> Unit = { Text("Please select it.") },
	enabled: Boolean = true
) {
	FlexSelectImpl(
		selectedKeys = listOfNotNull(selectedKey),
		onSelectedKeysChanged = {
			onSelectedKeyChanged(it?.firstOrNull())
		},
		options = options,
		multiple = false,
		modifier = modifier,
		sizeType = sizeType,
		brushType = brushType,
		cornerType = cornerType,
		selectType = selectType,
		placeholder = placeholder,
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
	placeholder: @Composable () -> Unit = { Text("Please select them.") },
	enabled: Boolean = true
) {
	FlexSelectImpl(
		selectedKeys = selectedKeys,
		onSelectedKeysChanged = {
			onSelectedKeysChanged(it ?: emptyList())
		},
		options = options,
		multiple = true,
		modifier = modifier,
		sizeType = sizeType,
		brushType = brushType,
		cornerType = cornerType,
		selectType = selectType,
		placeholder = placeholder,
		enabled = enabled
	)
}

@Composable
private fun <Key : Any> FlexSelectImpl(
	selectedKeys: List<Key>,
	onSelectedKeysChanged: (List<Key>?) -> Unit,
	options: List<FlexOption<Key>>,
	multiple: Boolean,
	modifier: Modifier,
	sizeType: FlexSizeType,
	brushType: FlexBrushType,
	cornerType: FlexCornerType,
	selectType: FlexSelectType,
	placeholder: @Composable () -> Unit,
	enabled: Boolean,
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
	val isHovered by interactionSource.collectIsHoveredAsState()
	Row(
		modifier = Modifier
			.widthIn(min = minWidth)
			.then(modifier)
			.width(minWidth)
			.height(height)
			.onGloballyPositioned {
				size = it.size
			}
			.clip(shape)
			.clickable(
				interactionSource = interactionSource,
				indication = null,
				onClick = {}
			)
			.hoverable(interactionSource)
			.border(
				width = borderWidth,
				brush = borderBrush,
				shape = shape
			)
			.padding(horizontal = horizontalPadding),
		verticalAlignment = Alignment.CenterVertically
	) {
		val density = LocalDensity.current
		val searchInteractionSource = remember { MutableInteractionSource() }
		var searchText by remember { mutableStateOf<String?>(null) }
		val filterOptions by remember(options, searchText) {
			derivedStateOf {
				searchText?.isNotBlank()?.let {
					options.filter {
						it.value.trim().contains(searchText!!.trim(), ignoreCase = true)
					}
				} ?: options
			}
		}
		if (multiple) {
			FlexMultipleSelectedOptions(
				selectedKeys = selectedKeys,
				options = options,
				config = config,
				brushType = brushType,
				cornerType = cornerType
			)
		} else {
			FlexSelectSingleSearch(
				selectedKey = selectedKeys.firstOrNull(),
				options = options,
				selectType = selectType,
				brushType = brushType,
				config = config,
				placeholder = placeholder,
				interactionSource = searchInteractionSource,
				isPopupVisible = isPopupVisible,
				onSearchChanged = { searchText = it }
			)
		}
		
		FlexSelectIcon(
			brushType = brushType,
			config = config,
			isSelectHovered = isHovered,
			onClearListener = { onSelectedKeysChanged(null) }
		)
		
		val isPressed by interactionSource.collectIsPressedAsState()
		val isSearchPressed by searchInteractionSource.collectIsPressedAsState()
		var isIdle by remember { mutableStateOf(true) }
		LaunchedEffect(isPressed, isSearchPressed) {
			if ((isPressed || isSearchPressed) && isIdle) {
				isPopupVisible = true
			}
		}
		val popupInterval by animateDpAsState(config.popupInterval)
		val offset by remember(size, popupInterval) {
			derivedStateOf {
				with(density) {
					IntOffset(
						x = -horizontalPadding.roundToPx(),
						y = size.height + popupInterval.roundToPx()
					)
				}
			}
		}
		FlexSelectPopup(
			isPopupVisible = isPopupVisible,
			onDismissRequest = {
				if (isIdle) {
					isPopupVisible = false
					searchText = null
				}
			},
			width = with(density) { size.width.toDp() },
			offset = offset,
			onIdleChanged = { isIdle = it },
			config = config,
			selectedKeys = selectedKeys,
			onSelectedKeysChanged = onSelectedKeysChanged,
			options = filterOptions,
			brushType = brushType,
			cornerType = cornerType,
			multiple = multiple,
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

private val DefaultEnterTransition = expandVertically(animationSpec = spring())
private val DefaultExitTransition = shrinkVertically(animationSpec = spring())

/**
 * 多选框的选项
 */
@Composable
private fun <Key : Any> RowScope.FlexMultipleSelectedOptions(
	selectedKeys: List<Key>,
	options: List<FlexOption<Key>>,
	config: FlexSelectConfig,
	brushType: FlexBrushType,
	cornerType: FlexCornerType,
) {
	val horizontalScrollState = rememberScrollState()
	Row(
		modifier = Modifier
			.weight(1f)
			.horizontalScroll(horizontalScrollState),
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
	val popupPadding by animateDpAsState(config.popupPadding)
	val popupMaxHeight by animateDpAsState(config.popupMaxHeight)
	val verticalScrollState = rememberScrollState()
	val itemShape by remember(corner) {
		derivedStateOf {
			RoundedCornerShape(corner)
		}
	}
	Column(
		modifier = Modifier
			.width(width)
			.then(
				if (options.isEmpty()) {
					val popupHeightWithEmptyOptions by animateDpAsState(config.popupHeightWithEmptyOptions)
					Modifier.height(popupHeightWithEmptyOptions)
				} else {
					Modifier.heightIn(
						max = popupMaxHeight
					)
				}
			)
			.background(MaterialTheme.colorScheme.surface)
			.padding(popupPadding)
			.clip(itemShape)
			.verticalScroll(verticalScrollState)
	) {
		val fontSize by animateFloatAsState(config.fontSize.value)
		val letterSpacing by animateFloatAsState(config.letterSpacing.value)
		val popupItemHeight by animateDpAsState(config.popupItemHeight)
		val popupItemHorizontalPadding by animateDpAsState(config.popupItemHorizontalPadding)
		val brush by animateFlexBrushAsState(brushType.brush)
		val onBrushContainer by animateFlexBrushAsState(brushType.onBrushContainer)
		val iconSize by animateDpAsState(config.iconSize)
		val selectedList by remember(options, selectedKeys) {
			derivedStateOf {
				options.map { it.key in selectedKeys }
			}
		}
		options.forEachIndexed { index, option ->
			val selected = selectedList[index]
			val topSelected = index > 0 && selectedList[index - 1]
			val bottomSelected = index < options.size - 1 && selectedList[index + 1]
			val topCorner = if (topSelected) Dp.Hairline else corner
			val bottomCorner = if (bottomSelected) Dp.Hairline else corner
			val shape by remember(topCorner, bottomCorner) {
				derivedStateOf {
					RoundedCornerShape(
						topStart = topCorner,
						topEnd = topCorner,
						bottomEnd = bottomCorner,
						bottomStart = bottomCorner
					)
				}
			}
			val brushContainer by animateFlexBrushAsState(
				targetValue = if (selected) brushType.brushContainer else FlexBrush.Transparent
			)
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.height(popupItemHeight)
					.background(
						brush = brushContainer,
						shape = shape
					)
					.clip(itemShape)
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
					.padding(horizontal = popupItemHorizontalPadding),
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

@Composable
private fun <Key : Any> RowScope.FlexSelectSingleSearch(
	selectedKey: Key?,
	options: List<FlexOption<Key>>,
	selectType: FlexSelectType,
	brushType: FlexBrushType,
	config: FlexSelectConfig,
	placeholder: @Composable () -> Unit,
	interactionSource: MutableInteractionSource,
	isPopupVisible: Boolean,
	onSearchChanged: (String) -> Unit
) {
	var value by remember { mutableStateOf("") }
	val brush by animateFlexBrushAsState(
		targetValue = when {
			isPopupVisible && selectType == FlexSelectType.Default -> brushType.brush.copy(alpha = 0.7f)
			else -> brushType.brush
		}
	)
	LaunchedEffect(selectedKey) {
		value = options.find { it.key == selectedKey }?.value ?: ""
	}
	val fontSize by animateTextUnitAsState(config.fontSize)
	val letterSpacing by animateTextUnitAsState(config.letterSpacing)
	val horizontalPadding by animateDpAsState(config.horizontalPadding)
	CompositionLocalProvider(
		LocalTextSelectionColors provides TextSelectionColors(
			handleColor = brushType.brush.colors.first(),
			backgroundColor = brushType.brush.colors.first().copy(alpha = 0.15f),
		)
	) {
		BasicTextField(
			value = value,
			onValueChange = {
				value = it
				onSearchChanged(it)
			},
			modifier = Modifier
				.weight(1f)
				.padding(horizontal = horizontalPadding),
			readOnly = selectType == FlexSelectType.Default,
			textStyle = LocalTextStyle.current.copy(
				brush = brush.original,
				fontSize = fontSize,
				fontWeight = config.fontWeight,
				letterSpacing = letterSpacing,
				lineHeight = fontSize
			),
			singleLine = true,
			interactionSource = interactionSource,
			decorationBox = @Composable { innerTextField ->
				if (value.isEmpty()) {
					val placeholderBrush by animateFlexBrushAsState(brushType.brush.copy(alpha = 0.7f))
					CompositionLocalProvider(
						LocalTextStyle provides LocalTextStyle.current.copy(
							brush = placeholderBrush.original,
							fontSize = fontSize,
							fontWeight = config.fontWeight,
							letterSpacing = letterSpacing,
							lineHeight = fontSize
						)
					) {
						placeholder()
					}
				} else {
					innerTextField()
				}
			}
		)
	}
}

@Composable
private fun FlexSelectIcon(
	brushType: FlexBrushType,
	config: FlexSelectConfig,
	isSelectHovered: Boolean,
	onClearListener: () -> Unit
) {
	val iconSize by animateDpAsState(config.iconSize)
	Crossfade(
		targetState = isSelectHovered,
		modifier = Modifier.size(iconSize),
		animationSpec = spring()
	) {
		if (it) {
			val interactionSource = remember { MutableInteractionSource() }
			val isHovered by interactionSource.collectIsHoveredAsState()
			val isPressed by interactionSource.collectIsPressedAsState()
			val brush by animateFlexBrushAsState(
				targetValue = when {
					isPressed -> brushType.brush.lightenWithBrush
					isHovered -> brushType.brush
					else -> FlexBrush.Gray
				}
			)
			val scale by animateFloatAsState(
				targetValue = if (isPressed) 0.9f else 1f
			)
			FlexIcon(
				imageVector = Icons.Rounded.Cancel,
				modifier = Modifier
					.scale(scale)
					.size(iconSize)
					.hoverable(interactionSource)
					.clickable(
						interactionSource = interactionSource,
						indication = null,
						onClick = onClearListener
					),
				tint = brush
			)
		} else {
			FlexIcon(
				imageVector = Icons.Rounded.KeyboardArrowDown,
				modifier = Modifier.size(iconSize),
				tint = FlexBrush.Gray
			)
		}
	}
}