package com.paypal.android.cardpayments.api

import com.paypal.android.cardpayments.CardRequest
import com.paypal.android.cardpayments.CardRequestFactory
import com.paypal.android.cardpayments.CardResponseParser
import com.paypal.android.corepayments.API

internal class CardAPI(
    private val api: API,
    private val requestFactory: CardRequestFactory = CardRequestFactory(),
    private val responseParser: CardResponseParser = CardResponseParser()
) {

    suspend fun fetchCachedOrRemoteClientID() {
        api.fetchCachedOrRemoteClientID()
    }

    suspend fun confirmPaymentSource(cardRequest: CardRequest): ConfirmPaymentSourceResponse {
        val apiRequest = requestFactory.createConfirmPaymentSourceRequest(cardRequest)
        val httpResponse = api.send(apiRequest)

        val error = responseParser.parseError(httpResponse)
        if (error != null) {
            throw error
        } else {
            return responseParser.parseConfirmPaymentSourceResponse(httpResponse)
        }
    }

    suspend fun getOrderInfo(getOrderRequest: GetOrderRequest): GetOrderInfoResponse {
        val apiRequest = requestFactory.createGetOrderInfoRequest(getOrderRequest)
        val httpResponse = api.send(apiRequest)

        val error = responseParser.parseError(httpResponse)
        if (error != null) {
            throw error
        } else {
            return responseParser.parseGetOrderInfoResponse(httpResponse)
        }
    }
}