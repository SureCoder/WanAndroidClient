package top.jowanxu.wanandroidclient.model

import Constant
import asyncRequestSuspend
import cancelAndJoinByActive
import cancelCall
import collectArticleCall
import getFriendListCall
import getHomeListCall
import getHotListCall
import getTypeTreeListCall
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import loginWanAndroid
import registerWanAndroid
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import top.jowanxu.wanandroidclient.bean.*
import top.jowanxu.wanandroidclient.presenter.HomePresenter

class HomeModelImpl : HomeModel, CollectArticleModel {

    /**
     * Home list Call
     */
    private var homeListCall: Call<HomeListResponse>? = null
    /**
     * Home list async
     */
    private var homeListAsync: Deferred<Any>? = null
    /**
     * TypeTree list Call
     */
    private var typeTreeListCall: Call<TreeListResponse>? = null
    /**
     * TypeTree async
     */
    private var typeTreeListAsync: Deferred<Any>? = null
    /**
     * Hot list Call
     */
    private var hotListCall: Call<HotKeyResponse>? = null
    /**
     * Hot async
     */
    private var hotListAsync: Deferred<Any>? = null
    /**
     * Login Call
     */
    private var loginCall: Call<LoginResponse>? = null
    /**
     * Login async
     */
    private var loginAsync: Deferred<Any>? = null
    /**
     * Register Call
     */
    private var registerCall: Call<LoginResponse>? = null
    /**
     * Register async
     */
    private var registerAsync: Deferred<Any>? = null
    /**
     * Friend list Call
     */
    private var friendListCall: Call<FriendListResponse>? = null
    /**
     * Friend list async
     */
    private var friendListAsync: Deferred<Any>? = null
    /**
     * Collect Article Call
     */
    private var collectArticleCall: Call<HomeListResponse>? = null
    /**
     * Collect Article async
     */
    private var collectArticleAsync: Deferred<Any>? = null

    /**
     * get Home List
     * @param onHomeListListener HomePresenter.OnHomeListListener
     * @param page page
     */
    override fun getHomeList(onHomeListListener: HomePresenter.OnHomeListListener, page: Int) {
        async(UI) {
            try {
                homeListAsync?.cancelAndJoinByActive()
                homeListAsync = async(CommonPool) {
                    // Async Request, wait resume
                    asyncRequestSuspend<HomeListResponse> { cont ->
                        homeListCall?.cancelCall()
                        getHomeListCall(page).apply {
                            homeListCall = this@apply
                        }.enqueue(object : Callback<HomeListResponse> {
                            override fun onResponse(call: Call<HomeListResponse>,
                                                    response: Response<HomeListResponse>) {
                                // resume
                                cont.resume(response.body())
                            }

                            override fun onFailure(call: Call<HomeListResponse>, t: Throwable) {
                                cont.resumeWithException(t)
                            }
                        })
                    }
                }
                // Get async result
                val result = homeListAsync?.await()
                when (result) {
                    is String -> onHomeListListener.getHomeListFailed(result)
                    is HomeListResponse -> onHomeListListener.getHomeListSuccess(result)
                    else -> onHomeListListener.getHomeListFailed(Constant.RESULT_NULL)
                }
            } catch (t: Throwable) {
                t.printStackTrace()
                // Return Throwable toString
                onHomeListListener.getHomeListFailed(t.toString())
                return@async
            }
        }
    }
    /**
     * cancel HomeList Request
     */
    override fun cancelHomeListRequest() {
        homeListCall?.cancelCall()
        homeListAsync?.cancel()
    }

