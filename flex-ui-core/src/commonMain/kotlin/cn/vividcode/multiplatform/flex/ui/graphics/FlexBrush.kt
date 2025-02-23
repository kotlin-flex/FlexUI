package cn.vividcode.multiplatform.flex.ui.graphics

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.util.fastMap
import cn.vividcode.multiplatform.flex.ui.utils.toSolidColor

@Immutable
sealed class FlexBrush {
	
	companion object {
		
		private const val SOLID_COLOR = 0
		private const val LINEAR_GRADIENT = 1
		private const val LINEAR_GRADIENT_STOPS = 2
		private const val RADIAL_GRADIENT = 3
		private const val RADIAL_GRADIENT_STOPS = 4
		private const val SWEEP_GRADIENT = 5
		private const val SWEEP_GRADIENT_STOPS = 6
		
		private val cache by lazy { arrayOfNulls<MutableMap<String, FlexBrush>>(7) }
		
		private fun createOrCache(
			type: Int,
			key: String,
			create: () -> FlexBrush,
		): FlexBrush {
			val cacheMap = cache[type] ?: mutableMapOf<String, FlexBrush>()
				.also { cache[type] = it }
			return cacheMap.getOrPut(key, create)
		}
		
		@Stable
		fun solidColor(
			color: Color,
		): FlexBrush = createOrCache(
			type = SOLID_COLOR,
			key = "${color.hashCode()}"
		) {
			FlexSolidColor(
				color = color,
			)
		}
		
		@Stable
		fun linearGradient(
			colors: List<Color>,
			start: Offset = Offset.Zero,
			end: Offset = Offset.Infinite,
			tileMode: TileMode = TileMode.Clamp,
		): FlexBrush = createOrCache(
			type = LINEAR_GRADIENT,
			key = "${colors.hashCode()}_${start.hashCode()}_${end.hashCode()}_${tileMode.hashCode()}",
		) {
			FlexLinearGradient(
				colors = colors,
				start = start,
				end = end,
				tileMode = tileMode
			)
		}
		
		@Stable
		fun linearGradient(
			vararg colorStops: Pair<Float, Color>,
			start: Offset = Offset.Zero,
			end: Offset = Offset.Infinite,
			tileMode: TileMode = TileMode.Clamp,
		): FlexBrush = createOrCache(
			type = LINEAR_GRADIENT_STOPS,
			key = "${colorStops.hashCode()}_${start.hashCode()}_${end.hashCode()}_${tileMode.hashCode()}",
		) {
			FlexStopsLinearGradient(
				colorStops = colorStops,
				start = start,
				end = end,
				tileMode = tileMode
			)
		}
		
		@Stable
		fun horizontalGradient(
			colors: List<Color>,
			startX: Float = 0f,
			endX: Float = Float.POSITIVE_INFINITY,
			tileMode: TileMode = TileMode.Clamp,
		): FlexBrush = FlexLinearGradient(
			colors = colors,
			start = Offset(startX, 0f),
			end = Offset(endX, 0f),
			tileMode = tileMode
		)
		
		@Stable
		fun horizontalGradient(
			vararg colorStops: Pair<Float, Color>,
			startX: Float = 0f,
			endX: Float = Float.POSITIVE_INFINITY,
			tileMode: TileMode = TileMode.Clamp,
		): FlexBrush = linearGradient(
			colorStops = colorStops,
			start = Offset(startX, 0f),
			end = Offset(endX, 0f),
			tileMode = tileMode
		)
		
		@Stable
		fun verticalGradient(
			colors: List<Color>,
			startY: Float = 0f,
			endY: Float = Float.POSITIVE_INFINITY,
			tileMode: TileMode = TileMode.Clamp,
		): FlexBrush = linearGradient(
			colors = colors,
			start = Offset(0f, startY),
			end = Offset(0f, endY),
			tileMode = tileMode
		)
		
		@Stable
		fun verticalGradient(
			vararg colorStops: Pair<Float, Color>,
			startY: Float = 0f,
			endY: Float = Float.POSITIVE_INFINITY,
			tileMode: TileMode = TileMode.Clamp,
		): FlexBrush = linearGradient(
			colorStops = colorStops,
			start = Offset(0f, startY),
			end = Offset(0f, endY),
			tileMode = tileMode
		)
		
		@Stable
		fun radialGradient(
			colors: List<Color>,
			center: Offset = Offset.Unspecified,
			radius: Float = Float.POSITIVE_INFINITY,
			tileMode: TileMode = TileMode.Clamp,
		): FlexBrush = createOrCache(
			type = RADIAL_GRADIENT,
			key = "${colors.hashCode()}_${center.hashCode()}_${radius.hashCode()}_${tileMode.hashCode()}"
		) {
			FlexRadialGradient(
				colors = colors,
				center = center,
				radius = radius,
				tileMode = tileMode
			)
		}
		
		@Stable
		fun radialGradient(
			vararg colorStops: Pair<Float, Color>,
			center: Offset = Offset.Unspecified,
			radius: Float = Float.POSITIVE_INFINITY,
			tileMode: TileMode = TileMode.Clamp,
		): FlexBrush = createOrCache(
			type = RADIAL_GRADIENT_STOPS,
			key = "${colorStops.hashCode()}_${center.hashCode()}_${radius.hashCode()}_${tileMode.hashCode()}",
		) {
			FlexStopsRadialGradient(
				colorStops = colorStops,
				center = center,
				radius = radius,
				tileMode = tileMode
			)
		}
		
		@Stable
		fun sweepGradient(
			colors: List<Color>,
			center: Offset = Offset.Unspecified,
		): FlexBrush = createOrCache(
			type = SWEEP_GRADIENT,
			key = "${colors.hashCode()}_${center.hashCode()}",
		) {
			FlexSweepGradient(
				colors = colors,
				center = center,
			)
		}
		
		@Stable
		fun sweepGradient(
			vararg colorStops: Pair<Float, Color>,
			center: Offset = Offset.Unspecified,
		): FlexBrush = createOrCache(
			type = SWEEP_GRADIENT_STOPS,
			key = "${colorStops.hashCode()}_${center.hashCode()}",
		) {
			FlexStopsSweepGradient(
				colorStops = colorStops,
				center = center,
			)
		}
		
		val White by lazy { Color.White.toSolidColor() }
		
		val Black by lazy { Color.Black.toSolidColor() }
		
		val Gray by lazy { Color.Gray.toSolidColor() }
		
		val LightGray by lazy { Color.LightGray.toSolidColor() }
		
		val DarkGray by lazy { Color.DarkGray.toSolidColor() }
	}
	
