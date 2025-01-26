package cn.vividcode.multiplatform.flex.ui.sample.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButton
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButtonType
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme
import kotlinx.coroutines.launch

@Composable
fun Code(
	methodName: String,
	variables: List<Any> = emptyList(),
	assigns: List<AssignT>,
) {
	Box(
		modifier = Modifier
			.width(320.dp)
			.fillMaxHeight()
			.background(
				color = MaterialTheme.colorScheme.surfaceContainer,
				shape = RoundedCornerShape(12.dp)
			)
	) {
		val horizontalScrollState = rememberScrollState()
		val verticalScrollState = rememberScrollState()
		val string = buildAnnotatedString {
			if (variables.isNotEmpty()) {
				variables.forEach {
					@Suppress("UNCHECKED_CAST")
					var codes = it as? List<CodeT>
					if (codes == null) {
						check(it is CodeT)
						codes = listOf(it)
					}
					codes.forEach {
						withStyle(it.spanStyle) {
							append(it.code)
						}
					}
					append("\n")
				}
			}
			withStyle(MethodStyle) {
				append(methodName)
			}
			withStyle(SymbolStyle) {
				append("(")
			}
			append("\n")
			assigns.forEachIndexed { index, parameter ->
				append("    ")
				withStyle(PropertyNameStyle) {
					append(parameter.name)
				}
				withStyle(SymbolStyle) {
					append(" = ")
				}
				parameter.codes.forEachIndexed { index, it ->
					withStyle(it.spanStyle) {
						append(it.code)
					}
				}
				if (index != assigns.lastIndex) {
					withStyle(SymbolStyle) {
						append(",")
					}
				}
				append("\n")
			}
			withStyle(SymbolStyle) {
				append(")")
			}
		}
		Text(
			text = string,
			modifier = Modifier
				.fillMaxSize()
				.padding(4.dp)
				.horizontalScroll(horizontalScrollState)
				.verticalScroll(verticalScrollState)
				.padding(
					horizontal = 12.dp,
					vertical = 7.dp
				),
			fontSize = 14.sp,
			lineHeight = 24.sp
		)
		val clipboardManager = LocalClipboardManager.current
		FlexButton(
			icon = Icons.Rounded.ContentCopy,
			modifier = Modifier
				.align(Alignment.TopEnd)
				.padding(
					top = 12.dp,
					end = 12.dp
				),
			sizeType = FlexSizeType.Small,
			buttonType = FlexButtonType.Primary
		) {
			clipboardManager.setText(string)
		}
		val isTop by remember {
			derivedStateOf { verticalScrollState.value == 0 }
		}
		val alpha by animateFloatAsState(
			targetValue = if (isTop) 0f else 1f
		)
		val coroutineScope = rememberCoroutineScope()
		FlexButton(
			icon = Icons.Rounded.KeyboardArrowUp,
			modifier = Modifier
				.align(Alignment.BottomEnd)
				.padding(
					bottom = 12.dp,
					end = 12.dp
				)
				.alpha(alpha),
			sizeType = FlexSizeType.Small,
			cornerType = FlexCornerType.Circle,
			buttonType = FlexButtonType.Primary,
			enabled = !isTop
		) {
			coroutineScope.launch {
				verticalScrollState.animateScrollTo(0)
			}
		}
	}
}

@Suppress("UNCHECKED_CAST")
infix fun String.assign(code: Any): AssignT = when (code) {
	is String -> AssignT(this, DoubleQuotesT + StringT(code) + DoubleQuotesT)
	is Number -> AssignT(this, NumberT(code))
	is Boolean -> AssignT(this, KeywordT(code.toString()))
	is CodeT -> AssignT(this, code)
	is List<*> -> {
		check(code.isNotEmpty())
		when (code[0]) {
			is CodeT -> {
				val codes = code as? List<CodeT>
				if (codes == null) {
					AssignT(this, code as CodeT)
				} else AssignT(this, codes)
			}
			
			is List<*> -> {
				val codes = mutableListOf<CodeT>()
				codes += MethodT("listOf") + LeftParenthesesT + LineFeed
				code.forEachIndexed { index, it ->
					codes += SpaceT(8)
					when (it) {
						is CodeT -> codes += it
						is List<*> -> codes += it as List<CodeT>
					}
					if (index != code.lastIndex) {
						codes += CommaT
					}
					codes += LineFeed
				}
				codes += SpaceT(4) + RightParenthesesT
				AssignT(this, codes)
			}
			
			else -> AssignT.Empty
		}
	}
	
	else -> AssignT.Empty
}

data class AssignT(
	val name: String,
	val codes: List<CodeT>,
) {
	constructor(name: String, code: CodeT) : this(name, listOf(code))
	
	companion object {
		val Empty = AssignT("", emptyList())
	}
}

sealed interface CodeT {
	
	val code: String
	
	val spanStyle: SpanStyle
		@Composable
		get
}

data class MethodT(
	val name: String,
) : CodeT {
	
	override val code = name
	
	override val spanStyle: SpanStyle
		@Composable
		get() = MethodStyle
}

