package cn.vividcode.multiplatform.flex.ui.common

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.util.fastMap

/**
 * 项目：flex-ui
 *
 * 作者：李佳伟
 *
 * 创建：2025/3/6 16:06
 *
 * 介绍：选项
 */
class FlexOption<Key : Any>(
	val key: Key,
	val value: String = key.toString(),
	val enabled: Boolean = true
)

fun <Key : Any> Array<Key>.options(
	transform: (Key) -> FlexOption<Key> = { FlexOption(it) }
): List<FlexOption<Key>> = this.map(transform)

fun <Key : Any> Iterable<Key>.options(
	transform: (Key) -> FlexOption<Key> = { FlexOption(it) }
): List<FlexOption<Key>> = this.map(transform)

fun <Key : Any> List<Key>.options(
	transform: (Key) -> FlexOption<Key> = { FlexOption(it) }
): List<FlexOption<Key>> = this.fastMap(transform)

fun <Key : Any> Array<Key>.optionsIndexed(
	transform: (index: Int, Key) -> FlexOption<Key> = { _, it -> FlexOption(it) }
): List<FlexOption<Key>> = this.mapIndexed(transform)

fun <Key : Any> Iterable<Key>.optionsIndexed(
	transform: (index: Int, Key) -> FlexOption<Key> = { _, it -> FlexOption(it) }
): List<FlexOption<Key>> = this.mapIndexed(transform)

fun <Key : Any> List<Key>.optionsIndexed(
	transform: (index: Int, Key) -> FlexOption<Key> = { _, it -> FlexOption(it) }
): List<FlexOption<Key>> = this.mapIndexed(transform)