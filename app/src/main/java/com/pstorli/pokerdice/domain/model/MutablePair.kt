package com.pstorli.pokerdice.domain.model

class MutablePair<A, B>(var first: A, var second: B) {

    /**
     * return string value of pair
     */
    override fun toString(): String {
        return "MutablePair ($first, $second)"
    }
}