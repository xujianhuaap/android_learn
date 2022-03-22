package com.skullmind.io.main.vo

import android.os.Parcelable
import com.skullmind.io.R
import kotlinx.android.parcel.Parcelize


const val KEY_CHECKIN = "选座登记" // 选座&办登机牌

const val KEY_REFUND = "退改" // 退改

const val KEY_Mzhy = "明珠会员" // 明珠会员

const val KEY_MALL = "南航商城" // 南航商城

const val KEY_LoungeIn = "休息室预售" // 休息室预售

const val KEY_CabinUpgrade = "升舱" //升舱

const val KEY_Luggage = "行李购买" // 额外行李购买

const val KEY_Wallet = "钱包" // 钱包

const val KEY_Invoice = "电子发票" // 电子发票

const val KEY_MyMeal = "餐食预定" //餐食预定

const val KEY_VipCoupon = "专享特惠" //专享特惠

const val KEY_FamilyFly = "家享飞" // 家享飞

const val KEY_Holiday = "旅游度假" // 旅游度假

const val KEY_Taxi = "用车" // 用车

const val KEY_PreferentialPrice = "特惠机票" // 特惠机票

const val KEY_AirportVipService = "贵宾服务" //机场贵宾服务

const val KEY_SUIT_COUPON = "快乐飞" //快乐飞

const val KEY_Certification = "实名认证" //实名认证


fun getMenuData() = listOf(
    ConfigItem(KEY_CHECKIN, "", R.mipmap.main_icon_home_check_in),
    ConfigItem(KEY_REFUND, "", R.mipmap.main_icon_home_refund),//
    ConfigItem(KEY_Mzhy, "", R.mipmap.main_icon_home_sky_pearl),//
    ConfigItem(KEY_MALL, "", R.mipmap.main_icon_home_mall),//
    ConfigItem(KEY_LoungeIn, "", R.mipmap.main_icon_home_xxsys),
    ConfigItem(KEY_CabinUpgrade, "", R.mipmap.main_icon_home_upgrade_cabin),
    ConfigItem(KEY_Luggage, "", R.mipmap.main_icon_home_extra_luggage),
    ConfigItem(KEY_Wallet, "", R.mipmap.main_icon_home_wallet),
    ConfigItem(KEY_Invoice, "", R.mipmap.main_icon_home_electric_invoice),
    ConfigItem(KEY_MyMeal, "", R.mipmap.main_icon_home_meal),
    ConfigItem(KEY_VipCoupon, "", R.mipmap.main_icon_home_vip_coupon),
    ConfigItem(KEY_FamilyFly, "", R.mipmap.main_icon_home_family_fly),
    ConfigItem(KEY_Holiday, "", R.mipmap.main_icon_home_holiday),
    ConfigItem(KEY_Taxi, "", R.mipmap.main_icon_home_car),
    ConfigItem(KEY_PreferentialPrice, "", R.mipmap.main_icon_home_cheap),
    ConfigItem(KEY_Certification, "", R.mipmap.main_icon_home_certify),
    ConfigItem(KEY_AirportVipService, "", R.mipmap.main_icon_home_airport_vip_service),
    ConfigItem(KEY_SUIT_COUPON, "", R.mipmap.main_icon_home_suit_coupon)

)

@Parcelize
data class ConfigItem(val title: String, val link: String, val resId: Int) : Parcelable


