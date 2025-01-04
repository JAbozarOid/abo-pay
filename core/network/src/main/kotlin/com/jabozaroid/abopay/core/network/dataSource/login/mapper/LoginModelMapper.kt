package com.jabozaroid.abopay.core.network.dataSource.login.mapper

import com.jabozaroid.abopay.core.domain.model.auth.LoginOTPResult
import com.jabozaroid.abopay.core.domain.model.auth.LoginOtpParam
import com.jabozaroid.abopay.core.domain.model.auth.VerifyLoginOtpParam
import com.jabozaroid.abopay.core.domain.model.auth.VerifyLoginOtpResult
import com.jabozaroid.abopay.core.network.model.auth.LoginOtpNetworkParam
import com.jabozaroid.abopay.core.network.model.auth.LoginOtpNetworkResult
import com.jabozaroid.abopay.core.network.model.auth.VerifyLoginOtpNetworkParam
import com.jabozaroid.abopay.core.network.model.auth.VerifyLoginOtpNetworkResult

fun LoginOtpNetworkResult.mapToDomainModel(): LoginOTPResult {
    return LoginOTPResult(
        result = this.result,
    )
}

fun LoginOtpParam.mapToNetworkModel(): LoginOtpNetworkParam {
    return LoginOtpNetworkParam(
        Mobile = this.mobile ,
        NationalCode = this.nationalCode
    )
}

fun VerifyLoginOtpNetworkResult.mapToDomainModel(): VerifyLoginOtpResult {
    return VerifyLoginOtpResult(
        accessToken = access_token
    )
}

fun VerifyLoginOtpParam.mapToNetworkModel(): VerifyLoginOtpNetworkParam {
    return VerifyLoginOtpNetworkParam(
        Code = this.Code,
        UniqueTraceNumber = this.UniqueTraceNumber,
        Mobile = this.Mobile
    )
}