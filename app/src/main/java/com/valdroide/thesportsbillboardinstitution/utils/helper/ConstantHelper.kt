package com.valdroide.thesportsbillboardinstitution.utils.helper

object ConstantHelper {
    const val PERMISSION_GALERY: Int = 101
    const val PNG: String = ".PNG"
    const val JSON_ENTITY: String = "json_entity"

    object USER_FRAGMENT {
        const val ID_MENU_FRAGMENT: String = "id_menu_fragment"
        const val ID_TOURNAMENT_FRAGMENT: String = "id_tournament_fragment"
       // const val ERROR_FRAGMENT: String = "error_fragment"
        const val WITHOUT_SCREEN: Int = 1111
    }

    object INTENT_EXTRA {
        const val ID_MENU_TO: String = "id_menu_to"
    }

    object ENTITY{
      enum class ENTITIES {
          MENU,SUBMENU,TOURNAMENT,FIELD, TIME, PLAYER, TEAM
      }
    }
}
