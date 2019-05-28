package com.kvelasco.domain.common

interface Mapper<TO, FROM> {
    fun from(entity: FROM): TO
}