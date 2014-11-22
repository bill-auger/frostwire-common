/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011-2014, FrostWire(R). All rights reserved.
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
 */

package com.frostwire.search.btdigg;

/**
 * @author gubatron
 * @author aldenml
 */
public class BtdiggItem {
    public long files;       // number of files in the torrent
    public String info;      // url to details page
    public long added;       // timestamp
    public String name;      // torrent title
    public long weight;      // dunno
    public String info_hash; // torrent hash
    public long reqs;        // dunno
    public String magnet;    // magnet link
    public double ff;        // dunno
    public long seen;        // dunno
    public long size;        // download size
}
