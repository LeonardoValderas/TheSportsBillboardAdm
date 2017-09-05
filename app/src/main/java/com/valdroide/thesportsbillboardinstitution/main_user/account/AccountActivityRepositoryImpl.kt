package com.valdroide.thesportsbillboardinstitution.main_user.account

import android.content.Context
import com.google.firebase.crash.FirebaseCrash
import com.valdroide.thesportsbillboardinstitution.R
import com.valdroide.thesportsbillboardinstitution.api.ApiService
import com.valdroide.thesportsbillboardinstitution.lib.base.EventBus
import com.valdroide.thesportsbillboardinstitution.lib.base.SchedulersInterface
import com.valdroide.thesportsbillboardinstitution.main_user.account.events.AccountActivityEvent
import com.valdroide.thesportsbillboardinstitution.model.entities.Account
import com.valdroide.thesportsbillboardinstitution.model.entities.WSResponse
import com.valdroide.thesportsbillboardinstitution.model.response.AccountResponse


class AccountActivityRepositoryImpl(val eventBus: EventBus, val apiService: ApiService, val scheduler: SchedulersInterface) : AccountActivityRepository {
    private var account: Account? = null
    private var response: WSResponse? = null
    private var accountResponse: AccountResponse? = null

//    override fun getAccountFromApi(): Observable<AccountResponse> =
//        apiService.getAccount()
//                .flatMap  { result -> Observable.fromArray(result)}
//

    override fun getAccount(context: Context) {
        //validate internet state connection
        try {
            apiService.getAccount()
                    .subscribeOn(scheduler.schedulerIO())
                    .observeOn(scheduler.schedulerMainThreader())
                    .subscribe({ result ->
                        accountResponse = result
                        if (accountResponse != null) {
                            response = accountResponse!!.wsResponse
                            if (response != null) {
                                if (response?.SUCCESS.equals("0")) {
                                    account = accountResponse!!.account
                                    post(AccountActivityEvent.ACCOUNT, account)
                                } else {
                                    post(AccountActivityEvent.ERROR, response?.MESSAGE!!)
                                }
                            } else {
                                post(AccountActivityEvent.ERROR, context.getString(R.string.null_response))
                            }
                        } else {
                            post(AccountActivityEvent.ERROR, context.getString(R.string.null_process))
                        }
                    }, { e ->
                        post(AccountActivityEvent.ERROR, e.message)
                        FirebaseCrash.report(e)
                    })

        } catch (e: Exception) {
            FirebaseCrash.report(e)
            post(AccountActivityEvent.ERROR, e.message)
        }
    }


    /*



                if (accountResponse != null) {
                    response = accountResponse?.wsResponse
                    if (response != null) {
                        if (response?.SUCCESS.equals("0")) {
                            account = accountResponse?.account
                            post(AccountActivityEvent.ACCOUNT, account)
                        } else {
                            post(AccountActivityEvent.ERROR, response?.MESSAGE!!)
                        }
                    } else {
                        post(AccountActivityEvent.ERROR, context.getString(R.string.null_response))
                    }
                } else {
                    post(AccountActivityEvent.ERROR, context.getString(R.string.null_process))
                }


            } catch (e: Exception) {
                FirebaseCrash.report(e)
                post(AccountActivityEvent.ERROR, e.message)
            }
        }

    */
    /*
          override fun getAccount(context: Context) {
              try {
                  apiService.getAccount()
                          .subscribeOn(Schedulers.io())
                          .observeOn(AndroidSchedulers.mainThread())
                          .subscribe({ result ->
                              if (result != null) {
                                  account = result.account
                                  if(account != null)
                                  post(AccountActivityEvent.ACCOUNT, account)

                              } else {
                                  post(AccountActivityEvent.ERROR, response?.MESSAGE!!)
                              }
                          } else {
                              post(AccountActivityEvent.ERROR, context.getString(R.string.null_response))
                          }
              } else {
                  post(AccountActivityEvent.ERROR, context.getString(R.string.null_process))
              }

              post(AccountActivityEvent.ERROR, context.getString(R.string.null_process))
          },
          {
              e ->
              post(AccountActivityEvent.ERROR, e.message)
              FirebaseCrash.report(e)
          })
      } catch (e: Exception) {
          FirebaseCrash.report(e)
          post(AccountActivityEvent.ERROR, e.message)
      }
      }
      */
    fun post(types: Int, error: String?) {
        post(types, null, error)
    }

    fun post(types: Int, account: Account?) {
        post(types, account, null)
    }

    fun post(types: Int, account: Account?, error: String?) {
        val event = AccountActivityEvent()
        event.type = types
        event.account = account
        event.error = error
        eventBus.post(event)
    }
}