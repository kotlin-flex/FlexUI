package cn.vividcode.multiplatform.flex.ui.config.type

/**
 * 默认类型配置
 */
class FlexDefaultConfig internal constructor(
	var colorType: FlexColorType,
	var cornerType: FlexCornerType,
	var sizeType: FlexSizeType,
) {
	
	internal companion object {
		
		val DefaultConfig: FlexDefaultConfig
			get() = FlexDefaultConfig(
				colorType = FlexColorType.Default,
				cornerType = FlexCornerType.Default,
				sizeType = FlexSizeType.Medium
			)
	}
}

