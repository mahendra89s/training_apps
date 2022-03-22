package com.example.demo_recyclerview

import java.text.FieldPosition

interface ItemClickListener {
    fun onclick(position : Int,lastPosition : Int)
}