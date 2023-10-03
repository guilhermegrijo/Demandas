package br.com.sp.demandas.core

import android.icu.text.NumberFormat
import java.util.Locale

actual fun moneyFormat(number: Double): String? {

    val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    format.maximumFractionDigits = 2
    return format.format(number)

}