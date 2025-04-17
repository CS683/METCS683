package edu.bu.metcs.graphicsexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambdaNInstance
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.bu.metcs.graphicsexample.ui.theme.GraphicsExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GraphicsExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   // MyGraphics()                }
                    NavGraph()
                }
        }
    }
}

@Composable
fun NavGraph(){
   val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home"){
        composable("home") {
            GraphicsExample {
                navController.navigate("next")
            }
        }
        composable("next") {
            AnimationExample{
                navController.popBackStack()
            }
        }
    }
}}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GraphicsExampleTheme {
        GraphicsExample{}
    }
}