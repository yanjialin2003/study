package com.readboy.coroutine.activity

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.readboy.coroutine.api.model.User
import com.readboy.coroutine.api.userApi
import com.readboy.coroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.createCoroutine
import kotlin.coroutines.resume

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            tv.text = "Hello World"
            btn.setOnClickListener {
                // getUserByAsyncTask()
                // getUserByCoroutine()
                // getWithMainScopeCoroutine()
            }
        }
    }

    /**
     * 使用异步任务
     */
    @SuppressLint("StaticFieldLeak")
    private fun getUserByAsyncTask() {
        object : AsyncTask<Void, Void, User>() {
            // 这个是一个子线程中的请求操作
            override fun doInBackground(vararg params: Void?): User? {
                return userApi.getUser("XXX").execute().body()
            }
            // 这个是一个回调函数
            override fun onPostExecute(result: User?) {
                binding.tv.text = result?.address
            }
        }.execute()
    }

    /**
     * 使用协程
     */
    @OptIn(DelicateCoroutinesApi::class)
    private fun getUserByCoroutine() {
        // 父协程,让他调度到主线程 (Dispatchers -> 调度器)
        GlobalScope.launch(Dispatchers.Main) {
            // 子协程,执行到这里时,让他调度到IO线程
            val user = withContext(Dispatchers.IO) {
                userApi.getUserSuspend("XXX")
            }
            binding.tv.text = user.address
        }
    }

    /**
     * 原生协程Api
     */
    private fun startPrimaryCoroutine() {
        // 创建一个协程体
        val continuation = suspend { 5 }.createCoroutine(object : Continuation<Int> {
            override val context: CoroutineContext = EmptyCoroutineContext

            // 这里实际上还是一个回调函数
            override fun resumeWith(result: Result<Int>) {
                Log.d("TAG", "resumeWith: ${result.getOrNull()}")
            }
        })

        // 启动协程体
        continuation.resume(Unit)
    }

    /**
     * 在MainScope中使用
     */
    private fun getWithMainScopeCoroutine() {
        launch {
            // 这里retrofit会识别到是挂起函数，自动启动一个IO子协程
            val user = userApi.getUserSuspend("XXX")
            binding.tv.text = user.address
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel() // 取消协程
    }
}