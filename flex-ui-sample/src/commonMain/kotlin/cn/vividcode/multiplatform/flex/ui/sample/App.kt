package cn.vividcode.multiplatform.flex.ui.sample

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.PlaylistPlay
import androidx.compose.material.icons.outlined.*
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
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButton
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButtonType
import cn.vividcode.multiplatform.flex.ui.sample.page.*
import cn.vividcode.multiplatform.flex.ui.theme.FlexPlatform
import cn.vividcode.multiplatform.flex.ui.theme.FlexThemeState
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme
import cn.vividcode.multiplatform.flex.ui.utils.multiplatform
import kotlin.math.sqrt

private const val VERSION_NAME = "v1.0.0-exp-07"

private val versionType = VersionType.EXP

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
	var showToolbar by remember { mutableStateOf(false) }
	var screenHeight by remember { mutableStateOf(0) }
	var toolbarSize by remember { mutableStateOf(IntSize.Zero) }
	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.surface)
			.multiplatform(FlexPlatform.Web, FlexPlatform.Desktop) {
				this.onGloballyPositioned {
					screenHeight = it.size.height
				}.pointerInput(Unit) {
					awaitPointerEventScope {
						if (toolbarSize == IntSize.Zero) return@awaitPointerEventScope
						val thresholdX = toolbarSize.width * 2 + 16.dp.toPx()
						val thresholdY = toolbarSize.height + 36.dp.toPx() + 16.dp.toPx()
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
		var showPreviews by remember { mutableStateOf(false) }
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
					PageRoute.entries.fastForEachIndexed { index, it ->
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
							var frameRate by remember { mutableLongStateOf(0L) }
							frameRateListener {
								frameRate = it
							}
							val frameText by remember(frameRate) {
								derivedStateOf { "$frameRate FPS" }
							}
							FlexButton(
								text = frameText,
								modifier = Modifier
									.width(90.dp),
								colorType = when {
									frameRate >= 60 -> FlexColorType.Primary
									frameRate >= 30 -> FlexColorType.Secondary
									else -> FlexColorType.Error
								}
							)
							Spacer(modifier = Modifier.width(if (FlexPlatform.isMobile) 8.dp else 70.dp))
							if (FlexPlatform.isMobile) {
								FlexButton(
									icon = if (showSidebar) Icons.Outlined.PlaylistRemove else Icons.AutoMirrored.Outlined.PlaylistPlay,
									colorType = if (showSidebar) FlexColorType.Primary else FlexColorType.InverseSurface,
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
						.padding(horizontal = 12.dp)
				) {
					when (currentPageRoute) {
						PageRoute.FlexButton -> FlexButtonPage()
						PageRoute.FlexRadio -> FlexRadioPage()
						PageRoute.FlexInput -> FlexInputPage()
						PageRoute.FlexSwitch -> FlexSwitchPage()
						PageRoute.FlexSlider -> FlexSliderPage()
						PageRoute.FlexSelect -> FlexSelectPage()
					}
				}
			}
			if (showSettings || showPreviews) {
				Box(
					modifier = Modifier
						.fillMaxSize()
						.clickable(
							interactionSource = null,
							indication = null,
							onClick = {
								showSettings = false
								showPreviews = false
							}
						)
				)
			}
			val settingsOffsetX by animateDpAsState(
				targetValue = if (showSettings) Dp.Hairline else 400.dp
			)
			Scaffold(
				modifier = Modifier
					.offset(x = settingsOffsetX)
					.width(400.dp)
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
			
			val previewsOffsetX by animateDpAsState(
				targetValue = if (showPreviews) Dp.Hairline else 700.dp
			)
			Scaffold(
				modifier = Modifier
					.offset(x = previewsOffsetX)
					.width(700.dp)
					.fillMaxHeight()
					.shadow(elevation = 12.dp)
					.align(Alignment.CenterEnd),
				topBar = {
					TopAppBar(
						title = { Text("Previews") },
						colors = TopAppBarDefaults.topAppBarColors(
							containerColor = MaterialTheme.colorScheme.surfaceContainerLow
						)
					)
				},
				containerColor = MaterialTheme.colorScheme.surfaceContainerLow
			) {
				PreviewsPage(it)
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
					.multiplatform(FlexPlatform.Web, FlexPlatform.Desktop) {
						this.onGloballyPositioned {
							toolbarSize = it.size
						}
					}
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
					icon = Icons.Outlined.Preview,
					buttonType = FlexButtonType.Primary,
					colorType = if (showPreviews) FlexColorType.Primary else FlexColorType.InverseSurface,
					scaleEffect = true
				) {
					showPreviews = !showPreviews
					showSettings = false
				}
				Spacer(modifier = Modifier.height(8.dp))
				FlexButton(
					icon = Icons.Outlined.Settings,
					buttonType = FlexButtonType.Primary,
					colorType = if (showSettings) FlexColorType.Primary else FlexColorType.InverseSurface,
					scaleEffect = true
				) {
					showSettings = !showSettings
					showPreviews = false
				}
				Spacer(modifier = Modifier.height(8.dp))
				FlexButton(
					icon = when (showSidebar) {
						true -> Icons.Outlined.PlaylistRemove
						false -> Icons.AutoMirrored.Outlined.PlaylistPlay
					},
					colorType = if (showSidebar) FlexColorType.Primary else FlexColorType.InverseSurface,
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
	FlexRadio,
	FlexInput,
	FlexSwitch,
	FlexSlider,
	FlexSelect
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

@Composable
private fun frameRateListener(callback: (frame: Long) -> Unit) {
	LaunchedEffect(Unit) {
		var lastFrameTime = 0L
		var lastUpdateTime = 0L
		val frameTimes = ArrayDeque<Long>(10)
		while (true) {
			withFrameNanos { currentFrameTime ->
				if (lastFrameTime != 0L) {
					val frameDuration = currentFrameTime - lastFrameTime
					
					if (frameTimes.size >= 10) {
						frameTimes.removeFirst()
					}
					frameTimes += frameDuration
					if (currentFrameTime - lastUpdateTime >= 500_000_000L) {
						val averageFrameTime = frameTimes.average().toLong()
						callback(1_000_000_000L / averageFrameTime)
						lastUpdateTime = currentFrameTime
					}
				}
				lastFrameTime = currentFrameTime
			}
		}
	}
}