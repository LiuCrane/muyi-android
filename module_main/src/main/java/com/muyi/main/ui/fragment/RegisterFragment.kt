package com.muyi.main.ui.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.util.DialogHelper
import com.lljjcoder.Interface.OnCustomCityPickerItemClickListener
import com.lljjcoder.bean.CustomCityData
import com.lljjcoder.citywheel.CustomConfig
import com.lljjcoder.style.citycustome.CustomCityPicker
import com.muyi.main.BR
import com.muyi.main.R
import com.muyi.main.databinding.FragmentLoginBinding
import com.muyi.main.viewmodel.RegisterViewModel


/**
 * Created by hq on 2022/7/30.
 **/
@Route(path = AppConstants.Router.Login.F_REGISTER)
class RegisterFragment : BaseFragment<FragmentLoginBinding, RegisterViewModel>() {
    var defaultProvinceName = "浙江省"
    var defaultCityName = "杭州市"
    var defaultCountyName = "上城区"

    override fun initContentView(): Int {
        return R.layout.fragment_register
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun useBaseLayout(): Boolean {
        return false
    }

    override fun initViewObservable() {
        val mProvinceListData: MutableList<CustomCityData> = ArrayList()
        val mCityListData: MutableList<CustomCityData> = ArrayList()
        val mCountyListData: MutableList<CustomCityData> = ArrayList()

        viewModel.uc.getAllAddressEvent.observe(this) {
            if (it.list.isNullOrEmpty()) {

            } else {
                mProvinceListData.clear()
                it.list?.forEach { provinceBean ->
                    val province = CustomCityData(provinceBean.id, provinceBean.name)
                    mCityListData.clear()
                    provinceBean.children?.forEach { cityBean ->
                        val city = CustomCityData(cityBean.id, cityBean.name)
                        mCountyListData.clear()
                        if (cityBean.children.isNullOrEmpty()) {
                            val county = CustomCityData(cityBean.id, cityBean.name)
                            mCountyListData.add(county)
                        } else {
                            cityBean.children?.forEach { countyBean ->
                                val county = CustomCityData(countyBean.id, countyBean.name)
                                mCountyListData.add(county)
                            }
                        }
                        city.list.addAll(mCountyListData)
                        mCityListData.add(city)
                    }
                    province.list.addAll(mCityListData)
                    mProvinceListData.add(province)
                }
            }
        }
        viewModel.uc.chooseRegionEvent.observe(this) {
            if (mProvinceListData.isNullOrEmpty()) {
                showErrorToast("地区数据获取失败，请重试")
                return@observe
            }

            val customCityPicker = CustomCityPicker(context)
            val mWheelType = CustomConfig.WheelType.PRO_CITY_DIS

            val cityConfig = CustomConfig.Builder()
                .title("选择地区")
                .visibleItemsCount(5)
                .setCityData(mProvinceListData)
                .province(defaultProvinceName)
                .city(defaultCityName)
                .district(defaultCountyName)
                .provinceCyclic(false)
                .cityCyclic(false)
                .districtCyclic(false)
                .setCustomItemLayout(com.muyi.main.R.layout.item_city)
                .setCustomItemTextViewId(com.muyi.main.R.id.item_city_name_tv)
                .drawShadows(true)
                .setCityWheelType(mWheelType)
                .build()


            customCityPicker.setCustomConfig(cityConfig)
            customCityPicker.setOnCustomCityPickerItemClickListener(object :
                OnCustomCityPickerItemClickListener() {
                override fun onSelected(
                    province: CustomCityData,
                    city: CustomCityData,
                    district: CustomCityData
                ) {
                    defaultProvinceName = province.name
                    defaultCityName = city.name
                    defaultCountyName = district.name

                    if (defaultCityName == defaultCountyName)
                        viewModel.storeRegion.set(province.name + " " + city.name)
                    else
                        viewModel.storeRegion.set(province.name + " " + city.name + " " + district.name)
                    viewModel.storeRegionId.set(district.id)
                }
            })
            customCityPicker.showCityPicker()

        }
        viewModel.uc.successLiveEvent.observe(this) {
            DialogHelper.showNoCancelDialog(requireContext(), "注册成功", "您的注册信息正在审核中，请稍后在登录页登录。") {
                this@RegisterFragment.back()
            }
        }
    }

    override fun initData() {
        viewModel.getAllAddress()

    }
}
