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

import com.frostwire.search.torrent.AbstractTorrentSearchResult;


/**
 * @author gubatron
 * @author aldenml
 */
public class BtdiggSearchResult extends AbstractTorrentSearchResult {
    private String filename;
    private String displayName;
    private String detailsUrl;
    private String torrentUrl;
    private String infoHash;
    private long size;
    private long creationTime;
    private int seeds;

    public BtdiggSearchResult(BtdiggItem response) {
        this.detailsUrl = response.info;
        this.displayName = response.name;
        this.torrentUrl = response.magnet;
        this.filename = response.name  + ".torrent";
        this.infoHash = response.info_hash;
        this.seeds = 42;
        this.creationTime = response.added;
        this.size = response.size;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public String getSource() {
        return "BtDigg";
    }

    @Override
    public String getHash() {
        return infoHash;
    }

    @Override
    public int getSeeds() {
        return seeds;
    }

    @Override
    public String getDetailsUrl() {
        return detailsUrl;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public String getTorrentUrl() {
        return torrentUrl;
    }
}
