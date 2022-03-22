package com.example.fcm_training.provider

import android.content.SharedPreferences
import net.simonvt.schematic.annotation.*
import java.lang.StringBuilder


object SquawkContract {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey(onConflict = ConflictResolutionType.REPLACE)
    @AutoIncrement
    const val COLUMN_ID = "_id"

    @DataType(DataType.Type.TEXT)
    @NotNull
    const val COLUMN_AUTHOR = "author"

    @DataType(DataType.Type.TEXT)
    @NotNull
    const val COLUMN_AUTHOR_KEY = "authorKey"

    @DataType(DataType.Type.TEXT)
    @NotNull
    const val COLUMN_MESSAGE = "message"

    @DataType(DataType.Type.INTEGER)
    @NotNull
    const val COLUMN_DATE = "date"


    // Topic keys as matching what is found in the database
    val ASSER_KEY = "key_asser"
    val CEZANNE_KEY = "key_cezanne"
    val JLIN_KEY = "key_jlin"
    val LYLA_KEY = "key_lyla"
    val NIKITA_KEY = "key_nikita"
    val TEST_ACCOUNT_KEY = "key_test"


    val INSTRUCTOR_KEYS = arrayOf(
        ASSER_KEY, CEZANNE_KEY, JLIN_KEY, LYLA_KEY, NIKITA_KEY
    )

    /**
     * Creates a SQLite SELECTION parameter that filters just the rows for the authors you are
     * currently following.
     */
    fun createSelectionForCurrentFollowers(preferences: SharedPreferences): String? {
        val stringBuilder = StringBuilder()
        //Automatically add the test account
        stringBuilder.append(COLUMN_AUTHOR_KEY).append(" IN  ('").append(TEST_ACCOUNT_KEY)
            .append("'")
        for (key in INSTRUCTOR_KEYS) {
            if (preferences.getBoolean(key, false)) {
                stringBuilder.append(",")
                stringBuilder.append("'").append(key).append("'")
            }
        }
        stringBuilder.append(")")
        return stringBuilder.toString()
    }
}
