package br.com.sp.demandas.core

import androidx.compose.ui.text.intl.Locale
import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle
import platform.Foundation.currentLocale

actual fun moneyFormat(number: Double): String? {
    var currencyFormatter = NSNumberFormatter()
    currencyFormatter.usesGroupingSeparator = true
    currencyFormatter.numberStyle = NSNumberFormatterCurrencyStyle
    currencyFormatter.locale = NSLocale.currentLocale()
    return currencyFormatter.stringFromNumber(NSNumber(number))
}