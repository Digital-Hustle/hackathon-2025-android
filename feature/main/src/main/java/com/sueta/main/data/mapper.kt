package com.sueta.main.data

import com.sueta.core.mLog
import ru.dgis.sdk.directory.DirectoryObject

fun DirectoryObject.toStringAddress(): String {
    mLog("TITLE",this.address?.components?.toString() ?:"пустая хуйня")
    if (this.address?.components?.isEmpty() == true) return "_"
    val adressStreet = this.address?.components[0]?.asStreetAddress
    return adressStreet?.street + " " + adressStreet?.number
}