    /**
     * get TypeTree List
     * @param onTypeTreeListListener HomePresenter.OnTypeTreeListListener
     */
    override fun getTypeTreeList(onTypeTreeListListener: HomePresenter.OnTypeTreeListListener) {
        async(UI) {
            try {
                typeTreeListAsync?.cancelAndJoinByActive()
                typeTreeListAsync = async(CommonPool) {
                    asyncRequestSuspend<TreeListResponse> { cont ->
                        typeTreeListCall?.cancelCall()
                        getTypeTreeListCall().apply {
                            typeTreeListCall = this@apply
                        }.enqueue(object : Callback<TreeListResponse> {
                            override fun onResponse(call: Call<TreeListResponse>?, response: Response<TreeListResponse>) {
                                cont.resume(response.body())
                            }
                            override fun onFailure(call: Call<TreeListResponse>?, t: Throwable) {
                                cont.resumeWithException(t)
                            }
                        })
                    }
                }
                val result = typeTreeListAsync?.await()
                when (result) {
                    is String -> onTypeTreeListListener.getTypeTreeListFailed(result)
                    is TreeListResponse -> onTypeTreeListListener.getTypeTreeListSuccess(result)
                    else -> onTypeTreeListListener.getTypeTreeListFailed(Constant.RESULT_NULL)
                }
            } catch (t: Throwable) {
                t.printStackTrace()
                onTypeTreeListListener.getTypeTreeListFailed(t.toString())
                return@async
            }
        }
    }
    /**
     * cancel TypeTree Request
     */
    override fun cancelTypeTreeRequest() {
        loginCall?.cancelCall()
        loginAsync?.cancel()
    }

