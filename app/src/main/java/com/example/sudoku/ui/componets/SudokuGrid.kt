package com.example.sudoku.ui.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sudoku.data.Cell

@Composable
fun SudokuGrid(
    modifier: Modifier,
    contentAlignment: Alignment,
    selectedIndex: Int?,
    gridValues: List<Cell>,
    onIndexSelected: (Int?) -> Unit
) {

    val items = (0 until 81).toList()
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = contentAlignment
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(9),
            modifier = Modifier
                .size(400.dp)
        ) {
            items(items) { index ->
                val isSelected = (index == selectedIndex)
                val lastCol = setOf(8, 17, 26, 44, 53, 71)

                val lastRow = setOf(72, 73, 74, 76, 77, 79)

                val leftBold =
                    setOf(3, 12, 21, 39, 48, 66, 6, 15, 24, 42, 51, 69)
                val topBold =
                    setOf(27, 28, 29, 31, 32, 34, 54, 55, 56, 58, 59, 61)


                val number = gridValues[index].value
                val displayText = if (number == 0) "" else number.toString()

                val borderModifier = when {
                    index in lastCol -> Modifier.customBorderSize(
                        startWidth = 2.dp,
                        topWidth = 2.dp,
                        endWidth = 2.dp
                    )

                    index in lastRow -> Modifier.customBorderSize(
                        startWidth = 2.dp,
                        topWidth = 2.dp,
                        bottomWidth = 2.dp
                    )

                    index == 80 -> Modifier.customBorderSize(
                        startWidth = 2.dp,
                        topWidth = 2.dp,
                        bottomWidth = 2.dp,
                        endWidth = 2.dp
                    )

                    index in leftBold -> Modifier.customBorderSize(
                        startWidth = 4.dp,
                        topWidth = 2.dp
                    )

                    index in topBold -> Modifier.customBorderSize(
                        startWidth = 2.dp,
                        topWidth = 4.dp
                    )

                    index in setOf(30, 33, 57, 60) -> Modifier.customBorderSize(
                        startWidth = 4.dp,
                        topWidth = 4.dp
                    )

                    index in setOf(35, 62) -> Modifier.customBorderSize(
                        startWidth = 2.dp,
                        topWidth = 4.dp,
                        endWidth = 2.dp
                    )

                    index in setOf(75, 78) -> Modifier.customBorderSize(
                        startWidth = 4.dp,
                        topWidth = 2.dp,
                        bottomWidth = 2.dp
                    )

                    else -> Modifier.customBorderSize(startWidth = 2.dp, topWidth = 2.dp)
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(if (isSelected) Color.Gray else Color.White)
                        .then(borderModifier)
                        .clickable {
                            val newIndex = if (isSelected) null else index
                            onIndexSelected(newIndex)
                        }
                ) {
                    Text(
                        text = displayText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = gridValues[index].color,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

fun Modifier.customBorderSize(
    topWidth: Dp = 0.dp,
    bottomWidth: Dp = 0.dp,
    startWidth: Dp = 0.dp,
    endWidth: Dp = 0.dp,
    color: Color = Color.Black
): Modifier = this.drawWithContent {
    // 先绘制内容
    drawContent()

    // 转换 Dp 到 Px
    val topPx = topWidth.toPx()
    val bottomPx = bottomWidth.toPx()
    val startPx = startWidth.toPx()
    val endPx = endWidth.toPx()

    // 如果所有边都是 0，则不绘制
    if (topPx == 0f && bottomPx == 0f && startPx == 0f && endPx == 0f) return@drawWithContent

    // 绘制上边
    if (topPx > 0) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = topPx
        )
    }

    // 绘制下边
    if (bottomPx > 0) {
        drawLine(
            color = color,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = bottomPx
        )
    }

    // 绘制左边（start）
    if (startPx > 0) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(0f, size.height),
            strokeWidth = startPx
        )
    }

    // 绘制右边（end）
    if (endPx > 0) {
        drawLine(
            color = color,
            start = Offset(size.width, 0f),
            end = Offset(size.width, size.height),
            strokeWidth = endPx
        )
    }
}