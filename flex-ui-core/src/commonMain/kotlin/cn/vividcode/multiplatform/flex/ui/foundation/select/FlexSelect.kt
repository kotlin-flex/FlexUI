package cn.vividcode.multiplatform.flex.ui.foundation.select

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cn.vividcode.multiplatform.flex.ui.common.FlexOption
import cn.vividcode.multiplatform.flex.ui.config.FlexComposeDefaultConfig
import cn.vividcode.multiplatform.flex.ui.config.FlexDefaults
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.type.FlexBrushType
import cn.vividcode.multiplatform.flex.ui.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.utils.animateFlexBrushAsState
import cn.vividcode.multiplatform.flex.ui.utils.border

/**
 * 未实现
 */
@Composable
fun <Key : Any> FlexSelect(
	selectedKey: Key,
	onSelectedKeyChanged: (Key) -> Unit,
	options: List<FlexOption<Key>>,
	modifier: Modifier = Modifier,
	sizeType: FlexSizeType = FlexSelectDefaults.DefaultSizeType,
	brushType: FlexBrushType = FlexSelectDefaults.DefaultBrushType,
	cornerType: FlexCornerType = FlexSelectDefaults.DefaultCornerType,
	enabled: Boolean = true
) {
	val config = LocalFlexConfig.current.select.getConfig(sizeType)
	val minWidth by animateDpAsState(config.minWidth)
	val height by animateDpAsState(config.height)
	val borderWidth by animateDpAsState(config.borderWidth)
	val horizontalPadding by animateDpAsState(config.horizontalPadding)
	val borderBrush by animateFlexBrushAsState(brushType.brush)
	val corner by animateDpAsState(config.height * cornerType.scale)
	val cornerShape by remember(corner) {
		derivedStateOf { RoundedCornerShape(corner) }
	}
	Column(
		modifier = Modifier
			.widthIn(min = minWidth)
			.height(height)
			.border(
				width = borderWidth,
				brush = borderBrush,
				shape = cornerShape
			)
			.padding(
				horizontal = horizontalPadding
			),
		verticalArrangement = Arrangement.Center
	) {
	
	}
}

object FlexSelectDefaults : FlexDefaults() {
	
	override val FlexComposeDefaultConfig.defaultConfig
		get() = this.select
	
}