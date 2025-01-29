package cn.vividcode.multiplatform.flex.ui.config.type

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig

/**
 * 颜色类型
 */
@Stable
interface FlexColorType {
	
	data object Default : FlexColorType
	
	data object Primary : FlexColorType
	
	data object Secondary : FlexColorType
	
	data object Success : FlexColorType
	
	data object Warning : FlexColorType
	
	data object Error : FlexColorType
	
	companion object {
		
		val defaultColorTypes = arrayOf(Primary, Default, Secondary, Success, Warning, Error)
		
		val allColorTypes: Array<FlexColorType>
			@Composable
			get() = defaultColorTypes + LocalFlexConfig.current.theme.colorScheme.current.customs.keys
	}
}

internal val LocalFlexColorType = compositionLocalOf<FlexColorType?> { null }

@Composable
internal fun getDefaultColorType(
	defaultColorType: FlexComposeDefaultConfig.() -> FlexColorType?,
): FlexColorType = LocalFlexColorType.current
	?: LocalFlexConfig.current.default.defaultColorType()
	?: LocalFlexConfig.current.default.common.colorType
	?: FlexColorType.Default