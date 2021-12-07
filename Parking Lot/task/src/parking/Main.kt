package parking

fun main() {
    var parking: Parking? = null
    //parking.test()
    while (true) {
        val input = readLine()!!.split(" ")
        when {
            input[0] == "create" -> {
                parking = Parking(input[1].toInt())
                println("Created a parking lot with ${input[1].toInt()} spots.")
            }
            input[0] == "park" -> tryPark { parking!!.park(Car(input[2],input[1])) }
            input[0] == "leave" -> tryPark { parking!!.leave(input[1].toInt()) }
            input[0] == "status" -> tryPark { parking!!.printStatus() }
            input[0] == "spot_by_color" -> tryPark { parking!!.spot_by_color(input[1]) }
            input[0] == "reg_by_color" -> tryPark { parking!!.reg_by_color(input[1]) }
            input[0] == "spot_by_reg" -> tryPark { parking!!.spot_by_reg(input[1]) }
            input[0] == "exit" -> break
        }
    }
}

fun tryPark(f: () -> Unit) {
    try {
        f()
    } catch (e: Exception) {
        println("Sorry, a parking lot has not been created.")
    }
}

enum class SpotState {
    FREE, BUSY
}

class Car(_color: String,_number: String) {
    var color: String = _color
    var number: String = _number
}

class Spot(_n: Int) {
    val n = _n
    val state: SpotState
    get() {  return if (car == null) SpotState.FREE else  SpotState.BUSY}
    var car: Car? = null
}

class Parking(spotSize: Int) {
    private val spotList: MutableList<Spot> = MutableList(spotSize){ Spot(it + 1) }
    fun test() {
        spotList[0].car = Car("Green", "213-213")
    }
    fun park(car: Car) {
        if (spotList.any { s -> s.state == SpotState.FREE }) {
            val spot = spotList.find { s -> s.state == SpotState.FREE }!!
            spot.car = car
            println("${car.color} car parked in spot ${spot.n}.")
        } else {
            println("Sorry, the parking lot is full.")
        }
    }

    fun leave(n: Int) {
        if (spotList[n - 1].state == SpotState.BUSY) {
            spotList[n - 1].car = null
            println("Spot $n is free.")
        } else {
            println("There is no car in spot $n.")
        }
    }

    fun reg_by_color(color: String) {
        val result = spotList.filter { s -> s.state == SpotState.BUSY && s.car!!.color.equals(color, ignoreCase = true) }
        when {
            result.isEmpty() -> println("No cars with color $color were found.")
            else -> println(result.joinToString(", "){ s -> s.car!!.number })
        }
    }

    fun spot_by_color(color: String) {
        val result = spotList.filter { s -> s.state == SpotState.BUSY && s.car!!.color.equals(color, ignoreCase = true) }
        when {
            result.isEmpty() -> println("No cars with color $color were found.")
            else -> println(result.joinToString(", "){ s -> s.n.toString() })
        }
    }

    fun spot_by_reg(num: String) {
        val result = spotList.filter { s -> s.state == SpotState.BUSY && s.car!!.number.equals(num, ignoreCase = true) }
        when {
            result.isEmpty() -> println("No cars with registration number $num were found.")
            else -> println(result.joinToString(", "){ s -> s.n.toString() })
        }
    }

    fun printStatus() {
        when {
            spotList.all { s -> s.state == SpotState.FREE } -> println("Parking lot is empty.")
            else -> spotList.forEach { s -> if (s.state == SpotState.BUSY) {
                println("${s.n} ${s.car!!.number} ${s.car!!.color}")
            }}
        }
    }
}
