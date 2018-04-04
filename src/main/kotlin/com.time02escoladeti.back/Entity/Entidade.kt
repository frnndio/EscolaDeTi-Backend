package com.time02escoladeti.back.Entity

import javax.persistence.AttributeOverride
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class Entidade<T : EntidadeId>(@EmbeddedId
                                        @AttributeOverride(name = "valor", column = Column(name = "id"))
                                        var id: T) {
    override fun toString() = id.valor
}