    /**
     * login
     * @param onLoginListener HomePresenter.OnLoginListener
     * @param username username
     * @param password password
     */
    override fun loginWanAndroid(onLoginListener: HomePresenter.OnLoginListener,
                                 username: String, password: String) {
        async(UI) {
            try {
                loginAsync?.cancelAndJoinByActive()
                loginAsync = async(CommonPool) {
                    // Async Request, wait resume
                    asyncRequestSuspend<LoginResponse> { cont ->
                        loginCall?.cancelCall()
                        loginWanAndroid(username, password).apply {
                            loginCall = this@apply
                        }.enqueue(object : Callback<LoginResponse> {
                            override fun onResponse(call: Call<LoginResponse>,
                                                    response: Response<LoginResponse>) {
                                // resume
                                cont.resume(response.body())
                            }

                            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                cont.resumeWithException(t)
                            }
                        })
                    }
                }
                // Get async result
                val result = loginAsync?.await()
                when (result) {
                    is String -> onLoginListener.loginFailed(result)
                    is LoginResponse -> onLoginListener.loginSuccess(result)
                    else -> onLoginListener.loginFailed(Constant.RESULT_NULL)
                }
            } catch (t: Throwable) {
                t.printStackTrace()
                onLoginListener.loginFailed(t.toString())
                return@async
            }
        }
    }
    /**
     * cancel login Request
     */
    override fun cancelLoginRequest() {
        registerCall?.cancelCall()
        registerAsync?.cancel()
    }

    /**
     * register
     * @param onRegisterListener HomePresenter.OnRegisterListener
     * @param username username
     * @param password password
     * @param repassword repassword
     */
    override fun registerWanAndroid(onRegisterListener: HomePresenter.OnRegisterListener,
                                    username: String, password: String, repassword: String) {
        async(UI) {
            try {
                registerAsync?.cancelAndJoinByActive()
                registerAsync = async(CommonPool) {
                    // Async Request, wait resume
                    asyncRequestSuspend<LoginResponse> { cont ->
                        registerCall?.cancelCall()
                        registerWanAndroid(username, password, repassword).apply {
                            registerCall = this@apply
                        }.enqueue(object : Callback<LoginResponse> {
                            override fun onResponse(call: Call<LoginResponse>,
                                                    response: Response<LoginResponse>) {
                                // resume
                                cont.resume(response.body())
                            }

                            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                cont.resumeWithException(t)
                            }
                        })
                    }
                }
                // Get async result
                val result = registerAsync?.await()
                when (result) {
                    is String -> onRegisterListener.registerFailed(result)
                    is LoginResponse -> onRegisterListener.registerSuccess(result)
                    else -> onRegisterListener.registerFailed(Constant.RESULT_NULL)
                }
            } catch (t: Throwable) {
                t.printStackTrace()
                onRegisterListener.registerFailed(t.toString())
                return@async
            }
        }
    }
    /**
     * cancel register Request
     */
    override fun cancelRegisterRequest() {
        typeTreeListCall?.cancelCall()
        typeTreeListAsync?.cancel()
    }

    /**
     * get friend list
     */
    override fun getFriendList(onFriendListListener: HomePresenter.OnFriendListListener) {
        async(UI) {
            try {
                friendListAsync?.cancelAndJoinByActive()
                friendListAsync = async(CommonPool) {
                    asyncRequestSuspend<FriendListResponse> { cont ->
                        friendListCall?.cancelCall()
                        getFriendListCall().apply {
                            friendListCall = this@apply
                        }.enqueue(object : Callback<FriendListResponse> {
                            override fun onResponse(call: Call<FriendListResponse>,
                                                    response: Response<FriendListResponse>) {
                                cont.resume(response.body())
                            }

                            override fun onFailure(call: Call<FriendListResponse>, t: Throwable) {
                                cont.resumeWithException(t)
                            }
                        })
                    }
                }
                val result = friendListAsync?.await()
                if (result !is FriendListResponse) {
                    if (result is String) {
                        onFriendListListener.getFriendListFailed(result)
                        return@async
                    } else {
                        onFriendListListener.getFriendListFailed(Constant.RESULT_NULL)
                        return@async
                    }
                }
                hotListAsync?.cancelAndJoinByActive()
                hotListAsync = async(CommonPool) {
                    asyncRequestSuspend<HotKeyResponse> { cont ->
                        hotListCall?.cancelCall()
                        getHotListCall().apply {
                            hotListCall = this@apply
                        }.enqueue(object : Callback<HotKeyResponse> {
                            override fun onResponse(call: Call<HotKeyResponse>,
                                                    response: Response<HotKeyResponse>) {
                                cont.resume(response.body())
                            }

                            override fun onFailure(call: Call<HotKeyResponse>, t: Throwable) {
                                cont.resumeWithException(t)
                            }
                        })
                    }
                }
                val hotResult = hotListAsync?.await()
                when (hotResult) {
                    is String -> onFriendListListener.getFriendListFailed(hotResult)
                    is HotKeyResponse -> onFriendListListener.getFriendListSuccess(result, hotResult)
                    else -> onFriendListListener.getFriendListFailed(Constant.RESULT_NULL)
                }

            } catch (t: Throwable) {
                t.printStackTrace()
                onFriendListListener.getFriendListFailed(t.toString())
                return@async
            }
        }
    }

    /**
     * cancel friend list Request
     */
    override fun cancelFriendRequest() {
        friendListCall?.cancelCall()
        friendListAsync?.cancel()
        hotListCall?.cancelCall()
        hotListAsync?.cancel()
    }

    /**
     * add or remove collect article
     */
    override fun collectArticle(onCollectArticleListener: HomePresenter.OnCollectArticleListener, id: Int, isAdd: Boolean) {
        async(UI) {
            try {
                collectArticleAsync?.cancelAndJoinByActive()
                collectArticleAsync = async(CommonPool) {
                    asyncRequestSuspend<HomeListResponse> { cont ->
                        collectArticleCall?.cancelCall()
                        collectArticleCall(id, isAdd).apply {
                            collectArticleCall = this@apply
                        }.enqueue(object : Callback<HomeListResponse> {
                            override fun onResponse(call: Call<HomeListResponse>,
                                                    response: Response<HomeListResponse>) {
                                cont.resume(response.body())
                            }

                            override fun onFailure(call: Call<HomeListResponse>, t: Throwable) {
                                cont.resumeWithException(t)
                            }
                        })
                    }
                }
                val result = collectArticleAsync?.await()
                when (result) {
                    is String -> onCollectArticleListener.collectArticleFailed(result, isAdd)
                    is HomeListResponse -> onCollectArticleListener.collectArticleSuccess(result, isAdd)
                    else -> onCollectArticleListener.collectArticleFailed(Constant.RESULT_NULL, isAdd)
                }
            } catch (t: Throwable) {
                t.printStackTrace()
                onCollectArticleListener.collectArticleFailed(t.toString(), isAdd)
                return@async
            }
        }
    }

    /**
     * cancel collect article Request
     */
    override fun cancelCollectRequest() {
        collectArticleCall.cancelCall()
        collectArticleAsync?.cancel()
    }
}