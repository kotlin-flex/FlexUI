package cn.vividcode.multiplatform.flex.ui.utils

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.sp

@Composable
fun animateTextUnitAsState(textUnit: TextUnit): State<TextUnit> {
	if (textUnit.isUnspecified) {
		return remember { derivedStateOf { textUnit } }
	}
	val value by animateFloatAsState(textUnit.value)
	return remember(value) {
		derivedStateOf {
			if (textUnit.isSp) value.sp else value.em
		}
	}
}