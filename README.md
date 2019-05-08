[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![](https://jitpack.io/v/jeffreyliu8/map-gradient-polyline.svg)](https://jitpack.io/#jeffreyliu8/map-gradient-polyline)

# map-gradient-polyline
Google Map gradient polyline



How to use
----------------

### Setup
```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```


##### Dependencies
```groovy
	dependencies {
	        implementation 'com.github.jeffreyliu8:map-gradient-polyline:0.0.5'
	}
```

A quick example is shown below, check out main activity for more detail:
```kotlin
val gradientPolyline = GradientPolyline.Builder()
            .map(mMap)
            .add(latLngList)
            .width(10f)
            .geodesic(false)
            .colors(colorStart, colorEnd)
            .build()
```



![Output sample](https://github.com/jeffreyliu8/map-gradient-polyline/blob/master/screenshot.png)

Requirements
--------------
Requires a minimum SDK version of 14
