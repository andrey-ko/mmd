package org.andreyko.mmd.core

class EmptyIterator<T>: Iterator<T>{
    override fun hasNext(): Boolean {
        return false
    }

    override fun next(): T {
        throw NoSuchElementException()
    }
}