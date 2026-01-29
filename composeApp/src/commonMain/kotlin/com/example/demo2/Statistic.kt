package com.example.demo2

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import demo2.composeapp.generated.resources.Diagramm
import demo2.composeapp.generated.resources.Res
import demo2.composeapp.generated.resources.autorenew_24px
import demo2.composeapp.generated.resources.bar_chart_24px
import demo2.composeapp.generated.resources.dark_mode_24px
import demo2.composeapp.generated.resources.home_24px
import demo2.composeapp.generated.resources.info_24px
import demo2.composeapp.generated.resources.keep_24px
import demo2.composeapp.generated.resources.link_24px
import demo2.composeapp.generated.resources.more_vert_24px
import demo2.composeapp.generated.resources.notifications_24px
import demo2.composeapp.generated.resources.settings_24px
import org.jetbrains.compose.resources.painterResource

@Composable
fun StatScreen(modifier: Modifier){





    Box(
        modifier
            .fillMaxSize()
            .background(Color.White)
    ) {


        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 30.dp)
//                .fillMaxSize()
                .background(brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color.White
                    ),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
                )
            ,
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {


          Row(modifier = Modifier
              .fillMaxWidth()
              .padding(15.dp),
              horizontalArrangement = Arrangement.Start,
              verticalAlignment = Alignment.CenterVertically){

              Box( modifier = Modifier
                  .size(40.dp)
                  .background(MaterialTheme.colorScheme.ContainerColor, RoundedCornerShape(5.dp)),
               ){

                  Icon(painter = painterResource(Res.drawable.autorenew_24px),
                      contentDescription = null,
                      modifier = Modifier
                          .align(Alignment.Center))

              }



          }
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)){
                item {
                    Box(modifier = Modifier
//                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryGray, RoundedCornerShape(15.dp))
                        .padding(start = 5.dp, end = 5.dp)){

                        Column(modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally) {

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                            ) {
                                Text(
                                    text = "Товар 1",
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .padding(start = 10.dp),
                                    fontSize = 15.sp
                                )
                                Icon(painter = painterResource(Res.drawable.keep_24px),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(end = 20.dp))
                                Icon(painter = painterResource(Res.drawable.more_vert_24px),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .align(Alignment.TopEnd))
                            }

                            val chartData = listOf(
                                BarData("Янв", 45f, MaterialTheme.colorScheme.darkGray),
                                BarData("Фев", 60f, MaterialTheme.colorScheme.darkGray),
                                BarData("Мар", 30f, MaterialTheme.colorScheme.darkGray),
                                BarData("Апр", 75f, MaterialTheme.colorScheme.darkGray),
                                BarData("Май", 50f, MaterialTheme.colorScheme.darkGray),
                                BarData("Апр", 25f, MaterialTheme.colorScheme.darkGray),
                                BarData("Апр", 55f, MaterialTheme.colorScheme.darkGray),
                                BarData("Апр", 60f, MaterialTheme.colorScheme.darkGray),
                            )
                            Box(modifier = Modifier
                                .padding(start = 35.dp, end = 35.dp)) {
                                BarChartCanvas(
                                    data = chartData, // ← именно так!
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp) // обязательно задай высоту!
                                )
                            }
//                            Image(painter = painterResource(Res.drawable.Diagramm),
//                                contentDescription = null,
//                                contentScale = ContentScale.Crop)


                        }
                    }
                }

            }





        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .align(Alignment.TopCenter)
            .background(color = Color.White)) {


            Text(text = "Статистика", fontSize = 25.sp,
                modifier = Modifier
                    .padding(start = 10.dp))

            Icon(painter = painterResource(Res.drawable.info_24px),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 10.dp))
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