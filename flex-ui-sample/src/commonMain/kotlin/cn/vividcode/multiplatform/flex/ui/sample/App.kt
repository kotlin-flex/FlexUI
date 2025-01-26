package cn.vividcode.multiplatform.flex.ui.sample

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.rotate
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
import cn.vividcode.multiplatform.flex.ui.theme.FlexPlatform
import cn.vividcode.multiplatform.flex.ui.theme.FlexThemeState
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme
import kotlin.math.sqrt

private val ItemWidth = 220.dp

private const val VERSION_NAME = "v1.0.0-exp-04"

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
		var itemOffsetX by remember { mutableStateOf(Dp.Hairline) }
		Box(
			modifier = Modifier.fillMaxSize()
		) {
			var currentFlexType by remember { mutableStateOf(FlexType.FlexButton) }
			val targetItemOffsetX by animateDpAsState(itemOffsetX)
			Column(
				modifier = Modifier
					.width(ItemWidth)
					.fillMaxHeight()
					.background(MaterialTheme.colorScheme.surfaceContainerLow)
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
						.widthIn(min = ItemWidth)
						.fillMaxSize()
						.padding(bottom = 60.dp)
				) {
					FlexType.entries.forEach { flexPage ->
						ComponentItem(
							flexType = flexPage,
							value = currentFlexType,
							onValueChange = {
								currentFlexType = it
							}
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
								Text(
									text = currentFlexType.name,
									fontSize = 20.sp,
									fontWeight = FontWeight.Medium,
									color = MaterialTheme.colorScheme.onSurface,
									lineHeight = 20.sp
								)
							}
						},
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
									icon = if (itemOffsetX == Dp.Hairline) Icons.Outlined.PlaylistRemove else Icons.AutoMirrored.Outlined.PlaylistPlay,
									colorType = if (itemOffsetX == Dp.Hairline) FlexColorType.Default else FlexColorType.Primary,
									buttonType = FlexButtonType.Primary
								) {
									itemOffsetX = if (itemOffsetX == Dp.Hairline) -ItemWidth else Dp.Hairline
								}
								Spacer(modifier = Modifier.width(8.dp))
								FlexButton(
									icon = if (LocalDarkTheme.current) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
									buttonType = FlexButtonType.Primary,
									iconRotation = if (LocalDarkTheme.current) -90f else 0f
								) {
									FlexThemeState.darkTheme = !FlexThemeState.darkTheme!!
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
					when (currentFlexType) {
						FlexType.FlexButton -> FlexButtonPage()
						FlexType.FlexRadioGroup -> FlexRadioPage()
						FlexType.FlexInput -> FlexInputPage()
					}
				}
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
					iconRotation = themeIconRotation
				) {
					FlexThemeState.darkTheme = !FlexThemeState.darkTheme!!
				}
				Spacer(modifier = Modifier.height(8.dp))
				FlexButton(
					icon = when (itemOffsetX == Dp.Hairline) {
						true -> Icons.Outlined.PlaylistRemove
						false -> Icons.AutoMirrored.Outlined.PlaylistPlay
					},
					colorType = if (itemOffsetX == Dp.Hairline) FlexColorType.Default else FlexColorType.Primary,
					buttonType = FlexButtonType.Primary
				) {
					itemOffsetX = if (itemOffsetX == Dp.Hairline) -(ItemWidth) else Dp.Hairline
				}
			}
		}
		VersionType()
	}
}

enum class FlexType {
	FlexButton,
	FlexRadioGroup,
	FlexInput
}

@Composable
private fun ComponentItem(
	flexType: FlexType,
	value: FlexType,
	onValueChange: (FlexType) -> Unit,
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.height(40.dp)
			.clip(RoundedCornerShape(10.dp))
			.background(
				color = if (value == flexType) MaterialTheme.colorScheme.surfaceContainerHighest else Color.Transparent,
				shape = RoundedCornerShape(10.dp)
			)
			.clickable {
				onValueChange(flexType)
			}
			.padding(horizontal = 16.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Text(
			text = flexType.name,
			modifier = Modifier.weight(1f),
			fontSize = 15.sp,
			color = MaterialTheme.colorScheme.onSurface,
			lineHeight = 15.sp,
			maxLines = 1,
			overflow = TextOverflow.Ellipsis
		)
		if (value == flexType) {
			Box(
				modifier = Modifier
					.size(10.dp)
					.background(
						color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
						shape = CircleShape
					)
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