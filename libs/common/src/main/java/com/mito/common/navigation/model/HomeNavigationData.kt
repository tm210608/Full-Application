package com.mito.common.navigation.model

import com.mito.common.navigation.NavigationData

data class HomeNavigationData(
    override val value: Int,
    override val optional: Any? = null
) : NavigationData<Int, Any>