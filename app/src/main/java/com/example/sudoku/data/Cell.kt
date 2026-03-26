package com.example.sudoku.data

import androidx.compose.ui.graphics.Color

data class Cell(val value:Int, val color: Color,val isTemp: Boolean = false)