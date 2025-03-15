import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable


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


