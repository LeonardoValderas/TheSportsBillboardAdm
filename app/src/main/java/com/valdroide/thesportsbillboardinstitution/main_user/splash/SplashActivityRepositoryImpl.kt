package com.valdroide.thesportsbillboardinstitution.main_user.splash

import android.content.Context
import com.raizlabs.android.dbflow.sql.language.SQLite
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.main_user.splash.events.SplashActivityEvent
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.sql.language.Delete
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction
import com.valdroide.thesportsbillboardinstitution.model.entities.*
import com.valdroide.thesportsbillboardinstitution.model.response.DataResponse
import com.valdroide.thesportsbillboardinstitution.db.SportsDataBase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class SplashActivityRepositoryImpl(val eventBus: EventBus, val apiService: ApiService) : SplashActivityRepository {

    private var dateData: DateData? = null
    private var wsResponse: WSResponse? = null
    private var menus: List<MenuDrawer>? = null
    private var submenus: List<SubMenuDrawer>? = null
    private var account: Account? = null
    private val database = FlowManager.getDatabase(SportsDataBase::class.java)
    private var transaction: FastStoreModelTransaction<*>? = null
    private val compositeDisposable = CompositeDisposable()
    override fun getDate(context: Context) {
        try {
            dateData = SQLite.select().from(DateData::class.java).querySingle()
            post(SplashActivityEvent.DATEDATA, dateData)
        } catch (e: Exception) {
            post(SplashActivityEvent.ERROR, e.message)
        }
    }

    override fun getData(context: Context) {
        post(SplashActivityEvent.GOTONAV)
        /*
        if (Utils.isNetworkAvailable(context)) {
            try {
                post(SplashActivityEvent.GOTONAV)
                /*
                apiService.getDateData()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({result ->
                            wsResponse = result.wsResponse
                            dateData = result.dateData
                            menus = result.menu
                            submenus = result.submenu
                            account = result.account
                            post(SplashActivityEvent.GOTONAV)
                        }, {e -> post(SplashActivityEvent.ERROR, e.message)} )

*/
                /*
                compositeDisposable.add(
                        apiService.getDateData()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe({ result ->

                                    if (result != null) {
                                        wsResponse = result.wsResponse
                                        if (wsResponse != null) {
                                            if (wsResponse?.SUCCESS.equals("0")) {
                                                dateData = result.dateData
                                                account = result.account
                                                menus = result.menu
                                                submenus = result.submenu
                                                storedData()
                                            } else {
                                                post(SplashActivityEvent.ERROR, wsResponse!!.MESSAGE)
                                            }
                                        } else {
                                            post(SplashActivityEvent.ERROR, "La respuesta es nula. Intente nuevamente.")//review exception firebase
                                        }
                                    } else {
                                        post(SplashActivityEvent.ERROR, "La respuesta no fue recibida. Intente nuevamente.")//review exception firebase
                                    }
                                }, { error ->
                                    error.printStackTrace()
                                })
                )
*/

                /*
                val getDateData = apiService.getDateData()
                getDateData.enqueue(object : Callback<DataResponse> {
                    override fun onResponse(call: Call<DataResponse>?, response: Response<DataResponse>?) {
                        if (response != null && response.isSuccessful() ) {
                            wsResponse = response.body()?.wsResponse
                            if (wsResponse != null) {
                                if (wsResponse?.SUCCESS.equals("0")) {
                                    //DATEDATA
                                    dateData = response.body()?.dateData
                                    if (dateData != null) {
                                        Delete.table(DateData::class.java);
                                        dateData!!.save();
                                    }
                                    //MENUS
                                    menus = response.body()?.menu
                                    if (menus != null) {
                                        Delete.table(Menu::class.java)
                                        transaction = FastStoreModelTransaction
                                                .saveBuilder<Menu>(FlowManager.getModelAdapter<Menu>(Menu::class.java))
                                                .addAll(menus)
                                                .build()
                                        database.executeTransaction(transaction)
                                    }
                                    //SUBMENUS
                                    submenus = response.body()?.submenu
                                    if (submenus!= null) {
                                        Delete.table(SubMenu::class.java)
                                        transaction = FastStoreModelTransaction
                                                .saveBuilder<SubMenu>(FlowManager.getModelAdapter<SubMenu>(SubMenu::class.java))
                                                .addAll(submenus)
                                                .build()
                                        database.executeTransaction(transaction)
                                    }
                                    //ACCOUNT
                                    account = response.body()?.account
                                    if (account != null) {
                                        Delete.table(Account::class.java);
                                        account!!.save();
                                    }
                                    post(SplashActivityEvent.GOTONAV)
                                } else {
                                    post(SplashActivityEvent.ERROR, wsResponse!!.MESSAGE)
                                }
                            } else {
                                post(SplashActivityEvent.ERROR, "La respuesta es null.")
                            }
                        } else {
                            post(SplashActivityEvent.ERROR, "La respuesta no fue recibida.")//review exception firebase
                        }
                    }

                    override fun onFailure(call: Call<DataResponse>?, t: Throwable?) {
                        post(SplashActivityEvent.ERROR, t?.message)
                    }
                })


                */

            } catch (e: Exception) {
                post(SplashActivityEvent.ERROR, e.message)//exception firebase
            }

        } else {
            post(SplashActivityEvent.ERROR, "Verifique su conexión de Internet.")
        }
        */
    }

    fun loadData(): Observable<DataResponse>? {
        return apiService.getDateData()
                .flatMap { data -> Observable.just(data)}
    }



    private fun storedData() {
        //DATEDATA
        if (dateData != null) {
            Delete.table(DateData::class.java)
            dateData!!.save()
        }
        //MENUS
        if (menus != null) {
            Delete.table(MenuDrawer::class.java)
            transaction = FastStoreModelTransaction
                    .saveBuilder<MenuDrawer>(FlowManager.getModelAdapter<MenuDrawer>(MenuDrawer::class.java))
                    .addAll(menus)
                    .build()
            database.executeTransaction(transaction)
        }
        //SUBMENUS
        if (submenus != null) {
            Delete.table(SubMenuDrawer::class.java)
            transaction = FastStoreModelTransaction
                    .saveBuilder<SubMenuDrawer>(FlowManager.getModelAdapter<SubMenuDrawer>(SubMenuDrawer::class.java))
                    .addAll(submenus)
                    .build()
            database.executeTransaction(transaction)
        }
        //ACCOUNT
        if (account != null) {
            Delete.table(Account::class.java);
            //account!!.save();
        }
        post(SplashActivityEvent.GOTONAV)
    }


