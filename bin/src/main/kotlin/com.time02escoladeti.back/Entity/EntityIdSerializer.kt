package com.time02escoladeti.back.Entity

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer

class EntityIdSerializer : StdSerializer<EntidadeId>(EntidadeId::class.java) {

    override fun serialize(entidadeId: EntidadeId?, jsonGenerator: JsonGenerator?, serializeProvider: SerializerProvider?) {
        jsonGenerator?.writeString(entidadeId?.valor)
    }
}