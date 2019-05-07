package com.askjeffreyliu.gradientpolyline

import android.animation.ArgbEvaluator
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.SphericalUtil


class GradientPolyline(
    private val map: GoogleMap?,
    private val width: Float?,
    private val geodesic: Boolean?,
    private val color1: Int?,
    private val color2: Int?
) {
    private val NUMBER_OF_POINTS = 30
    private var upSamplePoints = mutableListOf<LatLng>()
    private var polylines = mutableListOf<Polyline>()
    var isVisible: Boolean
        get() {
            return polylines[0].isVisible
        }
        set(value) {
            for (polyline in polylines) {
                polyline.isVisible = value
            }
        }

    var points: List<LatLng>? = null
        set(value) {
            remove()
            field = value
            draw()
        }

    data class Builder(
        var map: GoogleMap? = null,
        var points: List<LatLng>? = null,
        var width: Float? = null,
        var geodesic: Boolean? = null,
        var color1: Int? = null,
        var color2: Int? = null
    ) {
        fun map(map: GoogleMap) = apply { this.map = map }
        fun add(points: List<LatLng>?) = apply { this.points = points }
        fun width(width: Float) = apply { this.width = width }
        fun geodesic(geodesic: Boolean) = apply { this.geodesic = geodesic }
        fun colors(color1: Int, color2: Int) = apply {
            this.color1 = color1
            this.color2 = color2
        }

        fun build(): GradientPolyline {
            val poly = GradientPolyline(map, width, geodesic, color1, color2)
            poly.points = points // this would trigger the draw
            return poly
        }
    }

    private fun upSampling(input: List<LatLng>): MutableList<LatLng> {
        val result = mutableListOf<LatLng>()

        for (i in input.indices) {
            result.add(input[i])
            if (i + 1 >= input.size) {
                break
            }
            val temp = mutableListOf<LatLng>()
            temp.add(input[i])
            temp.add(input[i + 1])
            result.add(getCenter(temp))
        }
        return result
    }

    fun remove() {
        for (polyline in polylines) {
            polyline.remove()
        }
        polylines.clear()
    }

    private fun draw() {
        if (points.isNullOrEmpty() || points!!.size < 2) {
            return
        }
        upSamplePoints = points!!.toMutableList()

        while (upSamplePoints.size < NUMBER_OF_POINTS) {
            upSamplePoints = upSampling(upSamplePoints)
        }

        for (i in upSamplePoints.indices) {
            if (i + 1 >= upSamplePoints.size) {
                break
            }

            val color = ArgbEvaluator().evaluate(i * 1.0f / upSamplePoints.size, color2, color1) as Int

            val line = map?.addPolyline(
                PolylineOptions()
                    .add(upSamplePoints[i], upSamplePoints[i + 1])
                    .width(width!!)
                    .geodesic(geodesic!!)
                    .color(color)
            )
            polylines.add(line!!)
        }
    }

    private fun getCenter(points: List<LatLng>): LatLng {
        if (geodesic == true) {
            return SphericalUtil.interpolate(points[0], points[1], 0.5)
        }

        val builder = LatLngBounds.Builder()
        for (point in points) {
            builder.include(point)
        }
        val bounds = builder.build()
        return bounds.center
    }
}