//            fun getDataObservable(): Observable<DataResponse> {
//                //fun getDataObservable() {
////     return apiService.getDateData()
////             .subscribeOn(Schedulers.io())
////             .map(???({ DataResponse.items() }))
////        .map { badges -> UserStats.create(user, badges) };
//                //val compositeDisposable = CompositeDisposable()
//                //compositeDisposable.add(
//                return apiService.getDateData()
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.io())
//
//                        .subscribe({ result ->
//
//                            wsResponse = result.wsResponse
//                            dateData = result.dateData
//                            menus = result.menu
//                            submenus = result.submenu
//
//                            //Log.d("Result", "There are ${result.wsResponse.MESSAGE} Java developers in Lagos")
//                        }, { error ->
//                            error.printStackTrace()
//                        })
//                // )
//            }

    override fun validateDate(context: Context, dateData: DateData) {
        try {

        } catch (e: Exception) {

        }
    }

    override fun getLogin(context: Context) {
        try {

        } catch (e: Exception) {

        }
    }

    override fun validateLogin(context: Context, login: Login) {
        try {

        } catch (e: Exception) {

        }
    }

    override fun sendEmail(context: Context, comment: String) {
        try {

        } catch (e: Exception) {

        }
    }

    fun post(types: Int) {
        post(types, null, null)
    }

    fun post(types: Int, error: String?) {
        post(types, null, error)
    }

    fun post(types: Int, dateData: DateData?) {
        post(types, dateData, null)
    }

    fun post(types: Int, dateData: DateData?, error: String?) {
        val event = SplashActivityEvent()
        event.type = types
        event.error = error
        event.dateData = dateData
        eventBus.post(event)
    }
}