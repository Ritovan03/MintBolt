package com.example.mintbolt.viewmodel

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST







class ExpensesViewModel : ViewModel() {
    private val _expensesSummary = MutableStateFlow<String?>(null)
    val expensesSummary: StateFlow<String?> = _expensesSummary

    private val _barPlot = MutableStateFlow<String?>(null)
    val barPlot: StateFlow<String?> = _barPlot

    private val _pieChart = MutableStateFlow<String?>(null)
    val pieChart: StateFlow<String?> = _pieChart

    private val _heatmap = MutableStateFlow<String?>(null)
    val heatmap: StateFlow<String?> = _heatmap

    private val _arima = MutableStateFlow<String?>(null)
    val arima: StateFlow<String?> = _arima

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchExpensesSummary(employeeId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getExpensesSummary(EmployeeRequest(employeeId))
                _expensesSummary.value = response.summary
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error fetching expenses summary: ${e.message}"
            }
        }
    }

    fun fetchBarPlot(employeeId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getBarPlot(EmployeeRequest(employeeId))
                _barPlot.value = response.plot_url
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error fetching bar plot: ${e.message}"
            }
        }
    }

    fun fetchPieChart(employeeId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getPieChart(EmployeeRequest(employeeId))
                _pieChart.value = response.plot_url
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error fetching pie chart: ${e.message}"
            }
        }
    }

    fun fetchHeatmap(employeeId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getHeatmap(EmployeeRequest(employeeId))
                _heatmap.value = response.plot_url
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error fetching heatmap: ${e.message}"
            }
        }
    }

    fun fetchARIMA(employeeId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getARIMA(EmployeeRequest(employeeId))
                _arima.value = response.plot_url
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error fetching ARIMA: ${e.message}"
            }
        }
    }






}




