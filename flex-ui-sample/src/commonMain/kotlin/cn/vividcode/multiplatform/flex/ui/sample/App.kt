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
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.expends.multiplatform
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButton
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButtonType
import cn.vividcode.multiplatform.flex.ui.sample.page.FlexButtonPage
import cn.vividcode.multiplatform.flex.ui.sample.page.FlexInputPage
import cn.vividcode.multiplatform.flex.ui.sample.page.FlexRadioPage
import cn.vividcode.multiplatform.flex.ui.sample.page.SettingsPage
import cn.vividcode.multiplatform.flex.ui.theme.FlexPlatform
import cn.vividcode.multiplatform.flex.ui.theme.FlexThemeState
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme
import kotlin.math.sqrt

private const val VERSION_NAME = "v1.0.0-exp-05"

private val versionType = VersionType.EXP

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
	var showToolbar by remember { mutableStateOf(false) }
	var screenHeight by remember { mutableStateOf(0) }
	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.surface)
			.multiplatform(FlexPlatform.Web, FlexPlatform.Desktop) {
				this.onGloballyPositioned {
					screenHeight = it.size.height
				}.pointerInput(Unit) {
					awaitPointerEventScope {
						val thresholdX = 104.dp.toPx()
						val thresholdY = 152.dp.toPx()
						while (true) {
							val event = awaitPointerEvent()
							val position = event.changes.first().position
							showToolbar = position.x < thresholdX && position.y > screenHeight - thresholdY
						}
					}
				}
			}
	) {
		var showSidebar by remember { mutableStateOf(true) }
		var showSettings by remember { mutableStateOf(false) }
		Box(
			modifier = Modifier.fillMaxSize()
		) {
			var currentPageRoute by remember { mutableStateOf(PageRoute.FlexButton) }
			val sidebarOffsetX by animateDpAsState(
				targetValue = if (showSidebar) Dp.Hairline else -(220.dp)
			)
			Column(
				modifier = Modifier
					.width(220.dp)
					.fillMaxHeight()
					.background(MaterialTheme.colorScheme.surfaceContainerLow)
					.padding(horizontal = 8.dp)
					.offset(x = sidebarOffsetX)
			) {
				Column(
					modifier = Modifier
						.fillMaxWidth()
						.height(110.dp)
						.padding(top = 20.dp),
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.Center
				) {
					Text(
						text = "Flex UI",
						fontSize = 18.sp,
						color = MaterialTheme.colorScheme.onSurface,
						fontWeight = FontWeight.Medium
					)
					Text(
						text = VERSION_NAME,
						fontSize = 13.sp,
						color = MaterialTheme.colorScheme.onSurface
					)
				}
				Spacer(modifier = Modifier.height(8.dp))
				Column(
					modifier = Modifier
						.fillMaxWidth()
						.weight(1f)
						.padding(bottom = 4.dp)
				) {
					PageRoute.entries.forEachIndexed { index, it ->
						if (index != 0) {
							Spacer(modifier = Modifier.height(4.dp))
						}
						ComponentItem(
							pageRoute = it,
							value = currentPageRoute,
							onValueChange = { currentPageRoute = it }
						)
					}
				}
			}
			Scaffold(
				modifier = Modifier
					.fillMaxSize()
					.padding(start = sidebarOffsetX + 220.dp),
				topBar = {
					TopAppBar(
						title = { Text(text = currentPageRoute.name) },
						actions = {
							var refreshRate by remember { mutableLongStateOf(-1) }
							LaunchedEffect(Unit) {
								var lastFrameTime = 0L
								var total = 0L
								while (true) {
									withFrameNanos { currentFrameTime ->
										if (lastFrameTime != 0L) {
											total += currentFrameTime - lastFrameTime
											if (total > 500_000_000) {
												total %= 500_000_000
												refreshRate = 1_000_000_000 / (currentFrameTime - lastFrameTime)
											}
										}
										lastFrameTime = currentFrameTime
									}
								}
							}
							FlexButton(
								text = "${if (refreshRate == -1L) "NaN" else refreshRate} FPS",
								modifier = Modifier
									.width(90.dp),
								colorType = when {
									refreshRate >= 60 -> FlexColorType.Success
									refreshRate >= 30 -> FlexColorType.Warning
									else -> FlexColorType.Error
								},
								sizeType = FlexSizeType.Small
							)
							Spacer(modifier = Modifier.width(if (FlexPlatform.isMobile) 8.dp else 70.dp))
							if (FlexPlatform.isMobile) {
								FlexButton(
									icon = if (showSidebar) Icons.Outlined.PlaylistRemove else Icons.AutoMirrored.Outlined.PlaylistPlay,
									colorType = if (showSidebar) FlexColorType.Default else FlexColorType.Primary,
									buttonType = FlexButtonType.Primary
								) {
									showSidebar = !showSidebar
								}
								Spacer(modifier = Modifier.width(8.dp))
								FlexButton(
									icon = if (LocalDarkTheme.current) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
									buttonType = FlexButtonType.Primary,
									iconRotation = if (LocalDarkTheme.current) -90f else 0f
								) {
									FlexThemeState.darkTheme = !FlexThemeState.darkTheme
								}
							}
						}
					)
				}
			) {
				Column(
					modifier = Modifier
						.fillMaxSize()
						.padding(it)
				) {
					when (currentPageRoute) {
						PageRoute.FlexButton -> FlexButtonPage()
						PageRoute.FlexRadioGroup -> FlexRadioPage()
						PageRoute.FlexInput -> FlexInputPage()
					}
				}
			}
			val settingsOffsetX by animateDpAsState(
				targetValue = if (showSettings) Dp.Hairline else 380.dp
			)
			if (showSettings) {
				Box(
					modifier = Modifier
						.fillMaxSize()
						.clickable(
							interactionSource = null,
							indication = null,
							onClick = {
								showSettings = false
							}
						)
				)
			}
			Scaffold(
				modifier = Modifier
					.offset(x = settingsOffsetX)
					.width(380.dp)
					.fillMaxHeight()
					.shadow(elevation = 12.dp)
					.align(Alignment.CenterEnd),
				topBar = {
					TopAppBar(
						title = { Text("Settings") },
						colors = TopAppBarDefaults.topAppBarColors(
							containerColor = MaterialTheme.colorScheme.surfaceContainerLow
						)
					)
				},
				containerColor = MaterialTheme.colorScheme.surfaceContainerLow
			) {
				SettingsPage(it)
			}
		}
		if (!FlexPlatform.isMobile) {
			val toolbarOffsetX by animateDpAsState(
				targetValue = if (showToolbar) Dp.Hairline else -(48.dp)
			)
			Column(
				modifier = Modifier
					.align(Alignment.BottomStart)
					.padding(
						start = 8.dp,
						bottom = 8.dp
					)
					.offset(x = toolbarOffsetX)
			) {
				val themeIconRotation by animateFloatAsState(
					targetValue = if (LocalDarkTheme.current) -90f else 0f
				)
				FlexButton(
					icon = if (LocalDarkTheme.current) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
					buttonType = FlexButtonType.Primary,
					iconRotation = themeIconRotation,
					scaleEffect = true
				) {
					FlexThemeState.darkTheme = !FlexThemeState.darkTheme
				}
				Spacer(modifier = Modifier.height(8.dp))
				FlexButton(
					icon = Icons.Outlined.Settings,
					buttonType = FlexButtonType.Primary,
					scaleEffect = true
				) {
					showSettings = true
				}
				Spacer(modifier = Modifier.height(8.dp))
				FlexButton(
					icon = when (showSidebar) {
						true -> Icons.Outlined.PlaylistRemove
						false -> Icons.AutoMirrored.Outlined.PlaylistPlay
					},
					colorType = if (showSidebar) FlexColorType.Primary else FlexColorType.Default,
					buttonType = FlexButtonType.Primary,
					scaleEffect = true
				) {
					showSidebar = !showSidebar
				}
			}
		}
		VersionType()
	}
}

