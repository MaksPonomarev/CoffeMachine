package machine

object CoffeeMachine {

    enum class Recipe(val water: Int, val milk: Int, val coffeeBeans: Int, val cost: Int) {
        ESPRESSO(250, 0, 16, 4),
        LATTE(350, 75, 20, 7),
        CAPPUCCINO(200, 100, 12, 6)
    }

    private var availableWater = 400
        set(value) {
            field = if (value < 0) 0 else value
        }
    private var availableMilk = 540
        set(value) {
            field = if (value < 0) 0 else value
        }
    private var availableCoffeeBeans = 120
        set(value) {
            field = if (value < 0) 0 else value
        }
    private var availableCups = 9
        set(value) {
            field = if (value < 0) 0 else value
        }
    private var machineMoney = 550
        set(value) {
            field = if (value < 0) 0 else value
        }

    fun buyCoffee() {
        print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: > ")

        when(readLine()!!.toString()){
            "1" -> prepareCoffee(Recipe.ESPRESSO)
            "2" -> prepareCoffee(Recipe.LATTE)
            "3" -> prepareCoffee(Recipe.CAPPUCCINO)
            else -> println()
        }
    }

    private fun prepareCoffee(needed: Recipe) {

        val availableCoffee = minOf(
                availableWater / needed.water,
                availableMilk / maxOf(needed.milk, 1), // когда идет проверка на эспрессо идет деление на 0
                availableCoffeeBeans / needed.coffeeBeans
        )

        if (availableCups >= 1 && availableCoffee >= 1) {
            availableWater -= needed.water
            availableMilk -= needed.milk
            availableCoffeeBeans -= needed.coffeeBeans
            availableCups -= 1
            machineMoney += needed.cost
            println("I have enough resources, making you a coffee!\n")
        } else {
            if (availableWater < needed.water) println("Sorry, not enough water!\n")
            if (availableMilk < needed.milk) println("Sorry, not enough milk!\n")
            if (availableCoffeeBeans < needed.coffeeBeans) println("Sorry, not enough coffee beans!\n")
            if (availableCups < 1) println("Sorry, not enough cups!\n")
        }
    }

    fun fillCoffeeMachine() {
        print("Write how many ml of water do you want to add: > ")
        availableWater += readLine()!!.toInt()
        print("Write how many ml of milk do you want to add: > ")
        availableMilk += readLine()!!.toInt()
        print("Write how many grams of coffee beans do you want to add: > ")
        availableCoffeeBeans += readLine()!!.toInt()
        print("Write how many disposable cups of coffee do you want to add: > ")
        availableCups += readLine()!!.toInt()
    }

    fun takeMoney() {
        println("I gave you $$machineMoney")
        machineMoney = 0
    }

    fun showStatistics() {
        println("\nThe coffee machine has:\n" +
                "$availableWater of water\n" +
                "$availableMilk of milk\n" +
                "$availableCoffeeBeans of coffee beans\n" +
                "$availableCups of disposable cups\n" +
                "$$machineMoney of money\n")
    }

    fun on() {
        do {
            print("Write action (buy, fill, take, remaining, exit): > ")
            val action = readLine()!!
            when (action) {
                "buy" -> this.buyCoffee()
                "fill" -> this.fillCoffeeMachine()
                "take" -> this.takeMoney()
                "remaining" -> this.showStatistics()
            }
        } while (action != "exit")
    }
}
