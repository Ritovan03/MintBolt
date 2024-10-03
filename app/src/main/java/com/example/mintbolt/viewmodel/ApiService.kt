package com.example.mintbolt.viewmodel

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


data class ExpensesSummary(val summary: String)
data class PlotResponse(val employee_id: String, val plot_url: String)
data class EmployeeRequest(val employee_id: Int)

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("get_expenses_summary")
    suspend fun getExpensesSummary(@Body request: EmployeeRequest): ExpensesSummary

    @Headers("Content-Type: application/json")
    @POST("api/barplot")
    suspend fun getBarPlot(@Body request: EmployeeRequest): PlotResponse

    @Headers("Content-Type: application/json")
    @POST("api/piechart")
    suspend fun getPieChart(@Body request: EmployeeRequest): PlotResponse

    @Headers("Content-Type: application/json")
    @POST("api/heatmap")
    suspend fun getHeatmap(@Body request: EmployeeRequest): PlotResponse

    @Headers("Content-Type: application/json")
    @POST("api/ARIMA")
    suspend fun getARIMA(@Body request: EmployeeRequest): PlotResponse
}