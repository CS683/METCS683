package edu.bu.metcs.graphicsexample

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableOpenTarget
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class DrawPath(
    val path: Path,
    val paint: Paint,
    val startPoint: Offset,
    val endPoint: Offset
)

// this code is based on the exam
@Composable
fun AnimationExample(
    onBack: () -> Unit
) {
    var iconVector by remember { mutableStateOf(Icons.Filled.PlayArrow) }
    var isRotated by remember { mutableStateOf(false)}
    val targetAngle = if (isRotated) 360f else 0f

    var big by remember { mutableStateOf(false)}
    val rectSize by animateFloatAsState(
        targetValue = if (big) 300f else 150f,
    )

    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val rectColor by infiniteTransition.animateColor(
        initialValue = Color.Green,
        targetValue = Color.Blue,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "color"
    )
    val rotateAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = targetAngle,
        animationSpec = infiniteRepeatable<Float>(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "rotation"
    )


    Column(modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Animation Example",
            fontSize = 36.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawRoundRect(
                        color = Color.LightGray,
                        cornerRadius = CornerRadius(10.dp.toPx())
                    )
                }
                .padding(8.dp)
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Next"
                )
            }
        }
        Text("The following rectangle will rotate and change its color when click the play button!")
        IconButton(onClick = {
            isRotated = !isRotated
            if (isRotated) {
                iconVector = Icons.Default.Clear
            } else {
                iconVector = Icons.Filled.PlayArrow
            }
        }) {
            Icon(
                imageVector = iconVector,
                contentDescription = "Next"
            )
        }
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .size(rectSize.dp)
                .clickable { big = !big }
        ) {
            rotate(degrees = rotateAngle) {
                val canvasQuadrantSize = size / 4F
                drawRect(
                    color = rectColor,
                    size = canvasQuadrantSize,
                    topLeft = Offset(size.width / 4f, size.height / 4f)
                )
            }
        }
        Text("You can draw in the following area.")
        MyDrawing()
    }

}
@Composable
fun MyDrawing(){
    var currentPath by remember { mutableStateOf(Path()) }
    var paths by remember { mutableStateOf(mutableListOf<DrawPath>()) }
    var currentPaint by remember {
        mutableStateOf(
            Paint().apply {
                color = Color.Black
                style = PaintingStyle.Stroke
                strokeWidth = 5f
                isAntiAlias = true
            }
        )
    }
    var currentStartPoint by remember { mutableStateOf(Offset.Zero) }
    var currentEndPoint by remember { mutableStateOf(Offset.Zero) }

    Column() {
        IconButton(onClick = {
            paths.clear()
        }) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Next"
            )
        }

        Canvas(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput("drawing") {
                detectDragGestures(
                    onDragStart = {
                        currentPath.moveTo(it.x, it.y)
                        currentEndPoint = it
                        currentStartPoint = it
                    },
                    onDrag = { change, dragAmount ->
                        // currentPath.lineTo(curPoint.x, curPoint.y)
                        // currentPath.lineTo(change.position.x, change.position.y)

                        val newOffset = change.position
                        if (isWithinBounds(newOffset, size.width + 0f, size.height + 0f)) {
                            currentPath.lineTo(newOffset.x, newOffset.y)
                            currentEndPoint = newOffset
                        }
                    },
                    onDragEnd = {
                        paths.add(
                            DrawPath(
                                currentPath, currentPaint,
                                currentStartPoint, currentEndPoint
                            )
                        )
                        currentPath = Path()
                    }
                )
            }
        ) {

            paths.forEach { path ->
                drawPath(
                    path = path.path,
                    color = path.paint.color,
                    style =
                    Stroke(width = path.paint.strokeWidth)
                )
            }

            drawPath(
                path = currentPath,
                color = currentPaint.color,
                style = Stroke(width = currentPaint.strokeWidth)
            )

        }
    }
}
fun isWithinBounds(offset: Offset, width: Float, height: Float): Boolean {
    return offset.x in 0f..width && offset.y in 0f..height
}