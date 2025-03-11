package cn.vividcode.multiplatform.flex.ui.layout.popup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

/**
 * 项目：flex-ui
 *
 * 作者：李佳伟
 *
 * 创建：2025/3/11 20:52
 *
 * 介绍：支持动画的 Popup
 */
@Composable
fun AnimatedPopup(
	visible: Boolean,
	alignment: Alignment = Alignment.TopStart,
	offset: IntOffset = IntOffset.Zero,
	onDismissRequest: (() -> Unit)? = null,
	properties: PopupProperties = PopupProperties(),
	enter: EnterTransition = AnimatedPopupDefaults.DefaultEnterTransition,
	exit: ExitTransition = AnimatedPopupDefaults.DefaultExitTransition,
	content: @Composable () -> Unit
) {
	val transitionState = remember { MutableTransitionState(false) }
	LaunchedEffect(visible) {
		transitionState.targetState = visible
	}
	val isPopupVisible by remember(transitionState.targetState, transitionState.isIdle) {
		derivedStateOf { transitionState.targetState || !transitionState.isIdle }
	}
	if (isPopupVisible) {
		Popup(
			alignment = alignment,
			offset = offset,
			onDismissRequest = onDismissRequest,
			properties = properties
		) {
			AnimatedVisibility(
				visibleState = transitionState,
				enter = enter,
				exit = exit,
				label = "AnimatedPopupVisibility"
			) {
				content()
			}
		}
	}
}

private object AnimatedPopupDefaults {
	
	val DefaultEnterTransition = fadeIn()
	
	val DefaultExitTransition = fadeOut()
}