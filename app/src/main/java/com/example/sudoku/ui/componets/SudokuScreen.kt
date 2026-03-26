package com.example.sudoku.ui.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sudoku.data.Cell

@Composable
fun SudokuScreen(modifier: Modifier = Modifier) {

    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    var gridValues by remember { mutableStateOf(List(81) { Cell(0, Color.Green) }) }

    var isSolving by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .background(Color.Cyan)
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SudokuTitle(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        )
        SudokuGrid(
            modifier = Modifier
                .weight(5f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
            selectedIndex = selectedIndex,
            gridValues = gridValues,
            onIndexSelected = { newIdx ->
                if (!isSolving) selectedIndex = newIdx
            }
        )

        SudokuCtrl(
            modifier = Modifier
                .weight(3f)
                .fillMaxWidth(0.6f),
            contentAlignment = Alignment.Center,
            onNumberClick = { number ->
                if (isSolving) return@SudokuCtrl
                selectedIndex?.let { index ->
                    val newValues = gridValues.toMutableList()
                    newValues[index] = Cell(number, Color.Black, false)
                    selectedIndex = null
                    gridValues = newValues
                }
            },
            onNumberDelete = {
                if (isSolving) return@SudokuCtrl
                selectedIndex?.let { index ->
                    val newValues = gridValues.toMutableList()
                    newValues[index] = Cell(0, Color.Green, false)
                    selectedIndex = null
                    gridValues = newValues
                }
            },
            onNumberClear = {
                if (isSolving) return@SudokuCtrl
                val newValues = List(81) { Cell(0, Color.Green, false) }
                gridValues = newValues
            },
            onSolve = {
                if (isSolving) return@SudokuCtrl
                isSolving = true
                selectedIndex = null


                val data = Array(9) { Array(9) { Cell(0, Color.Black, false) } }
                var p = 0
                for (r in 0..8) {
                    for (c in 0..8) {
                        data[r][c] = gridValues[p++]
                    }
                }
                val finish = booleanArrayOf(false)
                //DFS，设置延迟时间
                dfs(
                    board = data,
                    i = 0,
                    j = 0,
                    finish = finish
                )
                isSolving = false
                gridValues = data.flatMap { it.toList() }

            }
        )
    }
}

fun isValid(board: Array<Array<Cell>>, num: Int, i: Int, j: Int): Boolean {
    // 检查行
    for (k in 0 until 9) {
        if (board[i][k].value == num) return false
    }

    // 检查列
    for (k in 0 until 9) {
        if (board[k][j].value == num) return false
    }

    // 检查 3x3 宫格
    val startRow = (i / 3) * 3
    val startCol = (j / 3) * 3

    for (r in 0 until 3) {
        for (c in 0 until 3) {
            if (board[startRow + r][startCol + c].value == num) {
                return false
            }
        }
    }

    return true
}


fun dfs(
    board: Array<Array<Cell>>,
    i: Int,
    j: Int,
    finish: BooleanArray
) {
    if (finish[0]) return

    // 换行
    if (j == 9) {
        dfs(board, i + 1, 0, finish)
        return
    }

    // 完成
    if (i == 9) {
        finish[0] = true
        return
    }

    // 跳过非空格子
    if (board[i][j].value != 0) {
        dfs(board, i, j + 1, finish)
        return
    }

    // 尝试填入 1-9
    for (num in 1..9) {
        if (isValid(board, num, i, j)) {

            board[i][j] = Cell(num, Color.Blue, true)

            // 2. 递归
            dfs(board, i, j + 1, finish)

            if (finish[0]) return

            // 3. 回溯 (撤销选择)
            board[i][j] = Cell(0, Color.Green, false)
        }
    }
}