abstract class SymbolT(
	override val code: String,
) : CodeT {
	
	override val spanStyle: SpanStyle
		@Composable
		get() = SymbolStyle
}

data object DotT : SymbolT(".")

data class SpaceT(
	val count: Int = 1,
) : SymbolT(" ".repeat(count))

data object LeftBraceT : SymbolT("{")

data object RightBraceT : SymbolT("}")

data object LeftParenthesesT : SymbolT("(")

data object RightParenthesesT : SymbolT(")")

data object LineFeed : SymbolT("\n")

data object CommaT : SymbolT(",")

data object EqualsT : SymbolT("=")

data object DoubleQuotesT : SymbolT("\"")

data class NumberT<T : Number>(
	val number: T,
) : CodeT {
	
	override val code = when (number) {
		is Float -> "${number}f"
		is Long -> "${number}L"
		else -> number.toString()
	}
	
	override val spanStyle: SpanStyle
		@Composable
		get() = NumberStyle
}

data class KeywordT(
	val keyword: String,
) : CodeT {
	
	override val code = keyword
	
	override val spanStyle: SpanStyle
		@Composable
		get() = KeywordStyle
}

data class VariableT(
	val name: String,
) : CodeT {
	
	override val code = name
	
	override val spanStyle: SpanStyle
		@Composable
		get() = VariableStyle
}

data class StringT(
	val string: String,
) : CodeT {
	
	override val code = string
	
	override val spanStyle: SpanStyle
		@Composable
		get() = StringStyle
}

data class ClassT<T>(
	val name: T,
) : CodeT {
	
	override val code = name.toString()
	
	override val spanStyle: SpanStyle
		@Composable
		get() = ClassStyle
}

data class ParameterT(
	val name: String,
) : CodeT {
	
	override val code = name
	
	override val spanStyle: SpanStyle
		@Composable
		get() = ParameterStyle
}

operator fun CodeT.plus(other: CodeT): List<CodeT> {
	return listOf(this, other)
}

operator fun List<CodeT>.plus(other: String): List<CodeT> {
	return this + other
}

private val MethodStyle: SpanStyle
	@Composable
	get() = if (LocalDarkTheme.current) MethodDarkStyle else MethodLightStyle

private val ClassStyle: SpanStyle
	@Composable
	get() = if (LocalDarkTheme.current) ClassDarkStyle else ClassLightStyle

private val NumberStyle: SpanStyle
	@Composable
	get() = if (LocalDarkTheme.current) NumberDarkStyle else NumberLightStyle

private val SymbolStyle: SpanStyle
	@Composable
	get() = if (LocalDarkTheme.current) SymbolDarkStyle else SymbolLightStyle

private val PropertyNameStyle: SpanStyle
	@Composable
	get() = if (LocalDarkTheme.current) PropertyNameDarkStyle else PropertyNameLightStyle

private val ParameterStyle: SpanStyle
	@Composable
	get() = if (LocalDarkTheme.current) ParameterDarkStyle else ParameterLightStyle

private val StringStyle: SpanStyle
	@Composable
	get() = if (LocalDarkTheme.current) StringDarkStyle else StringLightStyle

private val KeywordStyle: SpanStyle
	@Composable
	get() = if (LocalDarkTheme.current) KeywordDarkStyle else KeywordLightStyle

private val VariableStyle: SpanStyle
	@Composable
	get() = if (LocalDarkTheme.current) VariableDarkStyle else VariableLightStyle

private val MethodDarkStyle = SpanStyle(
	color = Color(0xFF56B6C2)
)

private val ClassDarkStyle = SpanStyle(
	color = Color(0xFFD19A66)
)

private val NumberDarkStyle = SpanStyle(
	color = Color(0xff2ca4d3)
)

private val SymbolDarkStyle = SpanStyle(
	color = Color(0xFFBBBBBB)
)

private val PropertyNameDarkStyle = SpanStyle(
	color = Color(0xFF80C8D4)
)

private val ParameterDarkStyle = SpanStyle(
	color = Color(0xFF73D0A2)
)

private val StringDarkStyle = SpanStyle(
	color = Color(0xff85d27d)
)

private val KeywordDarkStyle = SpanStyle(
	color = Color(0xFFE06C75)
)

private val VariableDarkStyle = SpanStyle(
	color = Color(0xFFDDDDDD)
)

private val MethodLightStyle = SpanStyle(
	color = Color(0xFF0077AA)
)

private val ClassLightStyle = SpanStyle(
	color = Color(0xFFBF6F32)
)

private val NumberLightStyle = SpanStyle(
	color = Color(0xFF006FBF)
)

private val SymbolLightStyle = SpanStyle(
	color = Color(0xFF666666)
)

private val PropertyNameLightStyle = SpanStyle(
	color = Color(0xFF008DA6)
)

private val ParameterLightStyle = SpanStyle(
	color = Color(0xFF007D63)
)

private val StringLightStyle = SpanStyle(
	color = Color(0xFF408D40)
)

private val KeywordLightStyle = SpanStyle(
	color = Color(0xFFB03050)
)

private val VariableLightStyle = SpanStyle(
	color = Color(0xFF222222)
)