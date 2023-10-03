package br.com.sp.demandas.core

fun Int.format(): String{
    return if(this in 0..9) "0$this" else this.toString()
}
