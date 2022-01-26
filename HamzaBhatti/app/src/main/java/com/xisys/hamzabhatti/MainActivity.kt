package com.xisys.hamzabhatti

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberImagePainter
import com.xisys.hamzabhatti.enums.Status
import com.xisys.hamzabhatti.models.AllCards
import com.xisys.hamzabhatti.ui.theme.ComposeRecyclerViewTheme
import com.xisys.hamzabhatti.viewModels.HomeViewModel

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        callEndpoint()
    }

    private fun callEndpoint() {

        viewModel.getAllFeeds().observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (resource.response!!.body() != null) {
                            if (resource.response.body()!!.page.cards.size > 0) {
                                val allFeeds = resource.response.body()!!.page.cards
                                setContent {
                                    ComposeRecyclerViewTheme {
                                        MyApp(allFeeds)
                                    }
                                }

                            }
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })

    }
}

@Composable
fun MyApp(allFeeds: ArrayList<AllCards>) {
    Scaffold(
        content = {
            FeedContent(allFeeds)
        }
    )
}

@Composable
fun FeedContent(allFeeds: ArrayList<AllCards>) {
    val feeds = remember { allFeeds }
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = feeds,
            itemContent = {
                FeedListItem(feed = it)
            })
    }
}

@SuppressLint("Range")
@Composable
fun FeedListItem(feed: AllCards) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(14.dp))
    ) {
        Row {
            when {
                feed.card_type.equals("text") -> {
                    Column(
                        Modifier.padding(10.dp)
                    ) {
                        Text(
                            text = feed.card.value,
                            fontSize = feed.card.attributes.font.size.sp,
                            color = Color(android.graphics.Color.parseColor(feed.card.attributes.text_color))
                        )
                    }
                }
                feed.card_type.equals("title_description") -> {
                    Column(
                        Modifier.padding(10.dp)
                    ) {
                        Text(
                            text = feed.card.title.value,
                            fontSize = feed.card.title.attributes.font.size.sp,
                            color = Color(android.graphics.Color.parseColor(feed.card.title.attributes.text_color))
                        )
                        Text(
                            text = feed.card.description.value,
                            fontSize = feed.card.description.attributes.font.size.sp,
                            color = Color(android.graphics.Color.parseColor(feed.card.description.attributes.text_color))
                        )

                    }

                }
                feed.card_type.equals("image_title_description") -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        FeedImage(feed = feed)
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(10.dp),
                        ) {

                            Text(
                                text = feed.card.title.value,
                                fontSize = feed.card.title.attributes.font.size.sp,
                                color = Color(android.graphics.Color.parseColor(feed.card.title.attributes.text_color))
                            )
                            Text(
                                text = feed.card.description.value,
                                fontSize = feed.card.description.attributes.font.size.sp,
                                color = Color(android.graphics.Color.parseColor(feed.card.description.attributes.text_color))
                            )

                        }
                    }
                }
            }

        }
    }


}

@Composable
fun FeedImage(feed: AllCards) {
    Image(
        painter = rememberImagePainter(feed.card.image.url),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.size(
            feed.card.image.size.width.dp,
            feed.card.image.size.height.dp
        )
    )

}
