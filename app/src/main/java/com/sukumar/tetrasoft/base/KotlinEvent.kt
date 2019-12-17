package com.sukumar.tetrasoft.base

open class KotlinEvent {
    /**
     * Generic Loading KotlinEvent
     */
    companion object {
        object LoadingEvent : KotlinEvent()
        /**
         * Generic Success KotlinEvent
         */

        object CompletedEvent : KotlinEvent()

        object InternetError : KotlinEvent()

        object WentWrongEvent : KotlinEvent()


        /**
         * Generic Failed KotlinEvent
         */
        data class FailedEvent(val error: String) : KotlinEvent()
    }
}