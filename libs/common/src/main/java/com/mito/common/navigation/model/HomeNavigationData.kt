package com.mito.common.navigation.model

import com.mito.common.navigation.NavigationData

data class HomeNavigationData(
    override val value: String?,
    override val optional: Any? = null
) : NavigationData<String, Any>