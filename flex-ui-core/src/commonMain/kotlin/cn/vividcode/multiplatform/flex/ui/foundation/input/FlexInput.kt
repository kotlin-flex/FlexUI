package cn.vividcode.multiplatform.flex.ui.foundation.input

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.type.*

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
	enabled: Boolean = true,
	readOnly: Boolean = false,
	maxLength: Int = Int.MAX_VALUE,
	filterRegex: Regex? = null,
	placeholder: @Composable (() -> Unit)? = null,
	leadingIcon: FlexInputIcon? = null,
	trailingIcon: FlexInputIcon? = null,
	prefix: @Composable (() -> Unit)? = null,
	suffix: @Composable (() -> Unit)? = null,
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
	keyboardActions: KeyboardActions = KeyboardActions.Default,
	visualTransformation: VisualTransformation = VisualTransformation.None,
) {
	val current = LocalFlexConfig.current
	val config = current.input.getConfig(sizeType)
	val targetColor = current.theme.colorScheme.current.getColor(colorType)
	val color by animateColorAsState(targetColor)
	
	CompositionLocalProvider(
		LocalTextSelectionColors provides TextSelectionColors(
			handleColor = color,
			backgroundColor = color.copy(alpha = 0.15f)
		)
	) {
		val minWidth by animateDpAsState(config.minWidth)
		val height by animateDpAsState(config.height)
		
		val textFieldInteractionSource = remember { MutableInteractionSource() }
		val leadingIconInteractionSource = remember { MutableInteractionSource() }
		val trailingIconInteractionSource = remember { MutableInteractionSource() }
		val textFieldIsFocused by textFieldInteractionSource.collectIsFocusedAsState()
		val leadingIconIsFocused by leadingIconInteractionSource.collectIsFocusedAsState()
		val trailingIconIsFocused by trailingIconInteractionSource.collectIsFocusedAsState()
		val isFocused = textFieldIsFocused || leadingIconIsFocused || trailingIconIsFocused
		
		val focusRequester = remember { FocusRequester() }
		val fontSize by animateFloatAsState(config.fontSize.value)
		val letterSpacing by animateFloatAsState(config.letterSpacing.value)
		val textStyle by remember(fontSize, config.fontWeight, letterSpacing, color) {
			derivedStateOf {
				TextStyle(
					fontSize = fontSize.sp,
					fontWeight = config.fontWeight,
					letterSpacing = if (letterSpacing >= 0f) letterSpacing.sp else TextUnit.Unspecified,
					color = color
				)
			}
		}
		
		BasicTextField(
			value = value,
			onValueChange = {
				if (it.length <= maxLength && filterRegex?.matches(it) != false) {
					onValueChange(it)
				}
			},
			modifier = Modifier
				.focusRequester(focusRequester),
			enabled = enabled,
			readOnly = readOnly,
			textStyle = textStyle,
			keyboardOptions = keyboardOptions,
			keyboardActions = keyboardActions,
			singleLine = true,
			visualTransformation = visualTransformation,
			interactionSource = textFieldInteractionSource,
			cursorBrush = SolidColor(color),
			decorationBox = @Composable { innerTextField ->
				val borderWidth by animateDpAsState(config.borderWidth)
				val corner by animateDpAsState(height * cornerType.percent)
				val cornerShape by remember(corner) {
					derivedStateOf { RoundedCornerShape(corner) }
				}
				val borderColor by animateColorAsState(
					targetValue = if (isFocused) targetColor else targetColor.copy(alpha = 0f),
				)
				val backgroundColor by animateColorAsState(
					targetValue = if (isFocused) Color.Gray.copy(alpha = 0f) else Color.Gray.copy(alpha = 0.15f),
				)
				val interval by animateDpAsState(config.horizontalPadding / 2)
				Row(
					modifier = Modifier
						.widthIn(min = minWidth)
						.height(height)
						.border(
							width = borderWidth,
							color = borderColor,
							shape = cornerShape
						)
						.background(
							color = backgroundColor,
							shape = cornerShape
						)
						.then(modifier)
						.padding(horizontal = interval + borderWidth),
					verticalAlignment = Alignment.CenterVertically,
				) {
					val iconSize by animateDpAsState(config.iconSize)
					if (leadingIcon != null) {
						FlexInputIcon(
							icon = leadingIcon.icon,
							size = iconSize,
							tint = leadingIcon.tint ?: targetColor,
							onClick = leadingIcon.onClick,
							isFocused = isFocused,
							focusRequester = focusRequester,
							interactionSource = leadingIconInteractionSource
						)
					}
					if (prefix != null) {
						Spacer(modifier = Modifier.width(interval))
						CompositionLocalProvider(
							LocalContentColor provides color,
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
									color = color.copy(alpha = 0.6f)
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
							LocalContentColor provides color,
							LocalTextStyle provides textStyle
						) {
							suffix()
						}
						Spacer(modifier = Modifier.width(interval))
					}
					if (trailingIcon != null) {
						FlexInputIcon(
							icon = trailingIcon.icon,
							size = iconSize,
							tint = trailingIcon.tint ?: targetColor,
							onClick = trailingIcon.onClick,
							isFocused = isFocused,
							focusRequester = focusRequester,
							interactionSource = trailingIconInteractionSource
						)
					}
				}
			}
		)
	}
}

@Composable
private fun FlexInputIcon(
	icon: ImageVector,
	size: Dp,
	tint: Color,
	onClick: (() -> Unit)?,
	isFocused: Boolean,
	focusRequester: FocusRequester,
	interactionSource: MutableInteractionSource,
) {
	val isPressed by interactionSource.collectIsPressedAsState()
	val iconTint by animateColorAsState(
		targetValue = when {
			!isFocused -> tint.copy(alpha = 0.6f)
			isPressed -> tint.copy(alpha = 0.8f)
			else -> tint
		}
	)
	Icon(
		imageVector = icon,
		contentDescription = null,
		modifier = Modifier
			.size(size)
			.pointerHoverIcon(PointerIcon.Default)
			.then(
				if (onClick == null) Modifier else Modifier.clickable(
					interactionSource = interactionSource,
					indication = null,
					onClick = {
						onClick()
						focusRequester.requestFocus()
					}
				)
			),
		tint = iconTint
	)
}

object FlexInputs {
	
	val DefaultSizeType: FlexSizeType
		@Composable
		get() = getDefaultSizeType { input.sizeType }
	
	val DefaultColorType: FlexColorType
		@Composable
		get() = getDefaultColorType { input.colorType }
	
	val DefaultCornerType: FlexCornerType
		@Composable
		get() = getDefaultCornerType { input.cornerType }
	
	fun icon(
		icon: ImageVector,
		tint: Color? = null,
		onClick: (() -> Unit)? = null,
	) = FlexInputIcon(icon, tint, onClick)
}

class FlexInputIcon internal constructor(
	val icon: ImageVector,
	val tint: Color?,
	val onClick: (() -> Unit)?,
)