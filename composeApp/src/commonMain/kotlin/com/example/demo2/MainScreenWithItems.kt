package com.example.demo2


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import demo2.composeapp.generated.resources.Res
import demo2.composeapp.generated.resources.arrow
import demo2.composeapp.generated.resources.arrow_forward_24px
import demo2.composeapp.generated.resources.bar_chart_24px
import demo2.composeapp.generated.resources.dark_mode_24px
import demo2.composeapp.generated.resources.filter_alt_24px
import demo2.composeapp.generated.resources.home_24px
import demo2.composeapp.generated.resources.line_end_arrow_24px
import demo2.composeapp.generated.resources.link_24px
import demo2.composeapp.generated.resources.more_horiz_24px
import demo2.composeapp.generated.resources.notifications_24px
import demo2.composeapp.generated.resources.settings_24px
import org.jetbrains.compose.resources.painterResource


@Composable
fun MainScreenWithItems(modifier: Modifier) {

    var ProductList by remember { mutableStateOf(mutableListOf(Product(name = "товар1", price = 10000, availability = true, id = 1))) }

    Box(
        modifier
            .fillMaxSize()
    ) {


        LazyColumn(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            Color.White
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        painter = painterResource(Res.drawable.dark_mode_24px),
                        contentDescription = null,
                        modifier = Modifier
                            .align(
                                Alignment.CenterStart
                            )
                            .padding(10.dp)
                            .size(30.dp)
                    )
                }

            }



            item {
                val message = remember { mutableStateOf("") }

                TextField(
                    value = message.value,
                    textStyle = TextStyle(fontSize = 15.sp),
                    onValueChange = { newText -> message.value = newText },
                    placeholder = { Text("Вставьте ссылку на товар") },
                    modifier = Modifier.clip(RoundedCornerShape(30.dp)),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = MaterialTheme.colorScheme.ContainerColor
                    ),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(Res.drawable.link_24px),
                            contentDescription = null,
                            modifier = Modifier.rotate(320f)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                    label = { Text(" Вставьте ссылку на товар") }
                )



                Button(onClick = { }, modifier = Modifier.padding(10.dp)) {

                    Text(text = "Продолжить", modifier = Modifier)
//                Icon(
//                    painter = painterResource(Res.drawable.arrow_forward_24px),
//                    contentDescription = null,
//                    modifier = Modifier
//                )
                }

            }
            item{
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {

//                    Box(modifier = Modifier
//                        .clip(RoundedCornerShape(25.dp))
//                        .background( MaterialTheme.colorScheme.ContainerColor)) {

                        IconButton(onClick = { }, modifier =  Modifier
//                            .align(Alignment.Center)

                            .padding(3.dp)){

                            Icon(painter = painterResource(Res.drawable.filter_alt_24px),
                                contentDescription = null,)


//                        }



                    }

                }
            }

            items(ProductList, {ProductList -> ProductList.id}) { item ->




                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .background( MaterialTheme.colorScheme.ContainerColor, RoundedCornerShape(20.dp))

                ) {

                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(text = item.name, modifier = Modifier)
                        Text(text = "${item.price}₽")
                    }


                    Box(modifier = Modifier
//                        .background(Color.Red)
                        .align(Alignment.CenterEnd)){

                        IconButton(onClick = { }, modifier =  Modifier
                            .align(Alignment.TopEnd)
//                            .align(Alignment.Center)

                            .padding(3.dp),
                            ){

                            Icon(painter = painterResource(Res.drawable.more_horiz_24px),
                                contentDescription = null,)


//                        }



                        }

                    }


                }
            }


        }




        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 7.dp)
        ) {

            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(35.dp))
                        .background(MaterialTheme.colorScheme.ContainerColor),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .padding(5.dp)
                            .clip(RoundedCornerShape(35.dp))
                            .background(Color.Transparent)
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.home_24px),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(4.dp)
                                .align(Alignment.Center),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .padding(5.dp)
                            .clip(RoundedCornerShape(35.dp))
                            .background(MaterialTheme.colorScheme.ContainerColor)
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.notifications_24px),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(4.dp)
                                .align(Alignment.Center)

                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .padding(5.dp)
                            .clip(RoundedCornerShape(35.dp))
                            .background(MaterialTheme.colorScheme.ContainerColor)
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.bar_chart_24px),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(4.dp)
                                .align(Alignment.Center)

                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .padding(5.dp)
                            .clip(RoundedCornerShape(35.dp))
                            .background(MaterialTheme.colorScheme.ContainerColor)
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.settings_24px),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(4.dp)
                                .align(Alignment.Center)

                        )
                    }

                }
            }

        }


    }


}



