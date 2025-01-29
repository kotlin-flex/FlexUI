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

@Composable
fun RowScope.Code2(
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
		val stringStyle = StringStyle
		val keywordStyle = KeywordStyle
		val symbolStyle = SymbolStyle
		val uppercaseWordStyle = UppercaseWordStyle
		val codeString by remember(code, LocalDarkTheme.current) {
			val keywordIndices = keywordRegexList.flatMap { regex ->
				regex.findAll(code).flatMap { it.range.toList() }
			}
			val symbolIndices = symbolRegexList.flatMap { regex ->
				regex.findAll(code).flatMap { it.range.toList() }
			}
			val uppercaseIndices = uppercaseWordRegex.findAll(code).flatMap {
				it.range.toList()
			}
			val stringIndices = stringRegex.findAll(code).flatMap {
				val list = it.range.toList()
				if (list.size < 2) list else list.subList(1, list.size - 1)
			}
			val charIndices = charRegex.findAll(code).flatMap {
				val list = it.range.toList()
				if (list.size < 2) list else list.subList(1, list.size - 1)
			}
			val quotationMarksIndices = quotationMarksRegex.findAll(code).flatMap {
				it.range.toList()
			}
			val codeString = buildAnnotatedString {
				code.forEachIndexed { index, char ->
					when (index) {
						in stringIndices -> {
							withStyle(stringStyle) {
								append(char)
							}
						}
						
						in charIndices -> {
							withStyle(stringStyle) {
								append(char)
							}
						}
						
						in quotationMarksIndices -> {
							withStyle(stringStyle) {
								append(char)
							}
						}
						
						in keywordIndices -> {
							withStyle(keywordStyle) {
								append(char)
							}
						}
						
						in symbolIndices -> {
							withStyle(symbolStyle) {
								append(char)
							}
						}
						
						in uppercaseIndices -> {
							withStyle(uppercaseWordStyle) {
								append(char)
							}
						}
						
						else -> {
							when {
								char == '\t' -> append(" ".repeat(4))
								else -> append(char)
							}
						}
					}
				}
			}
			mutableStateOf(codeString)
		}
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.height(36.dp)
				.padding(horizontal = 6.dp),
			verticalAlignment = Alignment.CenterVertically,
		) {
			FlexButton(
				text = "Kotlin",
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
					text = codeString.lines().mapIndexed { index, _ -> index + 1 }.joinToString("\n"),
					modifier = Modifier
						.fillMaxHeight()
						.padding(4.dp)
						.verticalScroll(verticalScrollState)
						.padding(
							start = 8.dp,
							top = 3.dp,
							bottom = 8.dp,
							end = 8.dp
						),
					fontSize = 13.sp,
					lineHeight = 24.sp,
					color = TextColor,
					textAlign = TextAlign.Center,
				)
				VerticalDivider()
				Text(
					text = codeString,
					modifier = Modifier
						.weight(1f)
						.fillMaxHeight()
						.padding(4.dp)
						.horizontalScroll(horizontalScrollState)
						.verticalScroll(verticalScrollState)
						.padding(
							start = 8.dp,
							top = 3.dp,
							bottom = 8.dp,
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

private val keywordRegexList by lazy {
	listOf(
		"abstract", "actual", "annotation", "as", "break", "by", "catch", "class",
		"companion", "const", "constructor", "continue", "crossinline", "data",
		"delegate", "do", "dynamic", "else", "enum", "expect", "external", "false",
		"final", "finally", "for", "fun", "get", "if", "import", "in", "infix",
		"init", "inline", "inner", "interface", "internal", "is", "lateinit",
		"noinline", "null", "object", "open", "operator", "out", "override", "package",
		"param", "private", "property", "protected", "public", "receiver", "reified",
		"return", "sealed", "set", "setparam", "super", "suspend", "tailrec", "this",
		"throw", "true", "try", "typealias", "typeof", "val", "var", "vararg", "when",
		"where", "while", "in"
	).map { "\\b${Regex.escape(it)}\\b".toRegex() }
}

private val symbolRegexList by lazy {
	listOf(
		"+", "-", "*", "/", "%",
		"=", "+=", "-=", "*=", "/=", "%=",
		"==", "!=", ">", "<", ">=", "<=",
		"&&", "||", "!",
		"++", "--",
		":", "..", "..<",
		"?", "!!", "->", "@"
	).map { Regex.escape(it).toRegex() }
}

private val quotationMarksRegex = "['\"]".toRegex()

private val uppercaseWordRegex = "\\b[A-Z][a-zA-Z]*\\b".toRegex()

private val stringRegex = "\"([^\"]*)\"".toRegex()

private val charRegex = "'([^\"]*)'".toRegex()

private val numberRegex by lazy {
	"\\d[f|L]+".toRegex()
}

private val StringStyle
	@Composable
	get() = SpanStyle(
		color = if (LocalDarkTheme.current) Color(0xFF95CCFF) else Color(0xFF0B2256)
	)

private val KeywordStyle
	@Composable
	get() = SpanStyle(
		color = if (LocalDarkTheme.current) Color(0xFFFC635F) else Color(0xFFC10A23)
	)

private val SymbolStyle
	@Composable
	get() = SpanStyle(
		color = if (LocalDarkTheme.current) Color(0xFFFC635F) else Color(0xFFC10A23)
	)

private val UppercaseWordStyle
	@Composable
	get() = SpanStyle(
		color = if (LocalDarkTheme.current) Color(0xFFC692FE) else Color(0xFF5220AB)
	)

private val TextColor
	@Composable
	get() = if (LocalDarkTheme.current) Color(0xFFECF4FB) else Color(0xFF181A1E)