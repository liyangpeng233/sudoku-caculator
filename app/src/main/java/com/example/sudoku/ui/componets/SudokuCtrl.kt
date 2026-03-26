package com.example.sudoku.ui.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SudokuCtrl(
    modifier: Modifier,
    contentAlignment: Alignment,
    onNumberClick: (Int) -> Unit,
    onNumberDelete: () -> Unit,
    onNumberClear: () -> Unit,
    onSolve:() -> Unit
) {

    val items = (0..11).toList()

    // 创建一个可观察的状态列表，用于存储每个格子的按下状态 (默认都是 false)
    val pressedStates =
        remember { mutableStateListOf<Boolean>().apply { repeat(12) { add(false) } } }

    Box(
        modifier = modifier.fillMaxSize(0.5f),
        contentAlignment = contentAlignment
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .size(400.dp)
        ) {
            items(items) { index ->
                // 获取当前格子的按下状态
                val isPressed = pressedStates[index]
                // 获取当前格子的按下状态
                if (index <= 8) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .border(1.dp, Color.Gray)
                            .background(if (isPressed) Color.Gray else Color.White)
                            .clickable {
                                onNumberClick(index + 1)
                            }
                    ) {
                        Text(
                            text = "${index + 1}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                } else if (index == 9) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .border(1.dp, Color.Gray)
                            .background(if (isPressed) Color.Gray else Color.White)
                            .clickable {
                                onNumberDelete()
                            }
                    ) {
                        Text(
                            text = "删除",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.align(Alignment.Center))
                    }
                } else if (index == 10) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .border(1.dp, Color.Gray)
                            .background(if (isPressed) Color.Gray else Color.White)
                            .clickable {
                                onNumberClear()
                            }
                    ) {
                        Text(
                            text = "清空",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.align(Alignment.Center))
                    }
                } else if (index == 11) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .border(1.dp, Color.Gray)
                            .background(if (isPressed) Color.Gray else Color.White)
                            .clickable {
                                onSolve()
                            }
                    ) {
                        Text(
                            text = "求解",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}