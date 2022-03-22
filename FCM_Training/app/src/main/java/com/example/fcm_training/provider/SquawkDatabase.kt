package com.example.fcm_training.provider

import net.simonvt.schematic.annotation.Database
import net.simonvt.schematic.annotation.Table


@Database(version = SquawkDatabase.VERSION)
class SquawkDatabase {

    companion object{
        const val VERSION = 4
        @Table(SquawkContract::class)
        const val SQUAWK_MESSAGES = "squawk_messages"
    }


}