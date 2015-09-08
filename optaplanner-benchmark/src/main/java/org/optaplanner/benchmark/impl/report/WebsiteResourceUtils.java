/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.benchmark.impl.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

public class WebsiteResourceUtils {

    private static final String RESOURCE_NAMESPACE = "/org/optaplanner/benchmark/impl/report/";

    private static final String JQUERY_VERSION = "2.1.4";
    private static final String BOOTSTRAP_VERSION = "3.3.5";
    private static final String PRETTIFY_VERSION = "4-Mar-2013";

    public static void copyResourcesTo(File benchmarkReportDirectory) {
        // Twitter Bootstrap
        copyResource(benchmarkReportDirectory, "webjars/bootstrap/" + BOOTSTRAP_VERSION + "/css/bootstrap.min.css");
        copyResource(benchmarkReportDirectory, "webjars/bootstrap/" + BOOTSTRAP_VERSION + "/img/glyphicons-halflings-white.png");
        copyResource(benchmarkReportDirectory, "webjars/bootstrap/" + BOOTSTRAP_VERSION + "/img/glyphicons-halflings.png");
        copyResource(benchmarkReportDirectory, "webjars/bootstrap/" + BOOTSTRAP_VERSION + "/js/bootstrap.min.js");
        // JQuery
        copyResource(benchmarkReportDirectory, "webjars/jquery/" + JQUERY_VERSION + "/js/jquery.min.js");
        // Prettify
        copyResource(benchmarkReportDirectory, "webjars/prettify/" + PRETTIFY_VERSION + "/css/prettify.css");
        copyResource(benchmarkReportDirectory, "webjars/prettify/" + PRETTIFY_VERSION + "/js/prettify.js");
        // Website resources
        copyResource(benchmarkReportDirectory, RESOURCE_NAMESPACE + "website/css/benchmarkReport.css");
        copyResource(benchmarkReportDirectory, RESOURCE_NAMESPACE + "website/img/optaPlannerLogo.png");
    }

    private static void copyResource(File benchmarkReportDirectory, String websiteResource) {
        File outputFile = new File(benchmarkReportDirectory, websiteResource);
        InputStream in = null;
        OutputStream out = null;
        try {
            in = WebsiteResourceUtils.class.getResourceAsStream(websiteResource);
            if (in == null) {
                throw new IllegalStateException("The websiteResource (" + websiteResource
                        + ") does not exist.");
            }
            outputFile.getParentFile().mkdirs();
            out = new FileOutputStream(outputFile);
            IOUtils.copy(in, out);
        } catch (IOException e) {
            throw new IllegalStateException("Could not copy websiteResource (" + websiteResource
                    + ") to outputFile (" + outputFile + ").", e);
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

    private WebsiteResourceUtils() {
    }

}
