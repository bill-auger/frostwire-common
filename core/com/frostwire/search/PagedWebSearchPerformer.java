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

package com.frostwire.search;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.frostwire.search.domainalias.DomainAliasManager;

/**
 * @author gubatron
 * @author aldenml
 *
 */
public abstract class PagedWebSearchPerformer extends WebSearchPerformer {

    private final int nPages;

    public PagedWebSearchPerformer(DomainAliasManager domainAliasManager, long token, String keywords, int timeout, int nPages) {
        super(domainAliasManager, token, keywords, timeout);
        this.nPages = nPages;
    }

    @Override
    public void perform() {
        for (int pageN = 1; !isStopped() && pageN <= nPages; pageN++) {
            onResults(this, searchPage(pageN));
        }
    }

    protected List<? extends SearchResult> searchPage(int pageN) {
        List<? extends SearchResult> results = Collections.emptyList();
        try {
            String url = getUrl(pageN, getEncodedKeywords());

System.out.println("PagedWebSearchPerformer::searchPage()  IN url=" + url);

            String text = fetchSearchPage(url);
            if (text != null) {

System.out.println("PagedWebSearchPerformer::searchPage() MID url=" + url + " text=" + text);

                results = searchPage(text);
            }
        } catch (Throwable e) {

System.out.println("PagedWebSearchPerformer::searchPage() ERR url=" + getUrl(pageN, getEncodedKeywords()) + " Throwable=" + e);

            checkAccesibleDomains();
        }
System.out.println("PagedWebSearchPerformer::searchPage() OUT url=" + getUrl(pageN, getEncodedKeywords()) + " nResults" + results.size());

        return results;
    }

    protected String fetchSearchPage(String url) throws IOException {

System.out.println("PagedWebSearchPerformer::fetchSearchPage() url=" + url);

        return fetch(url);
    }

    protected abstract String getUrl(int pageN, String encodedKeywords);

    protected abstract List<? extends SearchResult> searchPage(String page);
}
