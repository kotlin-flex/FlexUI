package cn.vividcode.multiplatform.flex.ui.config.type

/**
 * 默认类型配置
 */
class FlexDefaultTypeConfig internal constructor(
	var colorType: FlexColorType,
	var cornerType: FlexCornerType,
	var sizeType: FlexSizeType,
) {
	
	internal companion object {
		
		val DefaultTypeConfig: FlexDefaultTypeConfig
			get() = FlexDefaultTypeConfig(
				colorType = FlexColorType.Default,
				cornerType = FlexCornerType.Default,
				sizeType = FlexSizeType.Medium
			)
	}
}

