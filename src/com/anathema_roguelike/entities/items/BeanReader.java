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

package com.anathema_roguelike.entities.items;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.Reader;

//This class is needed to disambiguate a read method on the superclass that scala cannot differentiate
public class BeanReader extends CsvBeanReader {

    public BeanReader(Reader reader, CsvPreference preferences) {
        super(reader, preferences);
    }

    public <T> T readByClass(final Class<T> clazz, final String[] nameMapping, final CellProcessor... processors) throws IOException {
        return read(clazz, nameMapping, processors);
    }
}
