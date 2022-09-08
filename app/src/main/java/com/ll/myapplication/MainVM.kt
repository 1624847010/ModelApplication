package com.ll.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.*

/**
 * @Author: ll
 * @CreateTime: 2022/05/25 09:21
 * 状态（State）用 StateFlow ；事件（Event）用 SharedFlow
 */
class MainVM : ViewModel() {

}