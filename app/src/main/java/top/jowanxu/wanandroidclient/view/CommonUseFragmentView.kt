package top.jowanxu.wanandroidclient.view

import top.jowanxu.wanandroidclient.bean.FriendListResponse
import top.jowanxu.wanandroidclient.bean.HotKeyResponse

/**
 * CommonUse Fragment View interface
 */
interface CommonUseFragmentView {
    /**
     * get Friend list after operation
     */
    fun getFriendListAfter()
    /**
     * get Friend list Success
     */
    fun getFriendListSuccess(result: FriendListResponse, hotResult: HotKeyResponse)
    /**
     * get Friend list Failed
     */
    fun getFriendListFailed(errorMessage: String?)
    /**
     * get Friend list data size equal zero
     */
    fun getFriendListZero()
}