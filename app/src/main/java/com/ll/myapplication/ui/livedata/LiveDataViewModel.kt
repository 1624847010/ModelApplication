package com.ll.myapplication.ui.livedata

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @Author: ll
 * @CreateTime: 2022/03/04 15:13
 */
class LiveDataViewModel(application: Application, val i: Int) : AndroidViewModel(application) {
    private val data = liveData {
        LogUtils.d("data")
        emit(i.toString())
    }

    // Transformations.switchMap 等价于 data.switchMap
    //Transformations.switchMap(data) {
    //        liveData {
    //            LogUtils.d("switchData")
    //            emit(it)
    //        }
    //    }
    private val switchData = data.switchMap {
        liveData {
            LogUtils.d("switchData")
            emit(it)
        }
    }

    private val mapData = Transformations.map(switchData) {
        LogUtils.d("mapData")
        it.toIntOrNull() ?: 0
    }

    /**
     * 可以观察其他 LiveData 对象并对来自它们的 OnChanged 事件作出反应的子类。
     */
    val mediatorData = MediatorLiveData<String>()

    fun getMediator() = viewModelScope.launch {

        LogUtils.d("getMediator")

        mediatorData.value = "0.0"

        delay(6000)

        mediatorData.addSource(mapData) {
            mediatorData.value = it.plus(1).toString()
        }

        Toast.makeText(getApplication(),"失败乃成功之母",Toast.LENGTH_LONG).show()
    }
}

class LiveDataFactory(private val application: Application, private val i: Int) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LiveDataViewModel(application,i) as T
    }
}