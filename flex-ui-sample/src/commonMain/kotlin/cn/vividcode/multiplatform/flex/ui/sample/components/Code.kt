package cn.vividcode.multiplatform.flex.ui.sample.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.DoneAll
import androidx.compose.material.icons.rounded.RestartAlt
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButton
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButtonIconPosition
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButtonType
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 代码框展示组件
 */
@Composable
fun RowScope.Code(
	code: String,
) {
	Column(
		modifier = Modifier
			.weight(0.8f)
			.fillMaxHeight()
			.border(
				width = 1.dp,
				color = MaterialTheme.colorScheme.outlineVariant,
				shape = RoundedCornerShape(8.dp)
			)
	) {
		val darkTheme = LocalDarkTheme.current
		val spanStyles by remember(darkTheme) {
			derivedStateOf { colorCodings.map { it.getSpanStyle(darkTheme) } }
		}
		val indices by remember(code) {
			derivedStateOf {
				colorCodings.map { colorCoding ->
					colorCoding.regex.findAll(code).flatMap { it.range.toList() }
				}
			}
		}
		val codeString by remember(code, indices, darkTheme) {
			derivedStateOf {
				buildAnnotatedString {
					code.forEachIndexed { index, char ->
						val typeIndex = indices.indexOfFirst { index in it }
						when {
							typeIndex != -1 -> {
								withStyle(spanStyles[typeIndex]) {
									append(char)
								}
							}
							
							char == '\t' -> append(" ".repeat(4))
							else -> append(char)
						}
					}
				}
			}
		}
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.height(36.dp)
				.padding(horizontal = 6.dp),
			verticalAlignment = Alignment.CenterVertically,
		) {
			FlexButton(
				text = "Kotlin v${KotlinVersion.CURRENT}",
				sizeType = FlexSizeType.ExtraSmall,
				buttonType = FlexButtonType.Link,
				scaleEffect = false
			)
			Spacer(modifier = Modifier.weight(1f))
			val clipboardManager = LocalClipboardManager.current
			var copied by remember { mutableStateOf(false) }
			val coroutineScope = rememberCoroutineScope()
			FlexButton(
				text = if (copied) "Copied!" else "Copy",
				icon = if (copied) Icons.Rounded.DoneAll else Icons.Rounded.ContentCopy,
				sizeType = FlexSizeType.ExtraSmall,
				buttonType = FlexButtonType.Text,
				iconPosition = FlexButtonIconPosition.Start,
				enabled = !copied
			) {
				copied = true
				clipboardManager.setText(codeString)
				coroutineScope.launch {
					delay(5000)
					copied = false
				}
			}
		}
		HorizontalDivider()
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.weight(1f)
		) {
			val horizontalScrollState = rememberScrollState()
			val verticalScrollState = rememberScrollState()
			Row(
				modifier = Modifier
					.fillMaxSize()
			) {
				Text(
					text = (1 .. codeString.lines().size).joinToString("\n"),
					modifier = Modifier
						.width(36.dp)
						.fillMaxHeight()
						.padding(vertical = 4.dp)
						.verticalScroll(verticalScrollState)
						.padding(vertical = 4.dp),
					fontSize = 13.sp,
					lineHeight = 24.sp,
					color = TextColor.copy(alpha = 0.7f),
					textAlign = TextAlign.Center,
				)
				VerticalDivider()
				Text(
					text = codeString,
					modifier = Modifier
						.weight(1f)
						.fillMaxHeight()
						.padding(4.dp)
						.horizontalScroll(
							state = horizontalScrollState,
							enabled = !verticalScrollState.isScrollInProgress
						)
						.verticalScroll(
							state = verticalScrollState,
							enabled = !horizontalScrollState.isScrollInProgress
						)
						.padding(
							horizontal = 8.dp,
							vertical = 3.dp
						),
					color = TextColor,
					fontSize = 14.sp,
					lineHeight = 24.sp,
				)
			}
			val isStartTop by remember {
				derivedStateOf { horizontalScrollState.value == 0 && verticalScrollState.value == 0 }
			}
			val alpha by animateFloatAsState(
				targetValue = if (isStartTop) 0f else 1f
			)
			val coroutineScope = rememberCoroutineScope()
			FlexButton(
				icon = Icons.Rounded.RestartAlt,
				modifier = Modifier
					.align(Alignment.BottomEnd)
					.padding(12.dp)
					.alpha(alpha),
				sizeType = FlexSizeType.Small,
				cornerType = FlexCornerType.Circle,
				buttonType = FlexButtonType.Filled,
				enabled = !isStartTop
			) {
				if (!horizontalScrollState.isScrollInProgress) {
					coroutineScope.launch {
						horizontalScrollState.animateScrollTo(0)
					}
				}
				if (!verticalScrollState.isScrollInProgress) {
					coroutineScope.launch {
						verticalScrollState.animateScrollTo(0)
					}
				}
			}
		}
	}
}

