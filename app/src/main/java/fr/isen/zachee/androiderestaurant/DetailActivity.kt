package fr.isen.zachee.androiderestaurant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.isen.zachee.androiderestaurant.NetWork.Dish
import java.math.BigDecimal


class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val  dish = intent.getSerializableExtra(DISH_EXTRA_KEY) as? Dish
        setContent {
            val ingredient = dish?.ingredients?.map { tabIngredient ->
                (tabIngredient.name)
            }?.joinToString(", ") ?: ""


            Column {
                Box(
                    modifier = Modifier
                        .width(500.dp)
                        .height(50.dp)
                        .border(2.dp, Color.Blue)
                        .background(Color.Yellow)
                )
                {
                    Text(
                        stringResource(R.string.app_name),
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(dish?.images?.first())
                        .build(),
                    null,
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    error = painterResource(R.drawable.ic_launcher_foreground),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .height(80.dp)
                        .clip(RoundedCornerShape(10))
                        .padding(8.dp)
                        .size(500.dp)
                )

                Text(
                    "Liste d'ingrédients :\t\t${ingredient}",
                    Modifier
                        .height(150.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "${dish?.name} \n ",
                    Modifier
                        .padding(10.dp)
                        .height(80.dp)
                        .align(Alignment.CenterHorizontally)
                )

                var quantity by remember { mutableStateOf(0) }

                NumberIncrementerBox { newQuantity ->
                    quantity = newQuantity
                }

                DishPriceBox(dish = dish, quantity = quantity)


            }
        }

                  }




    companion object{
        val  DISH_EXTRA_KEY = " DISH_EXTRA_KEY"
    }

}

@Composable
fun NumberIncrementerBox(onQuantityChanged: (Int) -> Unit) {
    var count by remember { mutableStateOf(1) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Button(
                onClick = {
                    if (count >1)
                    count--
                    onQuantityChanged(count)
                },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text("-")
            }
            Text(
                text = count.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = {
                    count++
                    onQuantityChanged(count)
                },
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text("+")
            }
        }
    }
}

@Composable
fun DishPriceBox(dish: Dish?, quantity: Int) {
    val totalPrice = (dish?.prices?.firstOrNull()?.price?.toDouble() ?: 0.0) * quantity.toDouble()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
            Text(
                text = "Prix : $totalPrice €",
                modifier = Modifier
                    .width(80.dp)
                    .height(20.dp)
            )

    }
}


