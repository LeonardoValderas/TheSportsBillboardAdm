package com.valdroide.thesportsbillboardinstitution.utils.helper

import java.text.SimpleDateFormat
import java.util.*

object DateTimeHelper {

    fun getFechaOficial(): String {
        val dateOficial = Date()
        val sdf = SimpleDateFormat("yyyyMMddHHmmss")
        return sdf.format(dateOficial)
    }

    fun getFechaOficialSeparate(): String {
        val dateOficial = Date()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(dateOficial)
    }
}