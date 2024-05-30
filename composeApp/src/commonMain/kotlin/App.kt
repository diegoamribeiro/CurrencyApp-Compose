import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import di.initializeKoin
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.screen.HomeScreen

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {

    initializeKoin()

    MaterialTheme {
        Navigator(HomeScreen())
    }
}