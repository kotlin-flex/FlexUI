package cn.vividcode.multiplatform.flex.ui.sample.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme

@Composable
fun Code(
	methodName: String,
	assigns: List<AssignT>,
) {
	Box(
		modifier = Modifier
			.width(340.dp)
			.fillMaxHeight()
			.background(
				color = MaterialTheme.colorScheme.surfaceContainer,
				shape = RoundedCornerShape(12.dp)
			)
	) {
		val horizontalScrollState = rememberScrollState()
		val verticalScrollState = rememberScrollState()
		val string = buildAnnotatedString {
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
					vertical = 8.5.dp
				),
			fontSize = 15.sp,
			lineHeight = 22.sp
		)
	}
}

infix fun String.assign(code: Any): AssignT = when (code) {
	is String -> AssignT(this, DoubleQuotesT + StringT(code) + DoubleQuotesT)
	is Float -> AssignT(this, listOf(FloatT(code)))
	is Double -> AssignT(this, listOf(DoubleT(code)))
	is Short -> AssignT(this, listOf(ShortT(code)))
	is Int -> AssignT(this, listOf(IntT(code)))
	is Long -> AssignT(this, listOf(LongT(code)))
	is Boolean -> AssignT(this, listOf(KeywordT(code.toString())))
	else -> {
		@Suppress("UNCHECKED_CAST")
		val codes = code as? List<CodeT>
		if (codes == null) {
			check(code is CodeT)
			AssignT(this, listOf(code))
		} else AssignT(this, codes)
	}
}

data class AssignT(
	val name: String,
	val codes: List<CodeT>,
)

sealed interface CodeT {
	
	val code: String
	
	val spanStyle: SpanStyle
		@Composable
		get
}

open class SymbolT(
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

data object CommaT : SymbolT(",")

data object DoubleQuotesT : SymbolT("\"")

interface NumberT<T> : CodeT {
	
	val number: T
	
	override val spanStyle: SpanStyle
		@Composable
		get() = NumberStyle
}

data class FloatT(
	override val number: Float,
) : NumberT<Float> {
	
	override val code: String = "${number}f"
}

data class DoubleT(
	override val number: Double,
) : NumberT<Double> {
	
	override val code: String = number.toString()
}

data class ByteT(
	override val number: Byte,
) : NumberT<Byte> {
	
	override val code: String = number.toString()
}

data class ShortT(
	override val number: Short,
) : NumberT<Short> {
	
	override val code: String = number.toString()
}

data class IntT(
	override val number: Int,
) : NumberT<Int> {
	
	override val code: String = number.toString()
}

data class LongT(
	override val number: Long,
) : NumberT<Long> {
	
	override val code: String = "${number}L"
}

data class KeywordT(
	val keyword: String,
) : CodeT {
	
	override val code = keyword
	
	override val spanStyle: SpanStyle
		@Composable
		get() = KeywordStyle
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
	color = Color(0xffBBBBBB)
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