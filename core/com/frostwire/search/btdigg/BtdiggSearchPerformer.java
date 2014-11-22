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
import com.frostwire.search.SearchResult;
import com.frostwire.search.domainalias.DomainAliasManager;
import com.frostwire.util.JsonUtils;

/**
 * @author gubatron
 * @author aldenml
 *
 */
public class BtdiggSearchPerformer extends PagedWebSearchPerformer {
    /*
      API for frostwire is: http://api.btdigg.org/api/private-a6c30ec834a040bb/s02
      https is also available, but it will issue warnings about domain
      mismatch (api.btdigg.org vs btdigg.org)

      args:
      q     - search query (required)
      p     - page (optional)
      order - search order (optional). Order codes same as at btdigg.org.
    */
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

System.out.println("BtdiggSearchPerformer::searchPage()  IN page=" + page);

        List<SearchResult> results = new LinkedList<SearchResult>();
        BtdiggResponse response = JsonUtils.toObject("{\"collection\":" + page + "}", BtdiggResponse.class);

System.out.println("BtdiggSearchPerformer::searchPage() MID resp nItems=" + response.collection.size());

        for (BtdiggItem item : response.collection) {
            if (!isStopped()) {

System.out.println("BtdiggSearchPerformer::searchPage() item=" + item.name);

                results.add(new BtdiggSearchResult(item));
            }
        }

System.out.println("BtdiggSearchPerformer::searchPage() OUT nResults=" + results.size());

        return results;
    }
}
