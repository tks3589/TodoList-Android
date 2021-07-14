package com.aaron.todolist_android.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TodoItem::class],version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    companion object{
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase{
            if(instance == null)
                instance = Room.databaseBuilder(context,AppDatabase::class.java,"todo_list_db").build()

            return instance!!
        }
    }

    abstract fun todoItemDao(): TodoItemDao
}