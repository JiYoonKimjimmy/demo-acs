package me.jimmyberg.acs.address.domain

data class Address(
    private val zipCode: String,
    private val baseAddress: String,
    private val detailAddress: String
) {

    fun getZipCode(): String = this.zipCode
    fun getBaseAddress(): String = this.baseAddress
    fun getDetailAddress(): String = this.detailAddress

}