import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
	alias(libs.plugins.kotlin.multiplatform)
	alias(libs.plugins.compose.compiler)
	alias(libs.plugins.jetbrains.compose)
	alias(libs.plugins.kotlin.serialization)
	alias(libs.plugins.android.library)
	alias(libs.plugins.maven.publish)
}

val flexUIVersion = property("flex-ui.version").toString()
val flexUIAutomaticRelease = property("flex-ui.automatic-release").toString().toBoolean()

group = "cn.vividcode.multiplatform.flex.ui.api"
version = flexUIVersion

kotlin {
	jvmToolchain(21)
	
	androidTarget {
		compilerOptions {
			jvmTarget.set(JvmTarget.JVM_21)
		}
	}
	
	jvm("desktop") {
		compilerOptions {
			jvmTarget.set(JvmTarget.JVM_21)
		}
	}
	
	listOf(
		iosX64(),
		iosArm64(),
		iosSimulatorArm64()
	).forEach { iosTarget ->
		iosTarget.binaries.framework {
			baseName = "FlexUI"
			isStatic = true
		}
	}
	
	js {
		moduleName = "flexUI"
		browser {
			commonWebpackConfig {
				outputFileName = "flexUI.js"
			}
		}
		binaries.executable()
		useEsModules()
	}
	
	@OptIn(ExperimentalWasmDsl::class)
	wasmJs {
		moduleName = "flexUI"
		browser {
			val rootDirPath = project.rootDir.path
			val projectDirPath = project.projectDir.path
			commonWebpackConfig {
				outputFileName = "flexUI.js"
				devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
					static = (static ?: mutableListOf()).apply {
						add(rootDirPath)
						add(projectDirPath)
					}
				}
			}
		}
		binaries.executable()
	}
	
	sourceSets {
		commonMain {
			dependencies {
				implementation(compose.runtime)
				implementation(compose.foundation)
				implementation(compose.material3)
				implementation(compose.ui)
				implementation(compose.components.resources)
			}
			kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
		}
	}
}

android {
	namespace = "cn.vividcode.multiplatform.flex.ui"
	compileSdk = libs.versions.android.compileSdk.get().toInt()
	
	sourceSets["main"].apply {
		manifest.srcFile("src/androidMain/AndroidManifest.xml")
		res.srcDirs("src/androidMain/res")
		resources.srcDirs("src/commonMain/resources")
	}
	
	defaultConfig {
		minSdk = libs.versions.android.minSdk.get().toInt()
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
			merges += "/META-INF/DEPENDENCIES"
		}
	}
	buildTypes {
		getByName("release") {
			isMinifyEnabled = false
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_21
		targetCompatibility = JavaVersion.VERSION_21
	}
}

mavenPublishing {
	publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = flexUIAutomaticRelease)
	signAllPublications()
	
	coordinates("cn.vividcode.multiplatform", "flex-ui", flexUIVersion)
	
	pom {
		name = "flex-ui"
		description = "FlexUI 基于 Kotlin Multiplatform 的 UI 组件库"
		inceptionYear = "2025"
		url = "https://github.com/vividcodex/FlexUI"
		
		licenses {
			license {
				name = "The Apache License, Version 2.0"
				url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
				distribution = "http://www.apache.org/licenses/LICENSE-2.0.txt"
			}
		}
		developers {
			developer {
				id = "li-jia-wei"
				name = "li-jia-wei"
				url = "https://github.com/vividcodex/FlexUI"
			}
		}
		scm {
			url = "https://github.com/vividcodex/FlexUI"
			connection = "scm:git:git://github.com/vividcodex/FlexUI.git"
			developerConnection = "scm:git:ssh://git@github.com:vividcodex/FlexUI.git"
		}
	}
}