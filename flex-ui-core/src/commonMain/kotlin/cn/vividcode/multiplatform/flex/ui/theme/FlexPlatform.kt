package cn.vividcode.multiplatform.flex.ui.theme

enum class FlexPlatform {
	
	Android,
	
	IOS,
	
	Desktop,
	
	Web;
	
	companion object {
		
		val isAndroid get() = CurrentPlatform == Android
		
		val isIOS get() = CurrentPlatform == IOS
		
		val isDesktop get() = CurrentPlatform == Desktop
		
		val isWeb get() = CurrentPlatform == Web
		
		val isMobile get() = CurrentPlatform == Android || CurrentPlatform == IOS
	}
}

internal expect val CurrentPlatform: FlexPlatform