enum class PageRoute {
	FlexButton,
	FlexRadioGroup,
	FlexInput
}

@Composable
private fun ComponentItem(
	pageRoute: PageRoute,
	value: PageRoute,
	onValueChange: (PageRoute) -> Unit,
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.height(40.dp)
			.clip(RoundedCornerShape(10.dp))
			.background(
				color = if (value == pageRoute) MaterialTheme.colorScheme.surfaceContainerHighest else Color.Transparent,
				shape = RoundedCornerShape(10.dp)
			)
			.clickable {
				onValueChange(pageRoute)
			}
			.padding(horizontal = 16.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Text(
			text = pageRoute.name,
			modifier = Modifier.weight(1f),
			fontSize = 15.sp,
			color = MaterialTheme.colorScheme.onSurface,
			lineHeight = 15.sp,
			maxLines = 1,
			overflow = TextOverflow.Ellipsis
		)
		if (value == pageRoute) {
			Icon(
				imageVector = Icons.Rounded.Circle,
				contentDescription = null,
				modifier = Modifier.size(10.dp),
				tint = MaterialTheme.colorScheme.onSurface
			)
		}
	}
}

enum class VersionType {
	
	RELEASE,
	
	DEBUG,
	
	EXP
}

@Composable
private fun BoxScope.VersionType() {
	Box(
		modifier = Modifier
			.align(Alignment.TopEnd)
			.offset(
				x = ((75f * sqrt(2f)) / 2f - (75f / 2f) * (5f / 6f)).dp,
				y = ((75f / 2f) * (5f / 6f) - 25f * sqrt(2f) / 4f).dp
			)
			.rotate(45f)
			.size(
				width = (75 * sqrt(2f)).dp,
				height = (sqrt(2f) / 2 * 25).dp
			)
			.background(
				color = when (versionType) {
					VersionType.RELEASE -> Color(0xCC52C41A)
					VersionType.DEBUG -> Color(0xCCF9AC13)
					VersionType.EXP -> Color(0xCCF1211C)
				}
			),
		contentAlignment = Alignment.Center
	) {
		Text(
			text = versionType.toString(),
			fontSize = 14.sp,
			fontWeight = FontWeight.Bold,
			color = Color.White,
			lineHeight = 14.sp
		)
	}
}