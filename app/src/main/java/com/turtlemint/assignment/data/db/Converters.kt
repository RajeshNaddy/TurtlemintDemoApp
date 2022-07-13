package com.turtlemint.assignment.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.turtlemint.assignment.data.model.Comment
import com.turtlemint.assignment.data.model.Label
import com.turtlemint.assignment.data.model.User

class Converters {
    @TypeConverter
    fun fromLabelList(value: List<Label>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Label>>() {}.type
        return gson.toJson(value, type)
    }


    @TypeConverter
    fun toLabelList(value: String): List<Label> {
        val gson = Gson()
        val type = object : TypeToken<List<Label>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromUser(value: User): String {
        val gson = Gson()
        return gson.toJson(value, User::class.java)
    }

    @TypeConverter
    fun toUser(value: String): User {
        val gson = Gson()
        return gson.fromJson(value, User::class.java)
    }
}