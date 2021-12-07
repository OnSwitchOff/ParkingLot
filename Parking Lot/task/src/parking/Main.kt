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
            input[0] == "park" -> {
                try {
                    parking!!.park(Car(input[2],input[1]))
                } catch (e: Exception) {
                    println("Sorry, a parking lot has not been created.")
                }
            }
            input[0] == "leave" -> {
                try {
                    parking!!.leave(input[1].toInt())
                } catch (e: Exception) {
                    println("Sorry, a parking lot has not been created.")
                }
            }
            input[0] == "status" -> {
                try {
                    parking!!.printStatus()
                } catch (e: Exception) {
                    println("Sorry, a parking lot has not been created.")
                }
            }
            input[0] == "exit" -> break
        }
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

    fun printStatus() {
        when {
            spotList.all { s -> s.state == SpotState.FREE } -> println("Parking lot is empty.")
            else -> spotList.forEach { s -> if (s.state == SpotState.BUSY) {
                println("${s.n} ${s.car!!.number} ${s.car!!.color}")
            }}
        }
    }
}
