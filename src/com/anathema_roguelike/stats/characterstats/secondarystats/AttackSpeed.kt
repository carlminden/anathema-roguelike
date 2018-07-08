package com.anathema_roguelike.stats.characterstats.secondarystats

import com.anathema_roguelike.entities.characters.Character
import com.anathema_roguelike.entities.characters.inventory.PrimaryWeapon
import com.anathema_roguelike.stats.characterstats.CharacterStat
import com.anathema_roguelike.stats.itemstats.WeaponSpeed

class AttackSpeed(`object`: Character)// TODO Auto-generated constructor stub
    : CharacterStat(`object`) {

    override fun getAmount(): Double {
        return getObject().inventory.getSlot<PrimaryWeapon>().equippedItem!!.getStatAmount(WeaponSpeed::class.java)
    }
}
