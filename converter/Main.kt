package converter


enum class Units(val type: String, val initials: String, val normalName: String, val pluralName: String,
                 val other1: String?, val other2: String?, val factor: Double) {


    // distance
    DEFAULT("???", "???", "???", "???", null, null, .0),
    METERS("distance", "m", "meter", "meters", null, null, 1.0),
    KILOMETERS("distance", "km", "kilometer", "kilometers", null, null, 1000.0),
    CENTIMETERS("distance", "cm", "centimeter", "centimeters", null, null, 0.01),
    MILLIMETERS("distance", "mm", "millimeter", "millimeters", null, null, 0.001),
    MILES("distance", "mi", "mile", "miles", null, null, 1609.35),
    YARDS("distance", "yd", "yard", "yards", null, null, 0.9144),
    FEET("distance", "ft", "foot", "feet", null, null, 0.3048),
    INCHES("distance", "in", "inch", "inches", null, null, 0.0254),

    // mass
    GRAMS("mass", "g", "gram", "grams", null, null, 1.0),
    KILOGRAMS("mass", "kg", "kilogram", "kilograms", null, null, 1000.0),
    MILLIGRAMS("mass", "mg", "milligram", "milligrams", null, null, 0.001),
    POUNDS("mass", "lb", "pound", "pounds", null, null, 453.592),
    OUNCES("mass", "oz", "ounce", "ounces", null, null, 28.3495),

    // temperature
    CELSIUS("temperature", "c", "degree Celsius", "degrees Celsius", "dc", "Celsius", 1.0),
    FAHRENHEIT("temperature", "f", "degree Fahrenheit", "degrees Fahrenheit", "df", "Fahrenheit", 1.0),
    KELVINS("temperature", "k", "kelvin", "Kelvins", null, null, 1.0);


    companion object {

        fun convert(quantity: Double, unit1: String, unit2: String) {

            var unitFrom: Units = DEFAULT
            var unitTo: Units = DEFAULT
            val defaultUnit: Double


            for (enum in values()) {
                when (unit1) {
                    enum.initials.toLowerCase() -> unitFrom = enum
                    enum.normalName.toLowerCase() -> unitFrom = enum
                    enum.pluralName.toLowerCase() -> unitFrom = enum
                    enum.other1?.toLowerCase() -> unitFrom = enum
                    enum.other2?.toLowerCase() -> unitFrom = enum
                }
                when (unit2) {
                    enum.initials.toLowerCase() -> unitTo = enum
                    enum.normalName.toLowerCase() -> unitTo = enum
                    enum.pluralName.toLowerCase() -> unitTo = enum
                    enum.other1?.toLowerCase() -> unitTo = enum
                    enum.other2?.toLowerCase() -> unitTo = enum
                }
            }

            val conv: Double
            if (unitFrom.type != "temperature" && unitFrom != DEFAULT) {

                if (quantity < 0 && unitFrom.type == "distance") return println("Length shouldn't be negative")
                if (quantity < 0 && unitFrom.type == "mass") return println("Weight shouldn't be negative")
                conv = quantity * unitFrom.factor / unitTo.factor

            } else {

                defaultUnit = when (unitFrom) {

                    CELSIUS -> quantity
                    FAHRENHEIT -> (quantity - 32.0) * (5.0 / 9.0)
                    else -> quantity - 273.15
                }

                conv = when (unitTo) {

                    CELSIUS -> defaultUnit
                    FAHRENHEIT -> defaultUnit * 9.0 / 5.0 + 32.00
                    else -> defaultUnit + 273.15

                }

            }

            if (unitFrom == DEFAULT || unitTo == DEFAULT || unitFrom.type != unitTo.type) {
                println("Conversion from ${unitFrom.pluralName} to ${unitTo.pluralName} is impossible")

            } else {
                if (quantity == 1.0) {

                    print("$quantity ${unitFrom.normalName} is ")
                } else {

                    print("$quantity ${unitFrom.pluralName} is ")
                }

                if (conv == 1.0) {

                    println("$conv ${unitTo.normalName}")
                } else {

                    println("$conv ${unitTo.pluralName}")
                }
            }
        }
    }
}

fun main() {

    while (true) {
        print("Enter what you want to convert (or exit): ")
        val input = readLine()!!.toString()
        val inputSplit = input.split(" ")

        if (input == "exit") break
        if (input.isBlank()) println("Parse error") else {

            val quantity = inputSplit[0].toDouble()
            val unit1 = if (inputSplit[1].toLowerCase() == "degrees" || inputSplit[1].toLowerCase() == "degree")
                inputSplit[2].toLowerCase() else inputSplit[1].toLowerCase()
            val unit2 = inputSplit.last().toLowerCase()

            Units.convert(quantity, unit1, unit2)

        }
    }
}
