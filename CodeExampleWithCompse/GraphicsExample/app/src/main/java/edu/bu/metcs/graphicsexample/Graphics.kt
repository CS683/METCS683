package edu.bu.metcs.graphicsexample

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Paint.Align
import android.widget.Space
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// this code is based on the exam
@Composable
fun GraphicsExample(
    onNext: () -> Unit
) {
    var pointerOffset by remember { mutableStateOf(Offset(0f, 0f)) }

    Column(modifier = Modifier.fillMaxSize()
        .pointerInput("dragging") {
            detectDragGestures { change, dragAmount ->
            pointerOffset += dragAmount
            }
        }
        .onSizeChanged {
            pointerOffset = Offset(it.width / 2f, it.height / 2f)
        }
        .drawWithContent {
            drawContent()
            // draws a fully black area with a small keyhole at pointerOffset that’ll show part of the UI.
            drawRect(
                Brush.radialGradient(
                    listOf(Color.Blue, Color.Transparent),
                    center = pointerOffset,
                    radius = 20.dp.toPx(),
                ),
                //topLeft = pointerOffset - Offset(100.dp.toPx(), 100.dp.toPx()),
                topLeft = Offset(0f,0f),
               // size = Size(100.dp.toPx(), 100.dp.toPx()),
                size = Size(size.width, size.height),
                alpha = 0.5f
            )

        }

    ) {
        Text(text = "Graphics Example",
            fontSize = 36.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
                .drawBehind {
                    drawRoundRect(
                        color = Color.LightGray,
                        cornerRadius = CornerRadius(10.dp.toPx())
                    )
                    drawLine(
                        color = Color.Blue,
                        start = Offset(10f, 20f),
                        end = Offset(size.width- 10f,20f),
                        strokeWidth = 10f
                    )
                    drawLine(
                        color = Color.Blue,
                        start = Offset(10f, size.height-20f),
                        end = Offset(size.width -10f,size.height-20f),
                        strokeWidth = 10f
                    )
                }.padding (8.dp)
        )
        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                     painter = painterResource(id = R.drawable.welcome),
                    contentDescription = "Welcome"
                )
            IconButton(
                onClick = onNext,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Next"
                )
            }
        }
        Text("In the following Canvas Area, we draw a yellow circle, a green rectangle and a red line",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
                    .background(Color.Blue)
        )
        Canvas(modifier = Modifier.size(300.dp)) {
            // Draw a blue circle
            drawCircle(
                color = Color.Yellow,
                center = center,
                radius = size.minDimension / 3
            )
            // Draw a green rectangle
            drawRect(
                color = Color.Green,
                topLeft = Offset(20f, 20f),
                size = Size(100f, 150f)
            )

            // Draw a red line
            drawLine(
                color = Color.Red,
                start = Offset(20f, 20f),
                end = Offset(size.width - 20, size.height - 20),
                strokeWidth = 5f
            )
        }

     }

}