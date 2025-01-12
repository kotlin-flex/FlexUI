package cn.vividcode.multiplatform.flex.ui.sample

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.foundation.ButtonType
import cn.vividcode.multiplatform.flex.ui.foundation.FlexButton

@Composable
fun App() {
	Scaffold(
		modifier = Modifier.fillMaxSize(),
	) {
		val verticalScrollState = rememberScrollState()
		Column(
			modifier = Modifier
				.padding(it)
				.fillMaxSize()
				.verticalScroll(verticalScrollState)
				.padding(vertical = 16.dp)
		) {
			val horizontalScrollState1 = rememberScrollState()
			Row(
				modifier = Modifier.fillMaxWidth()
					.horizontalScroll(horizontalScrollState1)
					.padding(horizontal = 16.dp),
			) {
				FlexButton(
					text = "按钮11",
					sizeType = FlexSizeType.Large,
					colorType = FlexColorType.Primary,
					buttonType = ButtonType.Primary
				) {
				
				}
				Spacer(modifier = Modifier.width(16.dp))
				FlexButton(
					text = "按钮12",
					sizeType = FlexSizeType.Large,
					colorType = FlexColorType.Primary,
					buttonType = ButtonType.Default
				) {
				
				}
				Spacer(modifier = Modifier.width(16.dp))
				FlexButton(
					text = "按钮13",
					sizeType = FlexSizeType.Large,
					colorType = FlexColorType.Primary,
					buttonType = ButtonType.Dashed
				) {
				
				}
				Spacer(modifier = Modifier.width(16.dp))
				FlexButton(
					text = "按钮14",
					sizeType = FlexSizeType.Large,
					colorType = FlexColorType.Default,
					buttonType = ButtonType.Link
				) {
				
				}
				Spacer(modifier = Modifier.width(16.dp))
				FlexButton(
					text = "按钮15",
					sizeType = FlexSizeType.Large,
					colorType = FlexColorType.Default,
					buttonType = ButtonType.Primary
				) {
				
				}
			}
			
			Spacer(modifier = Modifier.height(16.dp))
			
			val horizontalScrollState2 = rememberScrollState()
			Row(
				modifier = Modifier.fillMaxWidth()
					.horizontalScroll(horizontalScrollState2)
					.padding(horizontal = 16.dp),
			) {
				FlexButton(
					text = "按钮21",
					sizeType = FlexSizeType.Medium,
					colorType = FlexColorType.Primary,
					buttonType = ButtonType.Primary
				) {
				
				}
				Spacer(modifier = Modifier.width(16.dp))
				FlexButton(
					text = "按钮22",
					sizeType = FlexSizeType.Medium,
					colorType = FlexColorType.Primary,
					buttonType = ButtonType.Default
				) {
				
				}
				Spacer(modifier = Modifier.width(16.dp))
				FlexButton(
					text = "按钮23",
					sizeType = FlexSizeType.Medium,
					colorType = FlexColorType.Primary,
					buttonType = ButtonType.Dashed
				) {
				
				}
				Spacer(modifier = Modifier.width(16.dp))
				FlexButton(
					text = "按钮24",
					sizeType = FlexSizeType.Medium,
					colorType = FlexColorType.Default,
					buttonType = ButtonType.Link
				) {
				
				}
				Spacer(modifier = Modifier.width(16.dp))
				FlexButton(
					text = "按钮25",
					sizeType = FlexSizeType.Medium,
					colorType = FlexColorType.Default,
					buttonType = ButtonType.Primary
				) {
				
				}
			}
			
			Spacer(modifier = Modifier.height(16.dp))
			
			val horizontalScrollState3 = rememberScrollState()
			Row(
				modifier = Modifier.fillMaxWidth()
					.horizontalScroll(horizontalScrollState3)
					.padding(horizontal = 16.dp),
			) {
				FlexButton(
					text = "按钮31",
					sizeType = FlexSizeType.Small,
					colorType = FlexColorType.Primary,
					buttonType = ButtonType.Primary
				) {
				
				}
				Spacer(modifier = Modifier.width(16.dp))
				FlexButton(
					text = "按钮32",
					sizeType = FlexSizeType.Small,
					colorType = FlexColorType.Primary,
					buttonType = ButtonType.Default
				) {
				
				}
				Spacer(modifier = Modifier.width(16.dp))
				FlexButton(
					text = "按钮33",
					sizeType = FlexSizeType.Small,
					colorType = FlexColorType.Primary,
					buttonType = ButtonType.Dashed
				) {
				
				}
				Spacer(modifier = Modifier.width(16.dp))
				FlexButton(
					text = "按钮34",
					sizeType = FlexSizeType.Small,
					colorType = FlexColorType.Default,
					buttonType = ButtonType.Link
				) {
				
				}
				Spacer(modifier = Modifier.width(16.dp))
				FlexButton(
					text = "按钮35",
					sizeType = FlexSizeType.Small,
					colorType = FlexColorType.Default,
					buttonType = ButtonType.Primary
				) {
				
				}
			}
		}
	}
}