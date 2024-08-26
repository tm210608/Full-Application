package com.mito.common.navigation.model

import com.mito.common.navigation.NavigationData

data class LoginNavigationData(
    override val value: Any? = null,
    override val optional: String? = null
) : NavigationData<Any, String>
