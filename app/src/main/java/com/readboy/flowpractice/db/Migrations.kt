package com.readboy.flowpractice.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migrations {
    class migration_1_2(): Migration(1,2){
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `SynTheme` (" +
                    "`id` INTEGER NOT NULL," +
                    " `book` TEXT," +
                    " `show_title` TEXT," +
                    " `description` TEXT," +
                    " `book_short_name` TEXT," +
                    " `title` TEXT  NOT NULL," +
                    " `grade` INTEGER NOT NULL," +
                    " `volume` INTEGER NOT NULL," +
                    " `unit` INTEGER NOT NULL," +
                    " PRIMARY KEY(`id`))"
            )
        }
    }
}