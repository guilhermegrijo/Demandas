package br.com.sp.demandas.core.app

import com.liftric.kvault.KVault

interface KeyValueStorage {
    fun getStore() : KVault
}