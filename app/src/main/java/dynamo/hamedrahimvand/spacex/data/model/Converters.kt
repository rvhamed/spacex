package dynamo.hamedrahimvand.spacex.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 *
 *@author Hamed.Rahimvand
 *@since 2/27/21
 */
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
class LinksTypeConverter {
    @TypeConverter
    fun linksToString(source: Links?): String? {
        if (source == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Links>() {

        }.type
        return gson.toJson(source, type)
    }

    @TypeConverter
    fun stringToLinks(source: String?): Links? {
        if (source == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Links>() {

        }.type
        return gson.fromJson(source, type)
    }
}
class PatchTypeConverter {
    @TypeConverter
    fun patchToString(source: Patch?): String? {
        if (source == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Patch>() {

        }.type
        return gson.toJson(source, type)
    }

    @TypeConverter
    fun stringToPatch(source: String?): Patch? {
        if (source == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Patch>() {

        }.type
        return gson.fromJson(source, type)
    }
}


