package cn.vividcode.multiplatform.flex.ui.foundation.input

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.FlexComposeDefaultConfig
import cn.vividcode.multiplatform.flex.ui.config.FlexDefaults
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.foundation.FlexInputConfig
import cn.vividcode.multiplatform.flex.ui.foundation.icon.FlexIcon
import cn.vividcode.multiplatform.flex.ui.graphics.FlexBrush
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme
import cn.vividcode.multiplatform.flex.ui.type.*
import cn.vividcode.multiplatform.flex.ui.utils.*

/**
 * FlexInput 输入框
 *
 * @param value 输入框的当前值
 * @param onValueChange 当输入值发生变化时调用的回调函数
 * @param modifier 应用于输入框的修饰符
 * @param sizeType 输入框的尺寸类型
 * @param brushType 输入框的颜色类型
 * @param cornerType 输入框的圆角类型
 * @param inputType 输入框类型
 * @param enabled 是否启用输入框，默认为 `true`
 * @param readOnly 是否为只读模式，默认为 `false`
 * @param maxLength 允许输入的最大字符数，默认为 `Int.MAX_VALUE`
 * @param inputRegex 限制输入内容的正则表达式，为 `null` 时不限制
 * @param placeholder 输入框的占位内容
 * @param leadingIcon 输入框前置图标
 * @param trailingIcon 输入框后置图标
 * @param prefix 输入框前缀组件
 * @param suffix 输入框后缀组件
 * @param keyboardOptions 键盘输入选项，控制键盘类型、行为等
 * @param keyboardActions 键盘操作选项，定义输入法操作按钮的行为
 * @param visualTransformation 视觉转换，控制输入文本的显示效果
 */
@Composable
fun FlexInput(
	value: String,
	onValueChange: (String) -> Unit,
	modifier: Modifier = Modifier,
	sizeType: FlexSizeType = FlexInputDefaults.DefaultSizeType,
	brushType: FlexBrushType = FlexInputDefaults.DefaultBrushType,
	cornerType: FlexCornerType = FlexInputDefaults.DefaultCornerType,
	inputType: FlexInputType = FlexInputDefaults.DefaultInputType,
	enabled: Boolean = true,
	readOnly: Boolean = false,
	maxLength: Int = Int.MAX_VALUE,
	inputRegex: Regex? = null,
	placeholder: @Composable (() -> Unit)? = null,
	leadingIcon: FlexInputIcon? = null,
	trailingIcon: FlexInputIcon? = null,
	prefix: @Composable (() -> Unit)? = null,
	suffix: @Composable (() -> Unit)? = null,
	visualTransformation: VisualTransformation = VisualTransformation.None,
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
	keyboardActions: KeyboardActions = KeyboardActions.Default,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
	val config = LocalFlexConfig.current.input.getConfig(sizeType)
	
	CompositionLocalProvider(
		LocalTextSelectionColors provides TextSelectionColors(
			handleColor = brushType.color,
			backgroundColor = brushType.color.copy(alpha = 0.15f)
		)
	) {
		val leadingIconInteractionSource = remember { MutableInteractionSource() }
		val trailingIconInteractionSource = remember { MutableInteractionSource() }
		val textFieldIsFocused by interactionSource.collectIsFocusedAsState()
		val leadingIconIsFocused by leadingIconInteractionSource.collectIsFocusedAsState()
		val trailingIconIsFocused by trailingIconInteractionSource.collectIsFocusedAsState()
		val isFocused by remember(textFieldIsFocused, leadingIconIsFocused, trailingIconIsFocused) {
			derivedStateOf { textFieldIsFocused || leadingIconIsFocused || trailingIconIsFocused }
		}
		
		val textFieldIsHovered by interactionSource.collectIsHoveredAsState()
		val leadingIconIsHovered by leadingIconInteractionSource.collectIsHoveredAsState()
		val trailingIconIsHovered by trailingIconInteractionSource.collectIsHoveredAsState()
		val isHovered by remember(textFieldIsHovered, leadingIconIsHovered, trailingIconIsHovered) {
			derivedStateOf { textFieldIsHovered || leadingIconIsHovered || trailingIconIsHovered }
		}
		
		val fontSize by animateFloatAsState(config.fontSize.value)
		val letterSpacing by animateFloatAsState(config.letterSpacing.value)
		val contentBrush by animateFlexBrushAsState(
			targetValue = if (enabled) brushType.brush else brushType.disabledBrush
		)
		val textStyle by remember(fontSize, config.fontWeight, letterSpacing, contentBrush) {
			derivedStateOf {
				TextStyle(
					fontSize = fontSize.sp,
					fontWeight = config.fontWeight,
					letterSpacing = if (letterSpacing >= 0f) letterSpacing.sp else TextUnit.Unspecified,
					brush = contentBrush.original
				)
			}
		}
		
		val focusRequester = remember { FocusRequester() }
		
		BasicTextField(
			value = value,
			onValueChange = {
				if (it.length <= maxLength && inputRegex?.matches(it) != false) {
					onValueChange(it)
				}
			},
			modifier = Modifier
				.hoverable(interactionSource)
				.focusRequester(focusRequester),
			enabled = enabled,
			readOnly = readOnly,
			textStyle = textStyle,
			keyboardOptions = keyboardOptions,
			keyboardActions = keyboardActions,
			singleLine = true,
			visualTransformation = visualTransformation,
			interactionSource = interactionSource,
			cursorBrush = contentBrush.original,
			decorationBox = @Composable { innerTextField ->
				FlexInputDecorationBox(
					value = value,
					modifier = modifier,
					enabled = enabled,
					brushType = brushType,
					cornerType = cornerType,
					inputType = inputType,
					innerTextField = innerTextField,
					config = config,
					textStyle = textStyle,
					isFocused = isFocused,
					isHovered = isHovered,
					placeholder = placeholder,
					leadingIcon = leadingIcon,
					trailingIcon = trailingIcon,
					prefix = prefix,
					suffix = suffix,
					focusRequester = focusRequester,
					leadingIconInteractionSource = leadingIconInteractionSource,
					trailingIconInteractionSource = trailingIconInteractionSource,
				)
			}
		)
	}
}

