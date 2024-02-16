package fr.isen.zachee.androiderestaurant.ui.theme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.isen.zachee.androiderestaurant.Panier.Panier
import fr.isen.zachee.androiderestaurant.Panier.PanierElem
import fr.isen.zachee.androiderestaurant.R

class PanierActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
          PanierView()
        }
    }
}

@Composable
fun PanierView() {
    val context = LocalContext.current
    val basketItems = remember {
        mutableStateListOf<PanierElem>()
    }
    LazyColumn {
        items(basketItems) {
            BasketItemView(it,basketItems)
        }
    }
    basketItems.addAll(Panier.current(context).items)
}
@Composable
fun BasketItemView(item: PanierElem, basketItems: MutableList<PanierElem>) {
    Card {
        val context = LocalContext.current
        Card(border =  BorderStroke(1.dp, Color.Black),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Row(Modifier.padding(8.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.dish.images.first())
                        .build(),
                    null,
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    error = painterResource(R.drawable.ic_launcher_foreground),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                        .clip(RoundedCornerShape(10))
                        .padding(8.dp)
                )
                Column(
                    Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .padding(8.dp)
                ) {
                    Text(item.dish.name)
                    Text("${item.dish.prices.first().price} €")
                }

                Spacer(Modifier.weight(1f))
                Text(item.count.toString(),
                    Modifier.align(alignment = Alignment.CenterVertically))
                Button(onClick = {
                    // delete item and redraw view
                    Panier.current(context).delete(item, context)
                    basketItems.clear()
                    basketItems.addAll(Panier.current(context).items)
                }) {
                    Text("X")
                }
            }
        }
    }
}