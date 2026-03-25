package com.example.cupcake.ui

import androidx.lifecycle.ViewModel
import com.example.cupcake.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/** Price for a single cupcake */
private const val PRICE_PER_CUPCAKE = 2.00

/** Additional cost for same day pickup of an order */
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

/**
 * [OrderViewModel] holds information about a cupcake order in terms of quantity, flavor, and
 * pickup date. It also knows how to calculate the total price based on these order details.
 */
class OrderViewModel : ViewModel() {

    // Tạo danh sách ngày một lần duy nhất để dùng chung
    private val dateOptions = pickupOptions()

    private val _uiState = MutableStateFlow(
        OrderUiState(
            pickupOptions = dateOptions,
            // Đảm bảo ngày mặc định được chọn là ngày đầu tiên để tránh lỗi rỗng (null/empty)
            date = dateOptions[0]
        )
    )
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    fun setQuantity(numberCupcakes: Int) {
        _uiState.update { currentState ->
            val newPrice = calculatePrice(quantity = numberCupcakes)
            currentState.copy(
                quantity = numberCupcakes,
                price = newPrice
            )
        }
    }

    fun setFlavor(desiredFlavor: String) {
        _uiState.update { currentState ->
            currentState.copy(flavor = desiredFlavor)
        }
    }

    fun setDate(pickupDate: String) {
        _uiState.update { currentState ->
            val newPrice = calculatePrice(pickupDate = pickupDate)
            currentState.copy(
                date = pickupDate,
                price = newPrice
            )
        }
    }

    fun resetOrder() {
        _uiState.value = OrderUiState(
            pickupOptions = dateOptions,
            date = dateOptions[0]
        )
    }

    private fun calculatePrice(
        quantity: Int = _uiState.value.quantity,
        pickupDate: String = _uiState.value.date
    ): String {
        // Nếu số lượng là 0 thì giá là 0 (tránh trường hợp chưa chọn gì đã tính phí ship)
        if (quantity <= 0) return NumberFormat.getCurrencyInstance().format(0.0)

        var calculatedPrice = quantity * PRICE_PER_CUPCAKE

        // Kiểm tra phí ship cùng ngày (so sánh với phần tử đầu tiên trong list ngày)
        if (dateOptions[0] == pickupDate) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }

        return NumberFormat.getCurrencyInstance().format(calculatedPrice)
    }

    private fun pickupOptions(): List<String> {
        val dateOptions = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(4) {
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return dateOptions
    }
}