object FlexInputDefaults : FlexDefaults() {
	
	override val FlexComposeDefaultConfig.defaultConfig
		get() = this.input
	
	val DefaultInputType: FlexInputType
		@Composable
		get() = LocalFlexInput.current
}

enum class FlexInputType {
	
	/**
	 * 默认样式
	 */
	Default,
	
	/**
	 * 线框样式
	 */
	Outlined
}

internal val LocalFlexInput = compositionLocalOf { FlexInputType.Default }

@Composable
private fun FlexInputDecorationBox(
	value: String,
	modifier: Modifier,
	enabled: Boolean,
	brushType: FlexBrushType,
	cornerType: FlexCornerType,
	inputType: FlexInputType,
	innerTextField: @Composable () -> Unit,
	config: FlexInputConfig,
	textStyle: TextStyle,
	isFocused: Boolean,
	isHovered: Boolean,
	placeholder: @Composable (() -> Unit)?,
	leadingIcon: FlexInputIcon?,
	trailingIcon: FlexInputIcon?,
	prefix: @Composable (() -> Unit)?,
	suffix: @Composable (() -> Unit)?,
	focusRequester: FocusRequester,
	leadingIconInteractionSource: MutableInteractionSource,
	trailingIconInteractionSource: MutableInteractionSource,
) {
	val borderWidth by animateDpAsState(config.borderWidth)
	val minWidth by animateDpAsState(config.minWidth)
	val height by animateDpAsState(config.height)
	val corner by animateDpAsState(height * cornerType.scale)
	val cornerShape by remember(corner) {
		derivedStateOf { RoundedCornerShape(corner) }
	}
	val borderBrush by animateFlexBrushAsState(
		targetValue = when (inputType) {
			FlexInputType.Default -> {
				when {
					!enabled -> brushType.transparentBrush
					isFocused -> brushType.brush
					isHovered -> brushType.brush.copy(alpha = 0.8f)
					else -> brushType.transparentBrush
				}
			}
			
			FlexInputType.Outlined -> {
				when {
					!enabled -> brushType.disabledBrush
					isFocused -> brushType.brush
					isHovered -> brushType.brush.copy(alpha = 0.8f)
					else -> brushType.brush.copy(alpha = 0.6f)
				}
			}
		}
	)
	val backgroundBrush by animateFlexBrushAsState(
		targetValue = run {
			val brush = brushType.brush.let {
				if (LocalDarkTheme.current) it.darken(0.65f) else it.lighten(0.85f)
			}
			when {
				inputType == FlexInputType.Outlined -> brush.copy(alpha = 0f)
				!enabled -> brush.copy(alpha = 0.8f)
				isFocused || isHovered -> brush.copy(alpha = 0f)
				else -> brush
			}
		}
	)
	val interval by animateDpAsState(config.horizontalPadding / 2)
	Row(
		modifier = Modifier
			.widthIn(min = minWidth)
			.height(height)
			.border(
				width = borderWidth,
				brush = borderBrush,
				shape = cornerShape
			)
			.background(
				brush = backgroundBrush,
				shape = cornerShape
			)
			.then(modifier)
			.padding(horizontal = interval + borderWidth),
		verticalAlignment = Alignment.CenterVertically,
	) {
		val iconSize by animateDpAsState(config.iconSize)
		if (leadingIcon != null) {
			FlexInputIcon(
				icon = leadingIcon,
				iconSize = iconSize,
				iconBrush = brushType.brush,
				enabled = enabled,
				isFocused = isFocused,
				isHovered = isHovered,
				focusRequester = focusRequester,
				interactionSource = leadingIconInteractionSource
			)
		}
		if (prefix != null) {
			Spacer(modifier = Modifier.width(interval))
			CompositionLocalProvider(
				LocalTextStyle provides textStyle
			) {
				prefix()
			}
		}
		Spacer(modifier = Modifier.width(interval))
		
		Box {
			val isEmpty by remember(value) {
				derivedStateOf { value.isEmpty() }
			}
			if (placeholder != null && isEmpty) {
				CompositionLocalProvider(
					LocalTextStyle provides textStyle.copy(
						brush = brushType.brush.copy(alpha = 0.7f).original
					)
				) {
					placeholder()
				}
			}
			innerTextField()
		}
		
		Spacer(modifier = Modifier.width(interval))
		if (suffix != null) {
			CompositionLocalProvider(
				LocalTextStyle provides textStyle
			) {
				suffix()
			}
			Spacer(modifier = Modifier.width(interval))
		}
		if (trailingIcon != null) {
			FlexInputIcon(
				icon = trailingIcon,
				iconSize = iconSize,
				iconBrush = brushType.brush,
				enabled = enabled,
				isFocused = isFocused,
				isHovered = isHovered,
				focusRequester = focusRequester,
				interactionSource = trailingIconInteractionSource
			)
		}
	}
}

