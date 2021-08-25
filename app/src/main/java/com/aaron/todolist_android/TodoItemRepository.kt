package com.aaron.todolist_android

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aaron.todolist_android.database.AppDatabase
import com.aaron.todolist_android.database.TodoItem
import com.aaron.todolist_android.network.ApiClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.*

class TodoItemRepository(application: Application) {
    private val database = AppDatabase.getInstance(application)
    private val apiClient = ApiClient()
    //TODO 判斷有沒有登入 做資料同步
    private val context = application
    private var account:GoogleSignInAccount?= null
    var liveData = MutableLiveData<List<TodoItem>>()
    var recyLiveData = MutableLiveData<List<TodoItem>>()

    enum class API_FUNC{
        INSERT,UPDATE,DELETE
    }

    suspend fun insertTodoItem(todoItem: TodoItem){
        database.todoItemDao().insert(todoItem)
        processingData2(API_FUNC.INSERT,todoItem)
    }

    suspend fun updateTodoItem(todoItem: TodoItem){
        database.todoItemDao().update(todoItem)
        processingData2(API_FUNC.UPDATE,todoItem)
        processingData4()
    }

    suspend fun deleteTodoItem(todoItem: TodoItem){
        database.todoItemDao().delete(todoItem)
        processingData2(API_FUNC.DELETE,todoItem)
        processingData4()
    }
    private suspend fun processingData2(type:API_FUNC,todoItem: TodoItem){
        account = GoogleSignIn.getLastSignedInAccount(context)
        if(account!=null){
            var id = account!!.id
            when(type){
                API_FUNC.INSERT -> {apiClient.insert(id, listOf(todoItem))}
                API_FUNC.UPDATE -> {apiClient.update(id,todoItem)}
                API_FUNC.DELETE -> {apiClient.delete(id,todoItem)}
            }
        }
        liveData.value = database.todoItemDao().findAll()
    }

    private suspend fun processingData4(){
        recyLiveData.value = database.todoItemDao().findAllRecycled()
    }

    suspend fun processingData3(){
        account = GoogleSignIn.getLastSignedInAccount(context)
        val localData = database.todoItemDao().findAllRecycled()
        return if(account!=null){
            val remoteData = apiClient.findAllRecycled(account!!.id)
            Log.d("RDataddd",remoteData.toString())
            //asyncing....
            var processedData = asyncData(localData,remoteData)
            Log.d("RDataddd",processedData.toString())
            processedData = asyncLocalData(processedData,localData)
            processedData.sortByDescending { it.createAt }
            asyncRemoteData(processedData,remoteData)
            recyLiveData.value = processedData
        }else
            recyLiveData.value = localData
    }

    suspend fun processingData(){
        account = GoogleSignIn.getLastSignedInAccount(context)
        val localData = database.todoItemDao().findAll()
        return if(account!=null){
            val remoteData = apiClient.findAll(account!!.id)
            Log.d("Dataddd",remoteData.toString())
            //asyncing....
            var processedData = asyncData(localData,remoteData)
            Log.d("Dataddd",processedData.toString())
            processedData = asyncLocalData(processedData,localData)
            processedData.sortByDescending { it.createAt }
            asyncRemoteData(processedData,remoteData)
            liveData.value = processedData
        }else
            liveData.value = localData
    }

    private fun asyncData(localData:List<TodoItem>,remoteData:List<TodoItem>):MutableList<TodoItem>{
        var set = HashSet<String>()
        var tmp = HashSet<String>()
        var ans = mutableListOf<TodoItem>()
        for(item in localData){
            set.add(item.title)
            tmp.add(item.title)
        }
        for(item in remoteData){
            set.add(item.title)
            tmp.add(item.title)
        }
        for(title in set){
            for(x in localData){
                if(title==x.title) {
                    ans.add(x)
                    tmp.remove(title)
                }
            }
        }
        for(title in tmp){
            for(y in remoteData){
                if(title==y.title)
                    ans.add(y)
            }
        }
        return ans
    }

    suspend fun getTodoItems():LiveData<List<TodoItem>>{
        processingData()
        return liveData
    }

    private suspend fun asyncLocalData(processedData: MutableList<TodoItem>,localData: List<TodoItem>):MutableList<TodoItem>{
        var ans = mutableListOf<TodoItem>()
        var flag = false
        for(item in processedData){
            flag = false
            for(x in localData){
                if(item.title == x.title) {
                    flag = true
                    break
                }
            }
            if(!flag)
                ans.add(item)
        }
        for(item in ans){
            try {
                val pItem = TodoItem(item.title,item.done,item.recycled,item.createAt)
                database.todoItemDao().insert(pItem)
            }catch (e:Exception){
                Log.d("db_error",e.toString())
                processedData.remove(item)
            }
        }
        return processedData
    }

    private suspend fun asyncRemoteData(processedData: List<TodoItem>,remoteData: List<TodoItem>){
        var ans = mutableListOf<TodoItem>()
        var flag = false
        for(item in processedData){
            flag = false
            for(x in remoteData){
                if(item.title == x.title) {
                    flag = true
                    break
                }
            }
            if(!flag)
                ans.add(item)
        }
        apiClient.insert(account!!.id,ans)
    }

    suspend fun getRecycledTodoItems(): LiveData<List<TodoItem>>{
        processingData3()
        return recyLiveData
    }

    fun getCheckNum(): LiveData<Int>{
        return database.todoItemDao().findAllCheckNum()
    }

     suspend fun login(id: String): String{
        return apiClient.login(id)
    }
}