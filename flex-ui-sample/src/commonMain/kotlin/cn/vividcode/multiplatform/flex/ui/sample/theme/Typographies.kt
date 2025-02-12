package cn.vividcode.multiplatform.flex.ui.sample.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import cn.vividcode.multiplatform.flex.ui.sample.generated.resources.MiSans_VF
import cn.vividcode.multiplatform.flex.ui.sample.generated.resources.Res
import org.jetbrains.compose.resources.Font


@Composable
fun getTypography() = with(MaterialTheme.typography) {
	val fontFamily = FontFamily(
		fonts = listOf(
			FontWeight.W100, FontWeight.W200, FontWeight.W300,
			FontWeight.W400, FontWeight.W500, FontWeight.W600,
			FontWeight.W700, FontWeight.W800, FontWeight.W900,
		).map {
			Font(
				resource = Res.font.MiSans_VF,
				weight = it,
				variationSettings = FontVariation.Settings(
					FontVariation.weight(it.weight)
				)
			)
		}
	)
	Typography(
		displayLarge = displayLarge.copy(fontFamily = fontFamily),
		displayMedium = displayMedium.copy(fontFamily = fontFamily),
		displaySmall = displaySmall.copy(fontFamily = fontFamily),
		headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
		headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
		headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
		titleLarge = titleLarge.copy(fontFamily = fontFamily),
		titleMedium = titleMedium.copy(fontFamily = fontFamily),
		titleSmall = titleSmall.copy(fontFamily = fontFamily),
		bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
		bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
		bodySmall = bodySmall.copy(fontFamily = fontFamily),
		labelLarge = labelLarge.copy(fontFamily = fontFamily),
		labelMedium = labelMedium.copy(fontFamily = fontFamily),
		labelSmall = labelSmall.copy(fontFamily = fontFamily)
	)
}