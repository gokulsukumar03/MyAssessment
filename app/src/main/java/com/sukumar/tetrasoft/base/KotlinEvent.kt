package com.sukumar.tetrasoft.base
open class KotlinEvent {
    companion object {
        object LoadingEvent : KotlinEvent()
        object CompletedEvent : KotlinEvent()
        data class FailedEvent(val error: String) : KotlinEvent()
    }
}