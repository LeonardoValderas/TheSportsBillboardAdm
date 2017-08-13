package com.valdroide.thesportsbillboardinstitution.lib.base


interface EventBus{
    fun register(subscriber : Any);
    fun unregister(subscriber : Any);
    fun post(event : Any);
}
