

package com.anathema_roguelike
package stats.characterstats

import com.anathema_roguelike.stats.Stat
import com.anathema_roguelike.entities.characters.Character

abstract class CharacterStat(val character: Character) extends Stat[Character](character) {

}