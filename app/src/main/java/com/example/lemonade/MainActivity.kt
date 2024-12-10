package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadApp()
            }
        }
    }
}

@Composable
fun LemonadApp() {
    var currentIndex by remember { mutableIntStateOf(1) }
    var squeezeTapCount by remember { mutableIntStateOf(0) }

    val stepText = when (currentIndex) {
        1 -> R.string.select_lemon
        2 -> R.string.squeeze_lemon
        3 -> R.string.drink_lemonade
        4 -> R.string.restart_process
        else -> R.string.select_lemon
    }

    val resourceImage = when (currentIndex) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        4 -> R.drawable.lemon_restart
        else -> R.drawable.lemon_tree
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Barre jaune avec le titre
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFEB3B)) // Jaune
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Lemonade",
                style = TextStyle(fontSize = 24.sp, color = Color.Black)
            )
        }

        // Espace pour centrer le contenu suivant verticalement
        Spacer(modifier = Modifier.weight(1f))

        // Image et texte interactifs
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(16.dp))
                .clickable {
                    when (currentIndex) {
                        1 -> {
                            // Passe à l'étape 2 et génère un nombre aléatoire entre 2 et 4
                            squeezeTapCount = (2..4).random()
                            currentIndex = 2
                        }
                        2 -> {
                            // Réduit le compteur de pressions nécessaires
                            if (squeezeTapCount > 1) {
                                squeezeTapCount--
                            } else {
                                currentIndex = 3
                            }
                        }
                        3 -> {
                            // Passe à l'étape 4
                            currentIndex = 4
                        }
                        4 -> {
                            // Réinitialise le processus
                            currentIndex = 1
                        }
                        else -> {}
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = resourceImage),
                contentDescription = "Lemon",
                modifier = Modifier.size(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = stepText),
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // Espace pour équilibrer le contenu
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LemonadeTheme {
        LemonadApp()
    }
}