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

import java.util.LinkedList;
import java.util.List;

import com.frostwire.search.PagedWebSearchPerformer;
// import com.frostwire.search.torrent.TorrentJsonSearchPerformer;
import com.frostwire.search.SearchResult;
import com.frostwire.search.domainalias.DomainAliasManager;
import com.frostwire.util.JsonUtils;

/**
 * @author gubatron
 * @author aldenml
 *
 * API URI for frostwire is: http://api.btdigg.org/api/private-a6c30ec834a040bb/s02
 * GET only - https is available but will issue warning about domain mismatch
 *            (api.btdigg.org vs btdigg.org)
 * BtDigg is a DHT aggregator and not a tracker - it has no information regarding
 *            .torrent files, trackers, or seeds and so provides magnet links only
 *
 * url:
 *   GET http://api.btdigg.org/api/private-a6c30ec834a040bb/s02
 * args:
 *   q=     // search query (required)
 *   p=     // page number
 *   order= // search order - one of: '0' => relevance
                                      '1' => popularity
                                      '2' => age
                                      '3' => download size
                                      '4' => number of files
 * returns:
 *   up to 10 paged items as a JSON list of hashes
 * example response:
 * [
 *   {
 *     "files":     42 ,                                            // number of files in the torrent
 *     "info":      "http://btdigg.org/search?info_hash=HASH" ,     // url to details page
 *     "added":     1234567890 ,                                    // created_at timestamp
 *     "name":      "Torrent Title" ,                               // friendly name
 *     "weight":    60655 ,                                         // popularity
 *     "info_hash": "HASH" ,                                        // torrent hash
 *     "reqs":      3 ,                                             // number of times downloaded
 *     "magnet":    "magnet:?xt=urn:btih:HASH&dn=Torrent%20Title" , // magnet link
 *     "ff":        0.0 ,                                           // dunno
 *     "seen":      1234567890 ,                                    // updated_at timestamp
 *     "size":      9876543210                                      // download size in bytes
 *   } ,
 *   ....
 * ]
 */
public class BtdiggSearchPerformer extends PagedWebSearchPerformer {

    private static final String API_URL = "http://api.btdigg.org/api/private-a6c30ec834a040bb/s02";
    private static final String QUERY_PARAM = "?q=";
    private static final String PAGE_PARAM = "&p=";
    private static final String ORDER_PARAM = "&order="; // unused


    public BtdiggSearchPerformer(DomainAliasManager domainAliasManager, long token, String keywords, int timeout) {
        super(domainAliasManager, token, keywords, timeout, 1);
    }

    @Override
    protected String getUrl(int page, String encodedKeywords) {
        return API_URL + QUERY_PARAM + encodedKeywords + PAGE_PARAM + page;
    }

    @Override
    protected List<? extends SearchResult> searchPage(String page) {

System.out.println("BtdiggSearchPerformer::searchPage()  IN");

        List<SearchResult> results = new LinkedList<SearchResult>();
        BtdiggResponse response = JsonUtils.toObject("{\"collection\":" + page + "}", BtdiggResponse.class);

String json = JsonUtils.toJson(response, true);
System.out.println("BtdiggSearchPerformer::searchPage() MID page=\n" + json);
System.out.println("BtdiggSearchPerformer::searchPage() MID nItems=" + response.collection.size());

        for (BtdiggItem item : response.collection) {
            if (!isStopped()) {

System.out.println("BtdiggSearchPerformer::searchPage() add item=" + item.name);

                results.add(new BtdiggSearchResult(item));
            }
        }

System.out.println("BtdiggSearchPerformer::searchPage() OUT nResults=" + results.size());

        return results;
    }
}
