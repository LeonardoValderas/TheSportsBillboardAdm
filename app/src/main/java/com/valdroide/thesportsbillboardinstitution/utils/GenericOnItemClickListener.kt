package com.valdroide.thesportsbillboardinstitution.utils

interface GenericOnItemClickListener {
    fun onClickActive(position: Int, any: Any)
    fun onClickUnActive(position: Int, any: Any)
    fun onClickUpdate(position: Int, any: Any)
    fun onClickDelete(position: Int, any: Any)
}