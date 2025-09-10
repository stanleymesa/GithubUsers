package com.stanleymesa.core.network

object RemoteEndPoint {
    const val ENDPOINT_OIL_LIST = "api/v1/oil/list"
    const val ENDPOINT_REGISTER = "api/v1/auth/register"
    const val ENDPOINT_LOGIN = "api/v1/auth/login"
    const val ENDPOINT_CHANGE_PASSWORD = "api/v1/auth/changePassword"
    const val ENDPOINT_FORGOT_PASSWORD = "api/v1/auth/forgotPasswordRequest"
    const val ENDPOINT_RECIPE = "api/v1/recipe"
    const val ENDPOINT_RESEND_EMAIL = "api/v1/verify/resend/email"
    const val ENDPOINT_MY_RECIPE_LIST = "api/v1/recipe/myList"
    const val ENDPOINT_OILS_BY_IDS = "api/v1/oil/listByIds"
    const val ENDPOINT_PUBLIC_RECIPE_LIST = "api/v1/recipe/publicList"
}