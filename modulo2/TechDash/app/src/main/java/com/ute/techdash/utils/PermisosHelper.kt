package com.ute.techdash.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object PermisosHelper {

    fun tienePermiso(context: Context, permiso: String): Boolean =
        ContextCompat.checkSelfPermission(context, permiso) ==
                PackageManager.PERMISSION_GRANTED

    fun tieneTodosLosPermisos(context: Context, permisos: Array<String>): Boolean =
        permisos.all { tienePermiso(context, it) }

    fun permisosNecesarios(
        context:  Context,
        permisos: Array<String>
    ): Array<String> = permisos.filter { !tienePermiso(context, it) }.toTypedArray()
}