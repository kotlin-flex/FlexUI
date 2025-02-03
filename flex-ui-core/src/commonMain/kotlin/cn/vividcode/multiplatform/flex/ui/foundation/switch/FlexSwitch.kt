package cn.vividcode.multiplatform.flex.ui.foundation.switch

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cn.vividcode.multiplatform.flex.ui.config.type.*

@Composable
fun FlexSwitch(
	checked: Boolean,
	onCheckedChange: (Boolean) -> Unit,
	modifier: Modifier = Modifier,
	sizeType: FlexSizeType = FlexSwitchDefaults.DefaultSizeType,
	colorType: FlexColorType = FlexSwitchDefaults.DefaultColorType,
	cornerType: FlexCornerType = FlexSwitchDefaults.DefaultCornerType,
) {

}

object FlexSwitchDefaults : FlexDefaults() {
	
	override val FlexComposeDefaultConfig.defaultConfig: FlexDefaultConfig
		get() = this.switch
}