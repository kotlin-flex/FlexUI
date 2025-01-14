package cn.vividcode.multiplatform.flex.ui.sample

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.PlaylistPlay
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.PlaylistRemove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexColorType
import cn.vividcode.multiplatform.flex.ui.expends.multiplatform
import cn.vividcode.multiplatform.flex.ui.foundation.ButtonType
import cn.vividcode.multiplatform.flex.ui.foundation.FlexButton
import cn.vividcode.multiplatform.flex.ui.sample.FlexCompose.FlexButton
import cn.vividcode.multiplatform.flex.ui.sample.foundation.FlexButtonPage
import cn.vividcode.multiplatform.flex.ui.theme.FlexPlatform
import cn.vividcode.multiplatform.flex.ui.theme.FlexThemeState
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme

private val ItemWidth = 180.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
	var showFoldButton by remember { mutableStateOf(false) }
	var showSwitchThemeButton by remember { mutableStateOf(false) }
	var screenSize by remember { mutableStateOf(IntSize.Zero) }
	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.surface)
			.multiplatform(FlexPlatform.Web, FlexPlatform.Desktop) {
				this.onGloballyPositioned {
					screenSize = it.size
				}.pointerInput(Unit) {
					awaitPointerEventScope {
						while (true) {
							val event = awaitPointerEvent()
							val position = event.changes.first().position
							val threshold = ItemWidth.toPx()
							val inStart = position.x < threshold
							val inEnd = position.x > screenSize.width - threshold
							val inBottom = position.y > screenSize.height - threshold
							showFoldButton = inStart && inBottom
							showSwitchThemeButton = inEnd && inBottom
						}
					}
				}
			}
	) {
		var itemOffsetX by remember { mutableStateOf(-ItemWidth) }
		Box(
			modifier = Modifier.fillMaxSize()
		) {
			val flexComposeState = remember { mutableStateOf(FlexButton) }
			val targetItemOffsetX by animateDpAsState(
				targetValue = itemOffsetX
			)
			Column(
				modifier = Modifier
					.width(ItemWidth)
					.fillMaxHeight()
					.background(MaterialTheme.colorScheme.surface)
					.padding(horizontal = 8.dp)
					.offset(x = targetItemOffsetX)
			) {
				Column(
					modifier = Modifier
						.widthIn(min = ItemWidth)
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
						.widthIn(min = ItemWidth)
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
					.padding(start = targetItemOffsetX + ItemWidth),
				topBar = {
					TopAppBar(
						title = {
							Row(
								modifier = Modifier.fillMaxHeight(),
								verticalAlignment = Alignment.CenterVertically,
							) {
								val current = flexComposeState.value
								Text(
									text = current.title,
									fontSize = 20.sp,
									fontWeight = FontWeight.Medium,
									color = MaterialTheme.colorScheme.onSurface,
									lineHeight = 20.sp
								)
								Spacer(Modifier.width(4.dp))
								Text(
									text = current.alias,
									fontSize = 14.sp,
									color = MaterialTheme.colorScheme.onSurface,
									lineHeight = 14.sp
								)
							}
						},
						actions = {
							if (FlexPlatform.isMobile) {
								FlexButton(
									icon = when (itemOffsetX == Dp.Hairline) {
										true -> Icons.Outlined.PlaylistRemove
										false -> Icons.AutoMirrored.Outlined.PlaylistPlay
									},
									colorType = if (itemOffsetX == Dp.Hairline) FlexColorType.Default else FlexColorType.Primary,
									buttonType = ButtonType.Primary,
								) {
									itemOffsetX = if (itemOffsetX == Dp.Hairline) -ItemWidth else Dp.Hairline
								}
								val themeIconRotation by animateFloatAsState(
									targetValue = if (LocalDarkTheme.current) -90f else 0f
								)
								Spacer(modifier = Modifier.width(8.dp))
								FlexButton(
									icon = if (LocalDarkTheme.current) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
									buttonType = ButtonType.Primary,
									iconRotation = themeIconRotation
								) {
									FlexThemeState.darkTheme = !FlexThemeState.darkTheme!!
								}
							}
						}
					)
				}
			) {
				when (flexComposeState.value) {
					FlexButton -> FlexButtonPage(it)
				}
			}
		}
		if (!FlexPlatform.isMobile) {
			val foldOffsetX by animateDpAsState(
				targetValue = if (showFoldButton) Dp.Hairline else -(52.dp)
			)
			FlexButton(
				modifier = Modifier
					.align(Alignment.BottomStart)
					.padding(
						start = 12.dp,
						bottom = 12.dp
					)
					.offset(x = foldOffsetX),
				icon = when (itemOffsetX == Dp.Hairline) {
					true -> Icons.Outlined.PlaylistRemove
					false -> Icons.AutoMirrored.Outlined.PlaylistPlay
				},
				colorType = if (itemOffsetX == Dp.Hairline) FlexColorType.Default else FlexColorType.Primary,
				buttonType = ButtonType.Primary,
			) {
				itemOffsetX = if (itemOffsetX == Dp.Hairline) -(ItemWidth) else Dp.Hairline
			}
			val switchThemeOffsetX by animateDpAsState(
				targetValue = if (showSwitchThemeButton) Dp.Hairline else 52.dp
			)
			val themeIconRotation by animateFloatAsState(
				targetValue = if (LocalDarkTheme.current) -90f else 0f
			)
			FlexButton(
				icon = if (LocalDarkTheme.current) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
				modifier = Modifier
					.align(Alignment.BottomEnd)
					.padding(
						end = 12.dp,
						bottom = 12.dp
					)
					.offset(x = switchThemeOffsetX),
				buttonType = ButtonType.Primary,
				iconRotation = themeIconRotation
			) {
				FlexThemeState.darkTheme = !FlexThemeState.darkTheme!!
			}
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