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

@Composable
fun Code(
	methodName: String,
	assigns: List<AssignT>,
) {
	Column(
		modifier = Modifier
			.width(360.dp)
			.fillMaxHeight()
			.background(
				color = MaterialTheme.colorScheme.surfaceContainerLow,
				shape = RoundedCornerShape(12.dp)
			)
			.padding(12.dp)
	) {
		val horizontalScrollState = rememberScrollState()
		val verticalScrollState = rememberScrollState()
		Text(
			text = buildAnnotatedString {
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
					println(parameter.codes)
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
			},
			modifier = Modifier
				.fillMaxSize()
				.horizontalScroll(horizontalScrollState)
				.verticalScroll(verticalScrollState),
			fontSize = 14.sp,
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
}

open class SymbolT(
	override val code: String,
) : CodeT {
	override val spanStyle = SymbolStyle
}

data object DotT : SymbolT(".")

data class SpaceT(
	val count: Int = 1,
) : SymbolT(" ".repeat(count))

data object LeftBraceT : SymbolT("{")

data object RightBraceT : SymbolT("}")

data object CommaT : SymbolT(",")

data object DoubleQuotesT : SymbolT("\"")

data class FloatT(
	val number: Float,
) : CodeT {
	override val code: String = "${number}f"
	override val spanStyle = NumberStyle
}

data class DoubleT(
	val number: Double,
) : CodeT {
	override val code: String = number.toString()
	override val spanStyle = NumberStyle
}

data class ByteT(
	val number: Byte,
) : CodeT {
	override val code: String = number.toString()
	override val spanStyle = NumberStyle
}

data class ShortT(
	val number: Short,
) : CodeT {
	override val code: String = number.toString()
	override val spanStyle = NumberStyle
}

data class IntT(
	val number: Int,
) : CodeT {
	override val code: String = number.toString()
	override val spanStyle = NumberStyle
}

data class LongT(
	val number: Long,
) : CodeT {
	override val code: String = "${number}L"
	override val spanStyle = NumberStyle
}

data class KeywordT(
	val keyword: String,
) : CodeT {
	override val code = keyword
	override val spanStyle = KeywordStyle
}

data class StringT(
	val string: String,
) : CodeT {
	override val code = string
	override val spanStyle = StringStyle
}

data class ClassT<T>(
	val name: T,
) : CodeT {
	override val code = name.toString()
	override val spanStyle = ClassStyle
}

data class ParameterT(
	val name: String,
) : CodeT {
	override val code = name
	override val spanStyle = ParameterStyle
}

operator fun CodeT.plus(other: CodeT): List<CodeT> {
	return listOf(this, other)
}

operator fun List<CodeT>.plus(other: String): List<CodeT> {
	return this + other
}

private val MethodStyle = SpanStyle(
	color = Color(0xFF56B6C2)
)

private val ClassStyle = SpanStyle(
	color = Color(0xFFD19A66)
)

private val NumberStyle = SpanStyle(
	color = Color(0xFFF5A623)
)

private val SymbolStyle = SpanStyle(
	color = Color(0xFFB0B0B0)
)

private val PropertyNameStyle = SpanStyle(
	color = Color(0xFF80C8D4)
)

private val ParameterStyle = SpanStyle(
	color = Color(0xFF73D0A2)
)

private val StringStyle = SpanStyle(
	color = Color(0xFF98C379)
)

private val KeywordStyle = SpanStyle(
	color = Color(0xFFE06C75)
)