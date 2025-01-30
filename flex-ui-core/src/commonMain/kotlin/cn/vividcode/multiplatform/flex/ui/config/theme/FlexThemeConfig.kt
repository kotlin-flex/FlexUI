package cn.vividcode.multiplatform.flex.ui.config.theme

/**
 * Flex 主题配置
 */
class FlexThemeConfig internal constructor() {
	
	internal val colorScheme = FlexThemeColorSchemeConfig()
	
	fun colorScheme(config: FlexThemeColorSchemeConfig.() -> Unit) {
		this.colorScheme.apply(config)
	}
}