@Composable
private fun FlexInputIcon(
	icon: FlexInputIcon,
	iconSize: Dp,
	iconBrush: FlexBrush,
	enabled: Boolean,
	isFocused: Boolean,
	isHovered: Boolean,
	focusRequester: FocusRequester,
	interactionSource: MutableInteractionSource,
) {
	val isPressed by interactionSource.collectIsPressedAsState()
	val brush = icon.tint ?: iconBrush
	val iconTint by animateFlexBrushAsState(
		targetValue = when {
			!enabled -> brush.disabledWithBrush
			isPressed -> brush.darkenWithBrush
			isFocused || isHovered -> brush
			else -> brush.copy(alpha = 0.7f)
		}
	)
	val size by animateDpAsState(
		targetValue = if (icon.size != Dp.Unspecified) icon.size else iconSize,
	)
	val rotateDegrees by animateFloatAsState(icon.rotateDegrees)
	val onClick = icon.onClick
	FlexIcon(
		imageVector = icon.icon,
		modifier = Modifier
			.size(size)
			.rotate(rotateDegrees)
			.pointerHoverIcon(PointerIcon.Default)
			.hoverable(interactionSource)
			.then(
				if (onClick == null) Modifier else Modifier
					.focusable(
						interactionSource = interactionSource
					)
					.clickable(
						interactionSource = interactionSource,
						indication = null,
						onClick = {
							onClick()
							focusRequester.requestFocus()
						}
					),
			),
		tint = iconTint
	)
}

class FlexInputIcon internal constructor(
	val icon: ImageVector,
	val tint: FlexBrush?,
	val rotateDegrees: Float,
	val size: Dp,
	val onClick: (() -> Unit)?,
)

object FlexInputIcons {
	
	@Composable
	fun icon(
		icon: ImageVector,
		tint: FlexBrush? = null,
		rotate: Float = 0f,
		size: Dp = Dp.Unspecified,
		onClick: (() -> Unit)? = null,
	) = remember(icon, tint, rotate, size, onClick) {
		FlexInputIcon(icon, tint, rotate, size, onClick)
	}
}