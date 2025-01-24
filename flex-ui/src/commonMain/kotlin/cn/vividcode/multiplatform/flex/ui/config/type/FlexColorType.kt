package cn.vividcode.multiplatform.flex.ui.config.type

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
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