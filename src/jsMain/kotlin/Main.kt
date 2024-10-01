import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable


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


fun main() {
    val vm = ViewModel()

    renderComposable(rootElementId = "root") {
        LaunchedEffect(Unit) {
            vm.loadCards()
        }

        Div(attrs = {
            style { padding(25.px) }
        }, content = {
            Text("Pick a number and click on the \"+\" next to the pile which contains your number. After 3 rounds it reveal your number")
            Spacer()
            Div {
                Text("Round: "+vm.round)
                Spacer()
            }
            Div() {
                Text(vm.end)
                Spacer()
            }
            Div {
                Text("Pile 1: ")
                vm.row1.forEach {
                    Text("$it, ")
                    Text("")
                }
                Button(attrs = {
                    onClick { vm.onPileSelected(0) }
                }) {
                    Text("+")
                    Text("")
                }
                Spacer()
            }
            Div() {
                Text("Pile 2: ")
                vm.row2.forEach {
                    Text("$it, ")

                }
                Button(attrs = {
                    onClick { vm.onPileSelected(1) }
                }) {
                    Text("+")
                }
                Spacer()
            }
            Div {
                Text("Pile 3: ")
                vm.row3.forEach {
                    Text("$it, ")
                }
                Button(attrs = {
                    onClick { vm.onPileSelected(2) }
                }) {
                    Text("+")
                }
                Spacer()
            }


        })
    }
}

@Composable
private fun Spacer() {
    Div(attrs = {
        style { padding(8.px) }
    }, content = {})
}


