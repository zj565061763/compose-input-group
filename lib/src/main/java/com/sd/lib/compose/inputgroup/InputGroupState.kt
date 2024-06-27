package com.sd.lib.compose.inputgroup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun rememberInputGroupState(
    count: Int,
): InputGroupState {
    require(count > 0)
    return remember(count) {
        InputGroupState(count = count)
    }
}

class InputGroupState internal constructor(
    val count: Int,
) {
    private var _value by mutableStateOf("")

    /** 输入的值 */
    val value: String
        get() = _value

    internal fun getIndexValue(index: Int): String {
        val item = value.elementAtOrNull(index) ?: ""
        return item.toString()
    }

    internal fun notifyValue(value: String) {
        val oldValue = _value
        val newValue = value.takeIf { it.length <= count } ?: value.take(count)
        val changed = newValue.length - oldValue.length

        // 剩余未填充数量
        val leftCount = (count - oldValue.length).coerceAtLeast(0)
        when (leftCount) {
            0 -> {
                if (changed < 0) {
                    _value = newValue
                }
            }
            else -> {
                _value = newValue
            }
        }
    }
}