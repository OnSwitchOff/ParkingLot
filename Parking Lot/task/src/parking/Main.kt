package parking

fun main() {
    val parking = Parking(2)
    parking.test()
    val input = readLine()!!.split(" ")
    when {
        input[0] == "park" -> parking.park(Car(input[2],input[1]))
        input[0] == "leave" -> parking.leave(input[1].toInt())
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
            println("There is no free spots.")
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
}
