/*******************************************************************************
 * Copyright (c) 2019. Carl Minden
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.anathema_roguelike.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	
	public static final int DUNGEON_WIDTH = 108;
	public static final int DUNGEON_HEIGHT = 57;
	public static final int DUNGEON_DEPTH = 1;
	public static final boolean DEBUG = true;
	
	private static Properties configFile;
	
	static {
		
		Properties defaults = new Properties();
		
		defaults.put("font", "Lucida Console");
		defaults.put("fontsize", "20");
		
		configFile = new Properties(defaults);
		try {
			configFile.load(new FileInputStream("res/config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static String getString(String key) {
		return configFile.getProperty(key);
	}


	public static int getInt(String key) {
		return Integer.parseInt(configFile.getProperty(key));
	}
}
