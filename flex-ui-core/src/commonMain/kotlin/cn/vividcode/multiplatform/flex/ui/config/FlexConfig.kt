package cn.vividcode.multiplatform.flex.ui.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cn.vividcode.multiplatform.flex.ui.config.foundation.*
import cn.vividcode.multiplatform.flex.ui.config.type.FlexComposeDefaultConfig

/**
 * Flex 配置
 */
fun flexConfig(config: (FlexConfig.() -> Unit)? = null): FlexConfig =
	FlexConfig().also { config?.invoke(it) }

@Composable
fun rememberFlexConfig(config: (FlexConfig.() -> Unit)? = null): FlexConfig =
	remember(config) { flexConfig(config) }


class FlexConfig internal constructor() {
	
	internal val default = FlexComposeDefaultConfig()
	
	internal val button = FlexSizeConfig(FlexButtonSizeDefaults)
	
	internal val radio = FlexSizeConfig(FlexRadioSizeDefaults)
	
	internal val input = FlexSizeConfig(FlexInputSizeDefaults)
	
	internal val switch = FlexSizeConfig(FlexSwitchSizeDefaults)
	
	internal val slider = FlexSizeConfig(FlexSliderSizeDefaults)
	
	internal val select = FlexSizeConfig(FlexSelectSizeDefaults)
	
	/**
	 * 默认类型配置
	 */
	fun default(config: FlexComposeDefaultConfig.() -> Unit) {
		this.default.apply(config)
	}
	
	/**
	 * 按钮配置
	 */
	fun button(config: FlexSizeConfig<FlexButtonConfig>.() -> Unit) {
		this.button.apply(config)
	}
	
	/**
	 * 单选框配置
	 */
	fun radio(config: FlexSizeConfig<FlexRadioConfig>.() -> Unit) {
		this.radio.apply(config)
	}
	
	/**
	 * 输入框配置
	 */
	fun input(config: FlexSizeConfig<FlexInputConfig>.() -> Unit) {
		this.input.apply(config)
	}
	
	/**
	 * 开关配置
	 */
	fun switch(config: FlexSizeConfig<FlexSwitchConfig>.() -> Unit) {
		this.switch.apply(config)
	}
	
	/**
	 * 滑动条配置
	 */
	fun slider(config: FlexSizeConfig<FlexSliderConfig>.() -> Unit) {
		this.slider.apply(config)
	}
	
	/**
	 * 选择器配置
	 */
	fun select(config: FlexSizeConfig<FlexSelectConfig>.() -> Unit) {
		this.select.apply(config)
	}
}