package cn.vividcode.multiplatform.flex.ui.sample

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.SpaceDashboard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexColorType
import cn.vividcode.multiplatform.flex.ui.foundation.ButtonType
import cn.vividcode.multiplatform.flex.ui.foundation.FlexButton
import cn.vividcode.multiplatform.flex.ui.sample.FlexCompose.FlexButton
import cn.vividcode.multiplatform.flex.ui.sample.foundation.FlexButtonPage
import cn.vividcode.multiplatform.flex.ui.theme.FlexThemeState
import cn.vividcode.multiplatform.flex.ui.theme.LocalInDarkTheme

@Composable
fun App() {
	var showFoldButton by remember { mutableStateOf(false) }
	var showSwitchThemeButton by remember { mutableStateOf(false) }
	var screenSize by remember { mutableStateOf(IntSize.Zero) }
	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.surface)
			.onGloballyPositioned {
				screenSize = it.size
			}
			.pointerInput(Unit) {
				awaitPointerEventScope {
					while (true) {
						val event = awaitPointerEvent()
						val position = event.changes.first().position
						val threshold = 200.dp.toPx()
						val inStart = position.x < threshold
						val inEnd = position.x > screenSize.width - threshold
						val inBottom = position.y > screenSize.height - threshold
						showFoldButton = inStart && inBottom
						showSwitchThemeButton = inEnd && inBottom
					}
				}
			}
	) {
		var itemOffsetX by remember { mutableStateOf(Dp.Hairline) }
		Box(
			modifier = Modifier.fillMaxSize()
		) {
			val flexComposeState = remember { mutableStateOf(FlexButton) }
			val targetItemOffsetX by animateDpAsState(
				targetValue = itemOffsetX
			)
			Column(
				modifier = Modifier
					.width(200.dp)
					.fillMaxHeight()
					.background(MaterialTheme.colorScheme.surface)
					.padding(horizontal = 8.dp)
					.offset(x = targetItemOffsetX)
			) {
				Column(
					modifier = Modifier
						.widthIn(min = 200.dp)
						.height(110.dp)
						.padding(top = 20.dp),
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.Center
				) {
					Text(
						text = "FlexUI",
						fontSize = 18.sp,
						color = MaterialTheme.colorScheme.onSurface,
						fontWeight = FontWeight.Medium
					)
					Text(
						text = "v1.0.0-exp01",
						fontSize = 13.sp,
						color = MaterialTheme.colorScheme.onSurface
					)
				}
				Spacer(modifier = Modifier.height(8.dp))
				Column(
					modifier = Modifier
						.widthIn(min = 200.dp)
						.fillMaxSize()
						.padding(bottom = 60.dp)
				) {
					FlexCompose.entries.forEach {
						ComposeItem(
							flexCompose = it,
							flexComposeState = flexComposeState
						)
					}
				}
			}
			Scaffold(
				modifier = Modifier
					.fillMaxSize()
					.padding(start = targetItemOffsetX + 200.dp),
				topBar = {
					Row(
						modifier = Modifier
							.shadow(
								elevation = 4.dp,
								spotColor = Color.Gray
							)
							.fillMaxWidth()
							.height(56.dp)
							.background(MaterialTheme.colorScheme.surface)
							.padding(horizontal = 8.dp),
						verticalAlignment = Alignment.CenterVertically,
						horizontalArrangement = Arrangement.SpaceBetween
					) {
						Row(
							modifier = Modifier.fillMaxHeight(),
							verticalAlignment = Alignment.CenterVertically,
						) {
							val current = flexComposeState.value
							Text(
								text = current.title,
								fontSize = 18.sp,
								fontWeight = FontWeight.Medium,
								color = MaterialTheme.colorScheme.onSurface,
								lineHeight = 18.sp
							)
							Spacer(Modifier.width(4.dp))
							Text(
								text = current.alias,
								fontSize = 13.sp,
								color = MaterialTheme.colorScheme.onSurface,
								lineHeight = 13.sp
							)
						}
						Row(
							modifier = Modifier.fillMaxHeight()
						) {
						
						}
					}
				}
			) {
				when (flexComposeState.value) {
					FlexButton -> FlexButtonPage(it)
				}
			}
		}
		val foldOffsetX by animateDpAsState(
			targetValue = if (showFoldButton) Dp.Hairline else -(50.dp)
		)
		FlexButton(
			modifier = Modifier
				.align(Alignment.BottomStart)
				.padding(
					start = 8.dp,
					bottom = 8.dp
				)
				.offset(x = foldOffsetX),
			icon = Icons.Outlined.SpaceDashboard,
			colorType = FlexColorType.Primary,
			buttonType = ButtonType.Primary,
		) {
			itemOffsetX = if (itemOffsetX == Dp.Hairline) -(200.dp) else Dp.Hairline
		}
		val switchThemeOffsetX by animateDpAsState(
			targetValue = if (showSwitchThemeButton) Dp.Hairline else 50.dp
		)
		val iconRotation by animateFloatAsState(
			targetValue = if (LocalInDarkTheme.current) -90f else 0f
		)
		FlexButton(
			icon = if (LocalInDarkTheme.current) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
			modifier = Modifier
				.align(Alignment.BottomEnd)
				.padding(
					end = 8.dp,
					bottom = 8.dp
				)
				.offset(x = switchThemeOffsetX),
			colorType = FlexColorType.Primary,
			buttonType = ButtonType.Primary,
			iconRotation = iconRotation
		) {
			FlexThemeState.darkTheme = !FlexThemeState.darkTheme!!
		}
	}
}

enum class FlexCompose(
	val title: String,
	val alias: String,
) {
	FlexButton("FlexButton", "按钮")
}

@Composable
private fun ComposeItem(
	flexCompose: FlexCompose,
	flexComposeState: MutableState<FlexCompose>,
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.height(40.dp)
			.clip(RoundedCornerShape(10.dp))
			.background(
				color = if (flexComposeState.value == flexCompose) MaterialTheme.colorScheme.surfaceContainerHighest else Color.Transparent,
				shape = RoundedCornerShape(10.dp)
			)
			.clickable {
				flexComposeState.value = flexCompose
			}
			.padding(horizontal = 16.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Text(
			text = flexCompose.title,
			fontSize = 15.sp,
			color = MaterialTheme.colorScheme.onSurface,
			lineHeight = 15.sp
		)
		Text(
			text = flexCompose.alias,
			fontSize = 13.sp,
			color = MaterialTheme.colorScheme.onSurface,
			lineHeight = 13.sp
		)
	}
}