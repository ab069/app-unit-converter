package com.ab069.unitconverter.converter

enum class ConversionCategory { LENGTH, WEIGHT, TEMPERATURE, VOLUME }

data class Unit(val label: String, val toBase: Double, val fromBase: Double = 1.0 / toBase)

object Converter {

    val length = listOf(
        Unit("Millimeter", 0.001),
        Unit("Centimeter", 0.01),
        Unit("Meter", 1.0),
        Unit("Kilometer", 1000.0),
        Unit("Inch", 0.0254),
        Unit("Foot", 0.3048),
        Unit("Yard", 0.9144),
        Unit("Mile", 1609.344)
    )

    val weight = listOf(
        Unit("Milligram", 0.000001),
        Unit("Gram", 0.001),
        Unit("Kilogram", 1.0),
        Unit("Ton", 1000.0),
        Unit("Ounce", 0.0283495),
        Unit("Pound", 0.453592)
    )

    val volume = listOf(
        Unit("Milliliter", 0.001),
        Unit("Liter", 1.0),
        Unit("Fluid Ounce", 0.0295735),
        Unit("Cup", 0.236588),
        Unit("Pint", 0.473176),
        Unit("Quart", 0.946353),
        Unit("Gallon", 3.78541)
    )

    fun convertStandard(value: Double, from: Unit, to: Unit): Double {
        val inBase = value * from.toBase
        return inBase / to.toBase
    }

    fun convertTemperature(value: Double, from: String, to: String): Double {
        val celsius = when (from) {
            "Celsius" -> value
            "Fahrenheit" -> (value - 32) * 5 / 9
            "Kelvin" -> value - 273.15
            else -> value
        }
        return when (to) {
            "Celsius" -> celsius
            "Fahrenheit" -> celsius * 9 / 5 + 32
            "Kelvin" -> celsius + 273.15
            else -> celsius
        }
    }

    fun format(value: Double): String {
        return if (value == value.toLong().toDouble()) {
            value.toLong().toString()
        } else {
            "%.6f".format(value).trimEnd('0').trimEnd('.')
        }
    }
}
