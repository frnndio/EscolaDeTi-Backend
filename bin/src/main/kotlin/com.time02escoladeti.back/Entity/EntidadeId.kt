package com.time02escoladeti.back.Entity

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.io.Serializable
import java.util.*
import javax.persistence.Embeddable
import javax.persistence.MappedSuperclass

@Embeddable
@JsonSerialize(using = EntityIdSerializer::class)
@MappedSuperclass
abstract class EntidadeId @JvmOverloads internal constructor(var valor: String = UUID.randomUUID().toString()) : Serializable {

    init {
        valor = UUID.fromString(valor).toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as EntidadeId

        if (valor != other.valor) return false

        return true
    }

    override fun hashCode(): Int {
        return valor.hashCode()
    }
}