package com.datastore

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.LifecycleEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DataStoreModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext), CoroutineScope by CoroutineScope(Dispatchers.Main.immediate) {

  private val job = Job()
  private val dataStoreHelper = DataStoreHelper(reactContext.applicationContext)

  init {
    reactContext.addLifecycleEventListener(object : LifecycleEventListener {
      override fun onHostResume() {}

      override fun onHostPause() {}

      override fun onHostDestroy() {
        job.cancel()
      }
    })
  }

  override fun getName(): String {
    return NAME
  }

  @ReactMethod
  fun readDataStoreValue(key: String, promise: Promise) {
    launch {
      try {
        val value = dataStoreHelper.readDataStoreValue(key)
        promise.resolve(value)
      } catch (e: Exception) {
        e.printStackTrace()
        promise.reject("READ_DATASTORE_ERROR", "Error reading from DataStore: ${e.message}")
      }
    }
  }

  @ReactMethod
  fun writeDataStoreValue(key: String, value: String, promise: Promise) {
    launch {
      try {
        dataStoreHelper.writeDataStoreValue(key, value)
        promise.resolve("Value written to DataStore successfully.")
      } catch (e: Exception) {
        e.printStackTrace()
        promise.reject("WRITE_DATASTORE_ERROR", "Error writing to DataStore: ${e.message}")
      }
    }
  }

  @ReactMethod
  fun clearDataStoreValue(key: String, promise: Promise) {
    launch {
      try {
        val dataStoreHelper = DataStoreHelper(reactApplicationContext.applicationContext)
        dataStoreHelper.clearDataStoreValue(key)
        promise.resolve("DataStore value for key '$key' cleared successfully.")
      } catch (e: Exception) {
        e.printStackTrace()
        promise.reject("CLEAR_DATASTORE_ERROR", "Error clearing DataStore value: ${e.message}")
      }
    }
  }

  @ReactMethod
  fun clearDataStore(promise: Promise) {
    launch {
      try {
        val dataStoreHelper = DataStoreHelper(reactApplicationContext.applicationContext)
        dataStoreHelper.clearDataStore()
        promise.resolve("DataStore cleared successfully.")
      } catch (e: Exception) {
        e.printStackTrace()
        promise.reject("CLEAR_DATASTORE_ERROR", "Error clearing DataStore: ${e.message}")
      }
    }
  }

  companion object {
    const val NAME = "DataStore"
  }
}
