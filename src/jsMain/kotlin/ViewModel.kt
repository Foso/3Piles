import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*

class ViewModel {
    var round: Int by mutableStateOf(0)
    private var cards = (1..21).map { it }.shuffled()
    private var groups: MutableList<List<Int>> = mutableListOf()

    var row1: List<Int> by mutableStateOf(listOf())
    var row2: List<Int> by mutableStateOf(listOf())
    var row3: List<Int> by mutableStateOf(listOf())
    var end: String by mutableStateOf("")

    fun onPileSelected(selected: Int) {
        when (round) {
            1, 2 -> {
                val middle = groups[selected]
                groups.removeAt(selected)
                cards = groups[0] + middle + groups[1]
                groups.clear()
                groups.add(cards.slice(0..20 step 3))
                groups.add(cards.slice(1..21 step 3))
                groups.add(cards.slice(2..20 step 3))
                groups.forEachIndexed { index, ints ->
                    if (index == 0) {
                        row1 = ints
                    }
                    if (index == 1) {
                        row2 = ints
                    }
                    if (index == 2) {
                        row3 = ints
                    }
                }
                round++
            }

            3 -> {
                val middle = groups[selected]
                groups.removeAt(selected)
                cards = groups[0] + middle + groups[1]
                end = "It is number: " + cards[10]
            }

        }
    }

    fun loadCards() {
        groups.add(cards.slice(0..20 step 3))
        groups.add(cards.slice(1..21 step 3))
        groups.add(cards.slice(2..20 step 3))
        groups.forEachIndexed { index, ints ->
            if (index == 0) {
                row1 = ints.shuffled()
            }
            if (index == 1) {
                row2 = ints.shuffled()
            }
            if (index == 2) {
                row3 = ints.shuffled()
            }
        }
        round++
    }
}