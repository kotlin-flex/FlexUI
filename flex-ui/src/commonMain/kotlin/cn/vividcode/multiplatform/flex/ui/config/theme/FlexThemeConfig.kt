package cn.vividcode.multiplatform.flex.ui.config.theme

class FlexThemeConfig internal constructor() {
	
	internal val colorScheme = FlexThemeColorSchemeConfig()
	
	fun colorScheme(config: FlexThemeColorSchemeConfig.() -> Unit) {
		this.colorScheme.apply(config)
	}
}