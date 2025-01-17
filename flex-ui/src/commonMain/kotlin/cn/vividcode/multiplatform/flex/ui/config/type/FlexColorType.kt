package cn.vividcode.multiplatform.flex.ui.config.type

import androidx.compose.runtime.Stable

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
		
		val entries by lazy {
			arrayOf(Primary, Default, Secondary, Success, Warning, Error)
		}
	}
}