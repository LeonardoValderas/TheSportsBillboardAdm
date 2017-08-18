package com.valdroide.thesportsbillboardinstitution.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiClient
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.model.entities.Account
import com.valdroide.thesportsbillboardinstitution.model.response.DataResponse


import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import rx.lang.kotlin.toSingle
import rx.subscriptions.CompositeSubscription

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Observable.fromArray("Leo", "Juan", "Pedro")
                .subscribe { onNextName -> println("name:  $onNextName") }

        Observable.fromArray("Leo", "Juan", "Pedro")
                .filter { item -> item == "Leo" }
                .subscribe { onNextName -> println("name:  $onNextName") }

        Observable.fromArray("Leo", "Juan", "Pedro")
                .subscribeOn(Schedulers.newThread())
                .filter { item -> item == "Leo" }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onNextName -> text.text = onNextName }
        //getTextFromNetwork()

 }

    fun getTextFromNetwork() {
  /*
        var data: DataResponse? = null

        val task = Observable.create<DataResponse> { subscriber ->
            val client = ApiClient()
            val a = client.getApiService()
            try {
                subscriber.onNext(a.getDateData())
                subscriber.onComplete()
            } catch (e: Exception) {
                subscriber.onError(e)
            }
        }
        task.subscribeOn(Schedulers.newThread())
        task.observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<DataResponse> {
            override fun onError(e: Throwable) {
                println(e.message)
            }

            override fun onComplete() {
                println("complete")
            }

            override fun onNext(t: DataResponse) {

                println("onNext")
             with(t) {
                 wsResponse
                 account
                 dateData
             }
            }

            override fun onSubscribe(d: Disposable) {

            }


        })
        */
    }

    fun getTextFromNetworkOb() {
        var data: DataResponse? = null
        val client = ApiClient()
        val a = client.getApiService()
//        val task =  Observable.create<Observable<DataResponse>> { subscriber ->
//            try {
//                subscriber.onNext(a.getDateDataOb())
//                subscriber.onComplete()
//            } catch (e: Exception) {
//                subscriber.onError(e)
//            }
//        }
//       return a.getDateDataOb()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
        /*
        .subscribe(object : Observer<DataResponse> {
            override fun onError(e: Throwable) {
                println(e.message)
            }

            override fun onComplete() {
                println("complete")
            }

            override fun onNext(t: DataResponse) {



                    //  t.subscribe(println(t));
             //   println("onNext"+t)
//                with(t) {
//                    wsResponse
//                    account
//                    dateData
//                }
            }

            override fun onSubscribe(d: Disposable) {

            }


        })
    */
    }



}