package cn.vividcode.multiplatform.flex.ui.expends

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 求 所有 Dp 的和
 */
fun List<Dp>.sum(): Dp = this.sumOf { it.value.toDouble() }.dp