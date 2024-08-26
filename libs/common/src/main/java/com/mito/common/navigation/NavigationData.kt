package com.mito.common.navigation

import java.io.Serializable

interface NavigationData<V, O> : Serializable {
    val value: V?
    val optional: O?
}