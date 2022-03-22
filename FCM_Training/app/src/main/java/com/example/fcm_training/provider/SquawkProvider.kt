package com.example.fcm_training.provider

import android.net.Uri
import net.simonvt.schematic.annotation.ContentProvider
import net.simonvt.schematic.annotation.ContentUri
import net.simonvt.schematic.annotation.TableEndpoint


@ContentProvider(authority = SquawkProvider.AUTHORITY, database = SquawkDatabase::class)
object SquawkProvider {
    const val AUTHORITY = "android.example.com.squawker.provider.provider"

    @TableEndpoint(table = SquawkDatabase.SQUAWK_MESSAGES)
    object SquawkMessages {
        @ContentUri(
            path = "messages",
            type = "vnd.android.cursor.dir/messages",
            defaultSort = SquawkContract.COLUMN_DATE.toString() + " DESC"
        )
        val CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/messages")
    }
}