	internal abstract val original: Brush
	
	internal abstract val colors: List<Color>
	
	internal abstract fun copy(alpha: Float = 1f): FlexBrush
	
	internal abstract fun replace(colors: List<Color>): FlexBrush
	
	final override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is FlexBrush) return false
		return this.original == other.original
	}
	
	final override fun hashCode(): Int {
		return original.hashCode()
	}
}

@Immutable
internal class FlexSolidColor(
	private val color: Color,
) : FlexBrush() {
	
	override val original = SolidColor(color)
	
	override val colors by lazy {
		listOf(color)
	}
	
	override fun copy(alpha: Float): FlexBrush {
		return solidColor(this.color.copy(alpha = alpha))
	}
	
	override fun replace(colors: List<Color>): FlexBrush {
		return FlexSolidColor(
			color = colors.first(),
		)
	}
}

@Immutable
internal class FlexLinearGradient(
	override val colors: List<Color>,
	private val start: Offset,
	private val end: Offset,
	private val tileMode: TileMode,
) : FlexBrush() {
	
	override val original = Brush.linearGradient(
		colors = colors,
		start = start,
		end = end,
		tileMode = tileMode,
	)
	
	override fun copy(alpha: Float): FlexBrush {
		val colors = colors.fastMap { it.copy(alpha = alpha) }
		return linearGradient(
			colors = colors,
			start = start,
			end = end,
			tileMode = tileMode,
		)
	}
	
	override fun replace(colors: List<Color>): FlexBrush {
		return FlexLinearGradient(
			colors = colors,
			start = start,
			end = end,
			tileMode = tileMode
		)
	}
}

