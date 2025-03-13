package cn.vividcode.multiplatform.flex.ui.layout.popup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

/**
 * 支持动画的 Popup
 */
@Composable
fun FlexAnimatedPopup(
	visible: Boolean,
	alignment: Alignment = Alignment.TopStart,
	offset: IntOffset = IntOffset.Zero,
	onDismissRequest: (() -> Unit)? = null,
	properties: PopupProperties = PopupProperties(),
	enter: EnterTransition = FlexAnimatedPopupDefaults.DefaultEnterTransition,
	exit: ExitTransition = FlexAnimatedPopupDefaults.DefaultExitTransition,
	shape: Shape = FlexAnimatedPopupDefaults.DefaultShape,
	shadow: FlexAnimatedPopupShadow? = FlexAnimatedPopupDefaults.shadow(shape = shape),
	onIdleChanged: ((idle: Boolean) -> Unit)? = null,
	content: @Composable () -> Unit
) {
	val transitionState = remember { MutableTransitionState(false) }
	LaunchedEffect(visible) {
		transitionState.targetState = visible
	}
	val isPopupVisible by remember(transitionState.targetState, transitionState.isIdle) {
		derivedStateOf { transitionState.targetState || !transitionState.isIdle }
	}
	if (onIdleChanged != null) {
		LaunchedEffect(transitionState.isIdle) {
			onIdleChanged(transitionState.isIdle)
		}
	}
	if (isPopupVisible) {
		Popup(
			alignment = alignment,
			offset = offset,
			onDismissRequest = onDismissRequest,
			properties = properties
		) {
			ShadowBoxIfUse(
				visible = transitionState.targetState,
				shadow = shadow
			) {
				AnimatedVisibility(
					visibleState = transitionState,
					modifier = Modifier
						.clip(shape),
					enter = enter,
					exit = exit,
					label = "FlexAnimatedPopupVisibility"
				) {
					content()
				}
			}
		}
	}
}

@Composable
private fun ShadowBoxIfUse(
	visible: Boolean,
	shadow: FlexAnimatedPopupShadow?,
	content: @Composable () -> Unit
) {
	if (shadow != null) {
		Box(
			modifier = Modifier
				.shadow(
					elevation = shadow.elevation,
					shape = shadow.shape,
					clip = shadow.clip,
					ambientColor = shadow.ambientColor,
					spotColor = shadow.spotColor
				)
		) {
			content()
		}
	} else {
		content()
	}
}

object FlexAnimatedPopupDefaults {
	
	internal val DefaultEnterTransition = fadeIn(animationSpec = spring())
	
	internal val DefaultExitTransition = fadeOut(animationSpec = spring())
	
	private val DefaultShadowElevation = 16.dp
	
	internal val DefaultShape = RectangleShape
	
	private val DefaultShadowColor = Color.Black.copy(alpha = 0.5f)
	
	fun shadow(
		elevation: Dp = DefaultShadowElevation,
		shape: Shape = DefaultShape,
		clip: Boolean = elevation > 0.dp,
		ambientColor: Color = DefaultShadowColor,
		spotColor: Color = DefaultShadowColor
	): FlexAnimatedPopupShadow = FlexAnimatedPopupShadow(elevation, shape, clip, ambientColor, spotColor)
}

@ConsistentCopyVisibility
data class FlexAnimatedPopupShadow internal constructor(
	val elevation: Dp,
	val shape: Shape,
	val clip: Boolean,
	val ambientColor: Color,
	val spotColor: Color
)