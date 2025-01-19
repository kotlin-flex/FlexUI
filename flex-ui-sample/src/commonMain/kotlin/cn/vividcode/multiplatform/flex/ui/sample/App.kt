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
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.expends.multiplatform
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButton
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButtonType
import cn.vividcode.multiplatform.flex.ui.sample.foundation.FlexButtonPage
import cn.vividcode.multiplatform.flex.ui.sample.foundation.FlexRadioPage
import cn.vividcode.multiplatform.flex.ui.theme.FlexPlatform
import cn.vividcode.multiplatform.flex.ui.theme.FlexThemeState
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme
import kotlin.math.min
import kotlin.math.sqrt

private val ItemWidth = 220.dp

private const val VERSION_NAME = "v1.0.0-exp-04"

private const val VERSION_TYPE = "EXP"

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
		var itemOffsetX by remember { mutableStateOf(Dp.Hairline) }
		Box(
			modifier = Modifier.fillMaxSize()
		) {
			var currentFlexPage by remember { mutableStateOf(FlexPage.FlexButton) }
			val targetItemOffsetX by animateDpAsState(
				targetValue = itemOffsetX
			)
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
					FlexPage.entries.forEach { flexPage ->
						ComposeItem(
							flexPage = flexPage,
							value = currentFlexPage,
							onValueChange = {
								currentFlexPage = it
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
									text = currentFlexPage.name,
									fontSize = 20.sp,
									fontWeight = FontWeight.Medium,
									color = MaterialTheme.colorScheme.onSurface,
									lineHeight = 20.sp
								)
							}
						},
						actions = {
							var frame by remember { mutableLongStateOf(-1) }
							LaunchedEffect(Unit) {
								var lastNanos = 0L
								var total = 0L
								while (true) {
									withFrameNanos {
										if (lastNanos != 0L) {
											total += it - lastNanos
											if (total > 500_000_000) {
												total -= 500_000_000
												frame = min(1_000_000_000 / (it - lastNanos), 120L)
											}
										}
										lastNanos = it
									}
								}
							}
							FlexButton(
								text = "${if (frame == -1L) "NaN" else frame} FPS",
								modifier = Modifier
									.width(90.dp),
								colorType = when {
									frame >= 90 -> FlexColorType.Success
									frame >= 60 -> FlexColorType.Warning
									else -> FlexColorType.Error
								},
								sizeType = FlexSizeType.Small
							)
							Spacer(modifier = Modifier.width(if (FlexPlatform.isMobile) 8.dp else 70.dp))
							if (FlexPlatform.isMobile) {
								FlexButton(
									icon = when (itemOffsetX == Dp.Hairline) {
										true -> Icons.Outlined.PlaylistRemove
										false -> Icons.AutoMirrored.Outlined.PlaylistPlay
									},
									colorType = if (itemOffsetX == Dp.Hairline) FlexColorType.Default else FlexColorType.Primary,
									buttonType = FlexButtonType.Primary,
									cornerType = FlexCornerType.Large
								) {
									itemOffsetX = if (itemOffsetX == Dp.Hairline) -ItemWidth else Dp.Hairline
								}
								val themeIconRotation by animateFloatAsState(
									targetValue = if (LocalDarkTheme.current) -90f else 0f
								)
								Spacer(modifier = Modifier.width(8.dp))
								FlexButton(
									icon = if (LocalDarkTheme.current) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
									buttonType = FlexButtonType.Primary,
									iconRotation = themeIconRotation,
									cornerType = FlexCornerType.Large
								) {
									FlexThemeState.darkTheme = !FlexThemeState.darkTheme!!
								}
							}
						}
					)
				}
			) {
				Box(
					modifier = Modifier
						.fillMaxSize()
						.padding(it)
				) {
					when (currentFlexPage) {
						FlexPage.FlexButton -> FlexButtonPage()
						FlexPage.FlexRadioGroup -> FlexRadioPage()
					}
				}
			}
		}
		if (!FlexPlatform.isMobile) {
			val foldOffsetX by animateDpAsState(
				targetValue = if (showFoldButton) Dp.Hairline else -(60.dp)
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
				sizeType = FlexSizeType.Large,
				buttonType = FlexButtonType.Primary,
				cornerType = FlexCornerType.Large,
			) {
				itemOffsetX = if (itemOffsetX == Dp.Hairline) -(ItemWidth) else Dp.Hairline
			}
			val switchThemeOffsetX by animateDpAsState(
				targetValue = if (showSwitchThemeButton) Dp.Hairline else 60.dp
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
				sizeType = FlexSizeType.Large,
				buttonType = FlexButtonType.Primary,
				cornerType = FlexCornerType.Large,
				iconRotation = themeIconRotation
			) {
				FlexThemeState.darkTheme = !FlexThemeState.darkTheme!!
			}
		}
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
				.background(color = Color(0xCCF1211C)),
			contentAlignment = Alignment.Center
		) {
			Text(
				text = VERSION_TYPE,
				fontSize = 16.sp,
				fontWeight = FontWeight.Bold,
				color = Color.White,
				lineHeight = 16.sp
			)
		}
	}
}

enum class FlexPage {
	FlexButton,
	FlexRadioGroup
}

@Composable
private fun ComposeItem(
	flexPage: FlexPage,
	value: FlexPage,
	onValueChange: (FlexPage) -> Unit,
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.height(40.dp)
			.clip(RoundedCornerShape(10.dp))
			.background(
				color = if (value == flexPage) MaterialTheme.colorScheme.surfaceContainerHighest else Color.Transparent,
				shape = RoundedCornerShape(10.dp)
			)
			.clickable {
				onValueChange(flexPage)
			}
			.padding(horizontal = 16.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Text(
			text = flexPage.name,
			modifier = Modifier.weight(1f),
			fontSize = 15.sp,
			color = MaterialTheme.colorScheme.onSurface,
			lineHeight = 15.sp,
			maxLines = 1,
			overflow = TextOverflow.Ellipsis
		)
		if (value == flexPage) {
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