@Immutable
internal class FlexStopsLinearGradient(
	private vararg val colorStops: Pair<Float, Color>,
	private val start: Offset,
	private val end: Offset,
	private val tileMode: TileMode,
) : FlexBrush() {
	
	override val original = Brush.linearGradient(
		colorStops = colorStops,
		start = start,
		end = end,
		tileMode = tileMode,
	)
	
	override val colors by lazy {
		colorStops.map { it.second }
	}
	
	override fun copy(alpha: Float): FlexBrush {
		val colorStops = Array(this.colorStops.size) { i ->
			this.colorStops[i].let { it.first to it.second.copy(alpha = alpha) }
		}
		return linearGradient(
			colorStops = colorStops,
			start = start,
			end = end,
			tileMode = tileMode,
		)
	}
	
	override fun replace(colors: List<Color>): FlexBrush {
		return FlexStopsLinearGradient(
			colorStops = Array(colorStops.size) { colorStops[it].first to colors[it] },
			start = start,
			end = end,
			tileMode = tileMode,
		)
	}
}

@Immutable
internal class FlexRadialGradient(
	override val colors: List<Color>,
	private val center: Offset,
	private val radius: Float,
	private val tileMode: TileMode,
) : FlexBrush() {
	
	override val original = Brush.radialGradient(
		colors = colors,
		center = center,
		radius = radius,
		tileMode = tileMode,
	)
	
	override fun copy(alpha: Float): FlexBrush {
		val colors = colors.fastMap { it.copy(alpha = alpha) }
		return radialGradient(
			colors = colors,
			center = center,
			radius = radius,
			tileMode = tileMode,
		)
	}
	
	override fun replace(colors: List<Color>): FlexBrush {
		return FlexRadialGradient(
			colors = colors,
			center = center,
			radius = radius,
			tileMode = tileMode,
		)
	}
}

@Immutable
internal class FlexStopsRadialGradient(
	private vararg val colorStops: Pair<Float, Color>,
	private val center: Offset,
	private val radius: Float,
	private val tileMode: TileMode,
) : FlexBrush() {
	
	override val original = Brush.radialGradient(
		colorStops = colorStops,
		center = center,
		radius = radius,
		tileMode = tileMode,
	)
	
	override val colors by lazy {
		colorStops.map { it.second }
	}
	
	override fun copy(alpha: Float): FlexBrush {
		val colorStops = Array(this.colorStops.size) { i ->
			this.colorStops[i].let { it.first to it.second.copy(alpha = alpha) }
		}
		return radialGradient(
			colorStops = colorStops,
			center = center,
			radius = radius,
			tileMode = tileMode,
		)
	}
	
	override fun replace(colors: List<Color>): FlexBrush {
		return FlexStopsRadialGradient(
			colorStops = Array(colorStops.size) { colorStops[it].first to colors[it] },
			center = center,
			radius = radius,
			tileMode = tileMode,
		)
	}
}

@Immutable
internal class FlexSweepGradient(
	override val colors: List<Color>,
	private val center: Offset,
) : FlexBrush() {
	
	override val original = Brush.sweepGradient(
		colors = colors,
		center = center
	)
	
	override fun copy(alpha: Float): FlexBrush {
		val colors = colors.fastMap { it.copy(alpha = alpha) }
		return sweepGradient(
			colors = colors,
			center = center
		)
	}
	
	override fun replace(colors: List<Color>): FlexBrush {
		return FlexSweepGradient(
			colors = colors,
			center = center
		)
	}
}

@Immutable
internal class FlexStopsSweepGradient(
	private vararg val colorStops: Pair<Float, Color>,
	private val center: Offset,
) : FlexBrush() {
	
	override val original = Brush.sweepGradient(
		colorStops = colorStops,
		center = center,
	)
	
	override val colors by lazy {
		colorStops.map { it.second }
	}
	
	override fun copy(alpha: Float): FlexBrush {
		val colorStops = Array(this.colorStops.size) { i ->
			this.colorStops[i].let { it.first to it.second.copy(alpha = alpha) }
		}
		return sweepGradient(
			colorStops = colorStops,
			center = center,
		)
	}
	
	override fun replace(colors: List<Color>): FlexBrush {
		return FlexStopsSweepGradient(
			colorStops = Array(colorStops.size) { colorStops[it].first to colors[it] },
			center = center
		)
	}
}