package com.example.demo2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonElevation
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import demo2.composeapp.generated.resources.AppleLogo
import demo2.composeapp.generated.resources.GoogleLogo
import demo2.composeapp.generated.resources.Res
import demo2.composeapp.generated.resources.TGLogo
import demo2.composeapp.generated.resources.Welcome
import demo2.composeapp.generated.resources.ios
import org.jetbrains.compose.resources.painterResource

@Composable
fun WelcomeScreen(modifier: Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(Res.drawable.Welcome),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
            )
//        Image(
//            painter = painterResource(Res.drawable.Background),
//            contentDescription = null,
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Crop
//        )
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Welcome", color = Color.White, fontSize = 40.sp, maxLines = 1)



            }
        }

        Box(modifier = modifier.align(Alignment.BottomCenter)){

            Column(modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {

                Button(
                    onClick = { }, modifier = Modifier
                        .padding(5.dp),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text("Зарегестрироваться", modifier = Modifier.padding(5.dp))

                }
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(RoundedCornerShape(35.dp)),
                    shape = RoundedCornerShape(15.dp),
                    border = BorderStroke(2.dp, Color.Blue),




                ) {
                    Text("Войти")

                }
                Row(modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically){

                     Box(modifier = Modifier
                         .size(40.dp)
                         .padding( end = 5.dp)){
                          Image(painter = painterResource(Res.drawable.GoogleLogo),
                              contentDescription = null,
                              contentScale = ContentScale.Inside)
                     }
                    Box(modifier = Modifier
                        .size(40.dp)
                        .padding( end = 5.dp)){
                        Image(painter = painterResource(Res.drawable.ios),
                            contentDescription = null,
                            contentScale = ContentScale.Inside)
                    }
                    Box(modifier = Modifier
                        .size(40.dp)
                        .padding( end = 5.dp)){
                        Image(painter = painterResource(Res.drawable.TGLogo),
                            contentDescription = null,
                            contentScale = ContentScale.Inside)
                    }

                }


            }

        }
    }
}