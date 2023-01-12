package app.vazovsky.healsted.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/** Конвертация в Map<String, Any> из Объекта */
fun <T> T.serializeToMap(): Map<String, Any> {
    return convert()
}

/** Конвертация в Объект из Map<String, Any> */
inline fun <reified T> Map<String, Any>.toDataClass(): T {
    return convert()
}

/** Конвертация объект из типа I в тип O */
inline fun <I, reified O> I.convert(): O {
    val gson = Gson()
    val json = gson.toJson(this)
    return gson.fromJson(json, object : TypeToken<O>() {}.type)
}