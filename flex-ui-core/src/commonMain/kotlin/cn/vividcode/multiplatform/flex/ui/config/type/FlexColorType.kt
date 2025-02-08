package cn.vividcode.multiplatform.flex.ui.config.type

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig

/**
 * 颜色类型
 */
interface FlexColorType {
	
	val contentColor: Color
		@Composable
		get() = MaterialTheme.colorScheme.contentColorFor(color)
	
	val color: Color
		@Composable
		get
	
	data object Primary : FlexColorType {
		
		override val color: Color
			@Composable
			get() = MaterialTheme.colorScheme.primary
	}
	
	data object Secondary : FlexColorType {
		
		override val color: Color
			@Composable
			get() = MaterialTheme.colorScheme.secondary
	}
	
	data object Tertiary : FlexColorType {
		
		override val color: Color
			@Composable
			get() = MaterialTheme.colorScheme.tertiary
	}
	
	data object Error : FlexColorType {
		
		override val color: Color
			@Composable
			get() = MaterialTheme.colorScheme.error
	}
	
	data object InverseSurface : FlexColorType {
		
		override val color: Color
			@Composable
			get() = MaterialTheme.colorScheme.inverseSurface
	}
	
	companion object {
		
		val entries by lazy {
			listOf(
				Primary, Secondary, Tertiary, Error, InverseSurface
			)
		}
	}
}

internal val LocalFlexColorType = compositionLocalOf<FlexColorType?> { null }

@Composable
internal fun getDefaultColorType(
	defaultColorType: FlexColorType = FlexColorType.Primary,
	composeDefaultColorType: FlexComposeDefaultConfig.() -> FlexColorType?,
): FlexColorType = LocalFlexColorType.current
	?: LocalFlexConfig.current.default.let {
		it.composeDefaultColorType()
			?: it.common?.colorType
			?: defaultColorType
	}