private val TextColor
	@Composable
	get() = if (LocalDarkTheme.current) Color(0xFFECF4FB) else Color(0xFF181A1E)


private val colorCodings = arrayOf(
	StringColorCoding,
	KeywordColorCoding,
	KeywordValueColorCoding,
	OperatorSymbolColorCoding,
	NumberColorCoding,
	UppercaseWordColorCoding
)

private sealed class ColorCoding {
	
	abstract val regex: Regex
	
	abstract val lightColor: Color
	
	abstract val darkColor: Color
	
	fun getSpanStyle(darkTheme: Boolean) = SpanStyle(
		color = if (darkTheme) darkColor else lightColor
	)
}

/**
 * 字符串
 */
private data object StringColorCoding : ColorCoding() {
	
	override val regex = "\"\"\"[\\s\\S]*?\"\"\"|\"([^\"\\\\]|\\\\.)*\"|'([^'\\\\]|\\\\.)*'".toRegex()
	
	override val lightColor: Color = Color(0xFF0B2256)
	
	override val darkColor = Color(0xFF95CCFF)
}

/**
 * 关键字
 */
private data object KeywordColorCoding : ColorCoding() {
	
	private val keywords = arrayOf(
		"abstract", "actual", "annotation", "as", "break", "by", "catch", "class",
		"companion", "const", "constructor", "continue", "crossinline", "data",
		"delegate", "do", "dynamic", "else", "enum", "expect", "external",
		"final", "finally", "for", "fun", "get", "if", "import", "in", "infix",
		"init", "inline", "inner", "interface", "internal", "is", "lateinit",
		"noinline", "object", "open", "operator", "out", "override", "package",
		"param", "private", "property", "protected", "public", "receiver", "reified",
		"return", "sealed", "set", "super", "suspend", "tailrec", "this",
		"throw", "try", "typealias", "typeof", "val", "var", "vararg", "when",
		"where", "while", "in"
	)
	
	override val regex = "\\b(${keywords.joinToString("|")})\\b".toRegex()
	
	override val lightColor = Color(0xFFC10A23)
	
	override val darkColor = Color(0xFFFC635F)
}

/**
 * 关键字 值
 */
private data object KeywordValueColorCoding : ColorCoding() {
	
	override val regex = "\\b(true|false|null)\\b".toRegex()
	
	override val lightColor = Color(0xFF093B9E)
	
	override val darkColor = Color(0xFF68B1FF)
}

/**
 * 运算符
 */
private data object OperatorSymbolColorCoding : ColorCoding() {
	
	override val regex = """\+\+|--|!=|==|>=|<=|\+=|-=|\*=|/=|%=|\.\.|\.\.<|&&|\|\||!!|->|[@:+*/%=<>&|\-!?]""".toRegex()
	
	override val lightColor = Color(0xFFC10A23)
	
	override val darkColor = Color(0xFFFC635F)
}

/**
 * 数字
 */
private data object NumberColorCoding : ColorCoding() {
	
	override val regex = "-?\\d+(\\.\\d+)?[fFL]?".toRegex()
	
	override val lightColor = Color(0xFF093B9E)
	
	override val darkColor = Color(0xFF68B1FF)
}

/**
 * 大写
 */
private data object UppercaseWordColorCoding : ColorCoding() {
	
	override val regex = "\\b[A-Z][a-zA-Z]*\\b".toRegex()
	
	override val lightColor = Color(0xFF5220AB)
	
	override val darkColor = Color(0xFFC692FE)
}