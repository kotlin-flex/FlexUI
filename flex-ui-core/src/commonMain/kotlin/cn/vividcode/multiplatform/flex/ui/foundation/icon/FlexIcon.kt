package cn.vividcode.multiplatform.flex.ui.foundation.icon

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import cn.vividcode.multiplatform.flex.ui.graphics.FlexBrush

@Composable
fun FlexIcon(
	imageVector: ImageVector,
	contentDescription: String? = null,
	modifier: Modifier = Modifier,
	tint: FlexBrush = FlexBrush.solidColor(LocalContentColor.current),
) {
	Icon(
		imageVector = imageVector,
		contentDescription = contentDescription,
		modifier = modifier
			.graphicsLayer(alpha = 0.999f)
			.drawWithCache {
				onDrawWithContent {
					drawContent()
					drawRect(
						brush = tint.original,
						blendMode = BlendMode.SrcAtop
					)
				}
			}
	)
}