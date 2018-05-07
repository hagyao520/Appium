<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:math="http://exslt.org/math"
                xmlns:testng="http://testng.org">

    <xsl:output method="html" indent="yes" omit-xml-declaration="yes"
                doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
                doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"/>
    <xsl:output name="text" method="text"/>
    <xsl:output name="xml" method="xml" indent="yes"/>
    <xsl:output name="html" method="html" indent="yes" omit-xml-declaration="yes"/>
    <xsl:output name="xhtml" method="xhtml" indent="yes" omit-xml-declaration="yes"/>

    <xsl:param name="testNgXslt.outputDir"/>
    <xsl:param name="testNgXslt.cssFile"/>
    <xsl:param name="testNgXslt.showRuntimeTotals"/>
    <xsl:param name="testNgXslt.reportTitle"/>
    <xsl:param name="testNgXslt.sortTestCaseLinks"/>
    <xsl:param name="testNgXslt.chartScaleFactor"/>
    <!-- FAIL,PASS,SKIP,CONF,BY_CLASS-->
    <xsl:param name="testNgXslt.testDetailsFilter"/>

    <xsl:variable name="testDetailsFilter" select="if ($testNgXslt.testDetailsFilter) then $testNgXslt.testDetailsFilter else 'FAIL,PASS,SKIP'"/>

    <xsl:variable name="chartWidth" select="round(600 * testng:getVariableSafe($testNgXslt.chartScaleFactor, 1))"/>
    <xsl:variable name="chartHeight" select="round(200 * testng:getVariableSafe($testNgXslt.chartScaleFactor, 1))"/>

    <xsl:template name="writeCssFile">
        <xsl:result-document href="{testng:absolutePath('style.css')}" format="text">
            <xsl:choose>
                <xsl:when test="testng:isFilterSelected('CONF') = 'true'">
                    .testMethodStatusCONF { }
                </xsl:when>
                <xsl:otherwise>
                    .testMethodStatusCONF { display: none; }
                </xsl:otherwise>
            </xsl:choose>

            <xsl:choose>
                <xsl:when test="testng:isFilterSelected('FAIL') = 'true'">
                    .testMethodStatusFAIL { background-color: #FFBBBB; }
                </xsl:when>
                <xsl:otherwise>
                    .testMethodStatusFAIL { background-color: #FFBBBB; display: none; }
                </xsl:otherwise>
            </xsl:choose>

            <xsl:choose>
                <xsl:when test="testng:isFilterSelected('PASS') = 'true'">
                    .testMethodStatusPASS { background-color: lightgreen; }
                </xsl:when>
                <xsl:otherwise>
                    .testMethodStatusPASS { background-color: lightgreen; display: none; }
                </xsl:otherwise>
            </xsl:choose>

            <xsl:choose>
                <xsl:when test="testng:isFilterSelected('SKIP') = 'true'">
                    .testMethodStatusSKIP { background-color: #FFFFBB; }
                </xsl:when>
                <xsl:otherwise>
                    .testMethodStatusSKIP { background-color: #FFFFBB; display: none; }
                </xsl:otherwise>
            </xsl:choose>

            <![CDATA[
            body { font-family: Arial, sans-serif; font-size: 12px; padding: 10px; margin: 0px; background-color: white; }
            a, a:hover, a:active, a:visited { color: navy; }

            .suiteMenuHeader { margin-top: 10px; }
            .suiteMenuHeader td { padding: 5px; background-color: #e0e0e0; font-size: 12px; width: 100%; vertical-align: top; }

            .suiteStatusPass, .suiteStatusFail { padding-right: 20px; width: 20px; height: 20px; margin: 2px 4px 2px 2px; display: inline; }
            .suiteStatusPass { background-color: green; }
            .suiteStatusFail { background-color: red; }

            .testCaseLink, .testCaseLinkSelected { margin-top: 2px; padding: 4px; cursor: pointer; }
            .testCaseLink { background-color: #f6f6f6; }
            .testCaseLinkSelected { background-color: lightblue; border: 1px solid gray;  padding: 3px; }
            .testCaseFail, .testCasePass, .testCaseSkip { padding-right: 15px; width: 15px; height: 15px; margin: 2px 4px 2px 2px; display: inline; }
            .testCaseFail { background-color: red; }
            .testCasePass { background-color: green; }
            .testCaseSkip { background-color: yellow; }

            tr.methodsTableHeader { background-color: #eaf0f7; font-weight: bold; }
            tr.methodsTableHeader td { padding: 3px; }

            .testMethodStatusFAIL a, .testMethodStatusPASS a, .testMethodStatusSKIP a { color:navy; text-decoration: none; cursor: pointer; }
            .testMethodStatusFAIL td, .testMethodStatusPASS td, .testMethodStatusSKIP td { padding: 3px; }

            .testMethodDetails, .testMethodDetailsVisible { padding: 5px; background-color: #f5f5f5; margin: 1px; }
            .testMethodDetails { display: none; }

            .testMethodsTable { margin-top: 10px; font-size: 12px; }
            .testMethodsTable td { border-width: 1px 0 0 1px; border-color: white; border-style:solid; }
            .testMethodsTable .testMethodStatusCONF td.firstMethodCell { border-left: 5px solid gray; }
            ]]>
        </xsl:result-document>
    </xsl:template>

    <xsl:template name="writeJsFile">
        <xsl:result-document href="{testng:absolutePath('main.js')}" format="text">
            <![CDATA[
            var selectedTestCaseLink;

            function clearAllSelections() {
                if (selectedTestCaseLink != null) {
                    selectedTestCaseLink.className = "testCaseLink";
                }
            }

            function selectTestCaseLink(testCaseLinkElement) {
                clearAllSelections();
                testCaseLinkElement.className = "testCaseLinkSelected";
                selectedTestCaseLink = testCaseLinkElement;
            }

            function switchTestMethodsView(checkbox) {
                document.getElementById("testMethodsByStatus").style["display"] = checkbox.checked ? "none" : "block";
                document.getElementById("testMethodsByClass").style["display"] = checkbox.checked ? "block" : "none";
            }

            function toggleVisibility(elementId) {
                var displayElement = document.getElementById(elementId);
                if (getCurrentStyle(displayElement, "display") == "none") {
                    displayElement.style["display"] = "block";
                } else {
                    displayElement.style["display"] = "none";
                }
            }

            function toggleDetailsVisibility(elementId) {
                var displayElement = document.getElementById(elementId);
                if (displayElement.className == "testMethodDetails") {
                    displayElement.className = "testMethodDetailsVisible";
                } else {
                    displayElement.className = "testMethodDetails";
                }
            }

            function getCurrentStyle(elem, prop) {
                if (elem.currentStyle) {
                    var ar = prop.match(/\w[^-]*/g);
                    var s = ar[0];
                    for(var i = 1; i < ar.length; ++i) {
                        s += ar[i].replace(/\w/, ar[i].charAt(0).toUpperCase());
                    }
                    return elem.currentStyle[s];
                } else if (document.defaultView.getComputedStyle) {
                    return document.defaultView.getComputedStyle(elem, null).getPropertyValue(prop);
                }
            }

            function testMethodsFilterChanged(filterCheckBox, status) {
                var filterAll = document.getElementById("methodsFilter_ALL");
                var filterFail = document.getElementById("methodsFilter_FAIL");
                var filterPass = document.getElementById("methodsFilter_PASS");
                var filterSkip = document.getElementById("methodsFilter_SKIP");
                var filterConf = document.getElementById("methodsFilter_CONF");
                if (filterCheckBox != filterAll) {
                    filterMethods(filterCheckBox, status);
                    checkMainFilter(filterAll, filterFail, filterPass, filterSkip, filterConf);
                } else {
                    filterFail.checked = filterPass.checked = filterSkip.checked = filterConf.checked = filterAll.checked;
                    filterMethods(filterAll, "FAIL");
                    filterMethods(filterAll, "PASS");
                    filterMethods(filterAll, "SKIP");
                    filterMethods(filterAll, "CONF");
                }
                closeAllExpandedDetails();
            }

            function checkMainFilter(filterAll, filterFail, filterPass, filterSkip, filterConf) {
                if ((filterFail.checked == filterPass.checked) && (filterPass.checked == filterSkip.checked) && (filterSkip.checked == filterConf.checked)) {
                    filterAll.checked = filterFail.checked;
                } else {
                    filterAll.checked = false;
                }
            }

            function filterMethods(filterCheckBox, status) {
                var visible = filterCheckBox.checked;
                alterCssElement("testMethodStatus" + status, "display", visible ? "" : "none");
            }

            function alterCssElement(cssClass, element, value) {
                var rules;
                if (document.all) {
                    rules = 'rules';
                }
                else if (document.getElementById) {
                    rules = 'cssRules';
                }
                for (var i = 0; i < document.styleSheets.length; i++) {
                    for (var j = 0; j < document.styleSheets[i][rules].length; j++) {
                        if (document.styleSheets[i][rules][j].selectorText.indexOf(cssClass) > -1) {
                            document.styleSheets[i][rules][j].style[element] = value;
                            break;
                        }
                    }
                }
            }

            function closeAllExpandedDetails() {
                var node = document.getElementsByTagName("body")[0];
                //var re = new RegExp("\\btestMethodDetailsVisible\\b");
                var els = document.getElementsByTagName("div");
                for (var i = 0,j = els.length; i < j; i++) {
                    if (els[i].className == "testMethodDetailsVisible") {
                        els[i].className = "testMethodDetails";
                    }
                }
            }

            function renderSvgEmbedTag(chartWidth, chartHeight) {
                var success = false;
                var userAgent = navigator.userAgent;

                if (userAgent.indexOf("Firefox") > -1 || userAgent.indexOf("Safari") > -1) {
                    success = true;
                } else if (navigator.mimeTypes != null && navigator.mimeTypes.length > 0) {
                    if (navigator.mimeTypes["image/svg+xml"] != null) {
                        success = true;
                    }
                } else if (window.ActiveXObject) {
                    try {
                        testObj = new ActiveXObject("Adobe.SVGCtl");
                        success = true;
                    } catch (e) {}
                }

                var chartContainer = document.getElementById('chart-container');
                
                if (success) {
                    var chart = document.createElement('embed');
                    
                    chart.src = 'overview-chart.svg';
                    chart.type = 'image/svg+xml';
                    chart.width = chartWidth;
                    chart.height = chartHeight;
                    
                    chartContainer.appendChild(chart);
                } else {
                    var message = document.createElement('h4');
                    var text = document.createTextNode('SVG Pie Charts are not available. Please install a SVG viewer for your browser.');
                    
                    message.style.color = 'navy';
                    message.appendChild(text);
                    
                    chartContainer.appendChild(message);
                }
            }
            ]]>
        </xsl:result-document>
    </xsl:template>

    <xsl:template name="htmlHead">
        <head>
            <title>
                <xsl:value-of select="testng:getVariableSafe($testNgXslt.reportTitle, 'TestNG Results')"/>
            </title>
            <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
            <meta http-equiv="pragma" content="no-cache"/>
            <meta http-equiv="cache-control" content="max-age=0"/>
            <meta http-equiv="cache-control" content="no-cache"/>
            <meta http-equiv="cache-control" content="no-store"/>
            <LINK rel="stylesheet" href="style.css"/>
            <xsl:if test="$testNgXslt.cssFile">
                <LINK rel="stylesheet" href="{$testNgXslt.cssFile}"/>
            </xsl:if>
            <script type="text/javascript" src="main.js"/>
        </head>
    </xsl:template>

    <xsl:function name="testng:getVariableSafe">
        <xsl:param name="testVar"/>
        <xsl:param name="defaultValue"/>
        <xsl:value-of select="if ($testVar) then $testVar else $defaultValue"/>
    </xsl:function>

    <xsl:function name="testng:trim">
        <xsl:param name="arg"/>
        <xsl:sequence select="replace(replace($arg,'\s+$',''),'^\s+','')"/>
    </xsl:function>

    <xsl:function name="testng:absolutePath">
        <xsl:param name="fileName"/>
        <xsl:value-of select="concat('file:////', $testNgXslt.outputDir, '/', $fileName)"/>
    </xsl:function>

    <xsl:function name="testng:safeFileName">
        <xsl:param name="fileName"/>
        <xsl:value-of select="translate($fileName, '[]{}`~!@#$%^*(){};?/\|' , '______________________')"/>
    </xsl:function>

    <xsl:function name="testng:suiteContentFileName">
        <xsl:param name="suiteElement"/>
        <xsl:value-of select="testng:safeFileName(concat($suiteElement/@name, '.html'))"/>
    </xsl:function>

    <xsl:function name="testng:suiteGroupsFileName">
        <xsl:param name="suiteElement"/>
        <xsl:value-of select="testng:safeFileName(concat($suiteElement/@name, '_groups.html'))"/>
    </xsl:function>

    <xsl:function name="testng:testCaseContentFileName">
        <xsl:param name="testCaseElement"/>
        <xsl:value-of
                select="testng:safeFileName(concat($testCaseElement/../@name, '_', $testCaseElement/@name, '.html'))"/>
    </xsl:function>

    <xsl:function name="testng:concatParams">
        <xsl:param name="params"/>
        <xsl:variable name="outputString">
            <xsl:value-of separator="," select="for $i in ($params) return $i"/>
        </xsl:variable>
        <xsl:value-of select="$outputString"/>
    </xsl:function>

    <xsl:function name="testng:testMethodStatus">
        <xsl:param name="testMethodElement"/>
        <xsl:variable name="status" select="$testMethodElement/@status"/>
        <xsl:variable name="statusClass" select="concat('testMethodStatus', $status)"/>
        <xsl:value-of select="if ($testMethodElement/@is-config) then concat($statusClass, ' testMethodStatusCONF') else $statusClass"/>
    </xsl:function>

    <xsl:function name="testng:suiteMethodsCount">
        <xsl:param name="testCasesElements"/>
        <xsl:param name="state"/>
        <xsl:value-of
                select="if ($state = '*') then count($testCasesElements/class/test-method[not(@is-config)]) else count($testCasesElements/class/test-method[(@status=$state) and (not(@is-config))])"/>
    </xsl:function>

    <xsl:function name="testng:testCaseMethodsCount">
        <xsl:param name="testCaseElement"/>
        <xsl:param name="state"/>
        <xsl:value-of
                select="if ($state = '*') then count($testCaseElement/class/test-method[not(@is-config)]) else count($testCaseElement/class/test-method[(@status=$state) and (not(@is-config))])"/>
    </xsl:function>

    <xsl:function name="testng:suiteStateClass">
        <xsl:param name="testCaseElements"/>
        <xsl:value-of select="if (count($testCaseElements/class/test-method[(@status='FAIL') and (not(@is-config))]) > 0) then 'suiteStatusFail' else 'suiteStatusPass'"/>
    </xsl:function>

    <xsl:function name="testng:methodsDuration">
        <xsl:param name="testMethods"/>
        <xsl:variable name="durationMs" select="sum($testMethods/@duration-ms)"/>
        <xsl:value-of select="testng:formatDuration($durationMs)"/>
    </xsl:function>

    <xsl:function name="testng:formatDuration">
        <xsl:param name="durationMs"/>
        <!--Days-->
        <xsl:if test="$durationMs > 86400000">
            <xsl:value-of select="format-number($durationMs div 86400000, '#')"/>d
        </xsl:if>
        <!--Hours-->
        <xsl:if test="($durationMs > 3600000) and ($durationMs mod 86400000 > 1000)">
            <xsl:value-of select="format-number(($durationMs mod 86400000) div 3600000, '#')"/>h
        </xsl:if>
        <xsl:if test="$durationMs &lt; 86400000">
            <!--Minutes-->
            <xsl:if test="($durationMs > 60000) and ($durationMs mod 3600000 > 1000)">
                <xsl:value-of select="format-number(($durationMs mod 3600000) div 60000, '#')"/>m
            </xsl:if>
            <!--Seconds-->
            <xsl:if test="($durationMs > 1000) and ($durationMs mod 60000 > 1000)">
                <xsl:value-of select="format-number(($durationMs mod 60000) div 1000, '#')"/>s
            </xsl:if>
        </xsl:if>
        <!--Milliseconds - only when less than a second-->
        <xsl:if test="$durationMs &lt; 1000">
            <xsl:value-of select="$durationMs"/>&#160;ms
        </xsl:if>
    </xsl:function>

    <xsl:function name="testng:isFilterSelected">
        <xsl:param name="filterName"/>
        <xsl:value-of select="contains($testDetailsFilter, $filterName)"/>
    </xsl:function>

    <xsl:template name="formField">
        <xsl:param name="label"/>
        <xsl:param name="value"/>
        <xsl:if test="$value">
            <div>
                <b>
                    <xsl:value-of select="$label"/>:
                </b>
                <xsl:value-of select="$value"/>
            </div>
        </xsl:if>
    </xsl:template>

    <xsl:template name="formFieldList">
        <xsl:param name="label"/>
        <xsl:param name="value"/>
        <xsl:if test="count($value) > 0">
            <div>
                <b>
                    <xsl:value-of select="$label"/>:
                </b>
                <xsl:for-each select="$value">
                    <div>
                        &#160;&#160;&#160;&#160;-
                        <xsl:value-of select="."/>
                    </div>
                </xsl:for-each>
            </div>
        </xsl:if>
    </xsl:template>

    <xsl:template match="/testng-results">
        <xsl:call-template name="writeCssFile"/>
        <xsl:call-template name="writeJsFile"/>
        <html>
            <xsl:call-template name="htmlHead"/>
            <frameset cols="250px, 100%" frameborder="1">
                <frame name="navigation" src="navigation.html"/>
                <frame name="content" src="overview.html"/>
            </frameset>
        </html>

        <xsl:variable name="suiteElements" select="if (suite/@url) then document(suite/@url)/suite else suite"/>

        <xsl:call-template name="navigationFile">
            <xsl:with-param name="suiteElements" select="$suiteElements"/>
            <xsl:with-param name="reporterOutputElement" select="reporter-output"/>
        </xsl:call-template>

        <!--TODO: Review this-->
        <xsl:result-document href="{testng:absolutePath('overview-chart.svg')}" format="xml">
            <svg xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid meet" width="{$chartWidth}" height="{$chartHeight}"
                 viewBox="0 0 900 300">
                <defs>
                    <style type="text/css">
                        <![CDATA[
				            .axistitle { font-weight:bold; font-size:24px; font-family:Arial; text-anchor:middle; }
				            .xgrid, .ygrid, .legendtext { font-weight:normal; font-size:24px; font-family:Arial; }
				            .xgrid {text-anchor:middle;}
				            .ygrid {text-anchor:end;}
				            .gridline { stroke:black; stroke-width:1; }
				            .values { fill:black; stroke:none; text-anchor:middle; font-size:12px; font-weight:bold; }
	   		            ]]>
                    </style>
                </defs>
                <svg id="graphzone" preserveAspectRatio="xMidYMid meet" x="0" y="0">
                    <xsl:variable name="testCaseElements" select="
                    if (suite/@url) then
                        if (document(suite/@url)/suite/test/@url)
                            then document(document(suite/@url)/suite/test/@url)/test 
                            else document(suite/@url)/suite/test
                        else suite/test"/>
                    <xsl:variable name="failedCount" select="testng:suiteMethodsCount($testCaseElements, 'FAIL')"/>
                    <xsl:variable name="passedCount" select="testng:suiteMethodsCount($testCaseElements, 'PASS')"/>
                    <xsl:variable name="skippedCount" select="testng:suiteMethodsCount($testCaseElements, 'SKIP')"/>
                    <xsl:variable name="totalCount" select="testng:suiteMethodsCount($testCaseElements, '*')"/>

                    <xsl:variable name="pi" select="3.141592"/>
                    <xsl:variable name="radius" select="130"/>

                    <xsl:variable name="failedPercent" select="format-number($failedCount div $totalCount, '###%')"/>
                    <xsl:variable name="failedAngle" select="($failedCount div $totalCount) * $pi * 2"/>
                    <xsl:variable name="failedX" select="$radius * math:cos($failedAngle)"/>
                    <xsl:variable name="failedY" select="-1 * $radius * math:sin($failedAngle)"/>
                    <xsl:variable name="failedArc" select="if ($failedAngle >= $pi) then 1 else 0"/>

                    <xsl:variable name="failedAngle_text" select="$failedAngle div 2"/>
                    <xsl:variable name="failedX_text" select="($radius div 2) * math:cos($failedAngle_text)"/>
                    <xsl:variable name="failedY_text" select="(-1 * ($radius div 2) * math:sin($failedAngle_text))"/>

                    <xsl:variable name="passPercent" select="format-number($passedCount div $totalCount, '###%')"/>
                    <xsl:variable name="passAngle" select="($passedCount div $totalCount) * $pi * 2"/>
                    <xsl:variable name="passX" select="$radius * math:cos($passAngle)"/>
                    <xsl:variable name="passY" select="-1 * $radius * math:sin($passAngle)"/>
                    <xsl:variable name="passArc" select="if ($passAngle >= $pi) then 1 else 0"/>

                    <xsl:variable name="skipPercent" select="format-number($skippedCount div $totalCount, '###%')"/>
                    <xsl:variable name="skipAngle" select="($skippedCount div $totalCount) * $pi * 2"/>
                    <xsl:variable name="skipX" select="$radius * math:cos($skipAngle)"/>
                    <xsl:variable name="skipY" select="-1 * $radius * math:sin($skipAngle)"/>
                    <xsl:variable name="skipArc" select="if ($skipAngle >= $pi) then 1 else 0"/>

                    <rect style="fill:red;stroke-width:1;stroke:black;" x="10" y="86" width="20" height="20"/>
                    <text class="legendtext" x="40" y="105">Failed (<xsl:value-of select="$failedPercent"/>)
                    </text>
                    <rect style="fill:green;stroke-width:1;stroke:black;" x="10" y="125" width="20" height="20"/>
                    <text class="legendtext" x="40" y="143">Passed (<xsl:value-of select="$passPercent"/>)
                    </text>
                    <rect style="fill:yellow;stroke-width:1;stroke:black;" x="10" y="163" width="20" height="20"/>
                    <text class="legendtext" x="40" y="182">Skipped (<xsl:value-of select="$skipPercent"/>)
                    </text>
                    <g style="stroke:black;stroke-width:1" transform="translate(450,150)">
                        <xsl:variable name="failedRotation" select="(($skippedCount) div $totalCount) * 360"/>
                        <xsl:if test="($failedCount div $totalCount) > 0">
                            <g style="fill:red"
                               transform="rotate(-{$failedRotation}) translate({round($failedX_text div 4)}, {round($failedY_text div 4)})">
                                <path d="M 0 0 h {$radius} A {$radius},{$radius} 0,{$failedArc},0 {$failedX},{$failedY} z"/>
                            </g>
                        </xsl:if>
                        <xsl:variable name="passRotation" select="(($failedCount + $skippedCount) div $totalCount) * 360"/>
                        <xsl:if test="($passedCount div $totalCount) > 0">
                            <g style="fill:green" transform="rotate(-{$passRotation})">
                                <path d="M 0 0 h {$radius} A {$radius},{$radius} 0,{$passArc},0 {$passX},{$passY} z"/>
                            </g>
                        </xsl:if>
                        <xsl:if test="($skippedCount div $totalCount) > 0">
                            <g style="fill:yellow" transform="rotate(360)">
                                <path d="M 0 0 h {$radius} A {$radius},{$radius} 0,{$skipArc},0 {$skipX},{$skipY} z"/>
                            </g>
                        </xsl:if>
                    </g>
                </svg>
            </svg>
        </xsl:result-document>


        <!-- Results overview file -->
        <xsl:result-document href="{testng:absolutePath('overview.html')}" format="xhtml">
            <html xmlns="http://www.w3.org/1999/xhtml">
                <xsl:call-template name="htmlHead"/>
                <body>
                    <h2>Test suites overview</h2>
                    <table width="100%">
                        <tr>
                            <td align="center" id="chart-container">
                                <script type="text/javascript">
                                    renderSvgEmbedTag(<xsl:value-of select="$chartWidth"/>, <xsl:value-of select="$chartHeight"/>);
                                </script>
                            </td>
                        </tr>
                    </table>
                    <xsl:for-each select="$suiteElements">
                        <xsl:variable name="testCaseElements"
                                      select="if (test/@url) then document(test/@url)/test else test"/>
                        <table width="100%" cellpadding="5" cellspacing="1">
                            <tr style="background-color: #eaf0f7;">
                                <td width="100%">
                                    <div class="{testng:suiteStateClass($testCaseElements)}"/>
                                    <xsl:value-of select="@name"/>
                                </td>
                                <xsl:call-template name="percentageOverview">
                                    <xsl:with-param name="failedCount"
                                                    select="testng:suiteMethodsCount($testCaseElements, 'FAIL')"/>
                                    <xsl:with-param name="passedCount"
                                                    select="testng:suiteMethodsCount($testCaseElements, 'PASS')"/>
                                    <xsl:with-param name="skippedCount"
                                                    select="testng:suiteMethodsCount($testCaseElements, 'SKIP')"/>
                                    <xsl:with-param name="totalCount"
                                                    select="testng:suiteMethodsCount($testCaseElements, '*')"/>
                                    <xsl:with-param name="totalDuration"
                                                    select="testng:methodsDuration($testCaseElements/class/test-method)"/>
                                </xsl:call-template>
                            </tr>
                            <xsl:for-each select="$testCaseElements">
                                <tr style="background-color: #f5f5f5; font-size: 12px;">
                                    <td>
                                        <xsl:value-of select="@name"/>
                                    </td>
                                    <td align="center">
                                        <xsl:value-of select="testng:testCaseMethodsCount(., 'FAIL')"/>
                                    </td>
                                    <td align="center">
                                        <xsl:value-of select="testng:testCaseMethodsCount(., 'PASS')"/>
                                    </td>
                                    <td align="center">
                                        <xsl:value-of select="testng:testCaseMethodsCount(., 'SKIP')"/>
                                    </td>
                                    <td align="center">
                                        <xsl:value-of select="testng:testCaseMethodsCount(., '*')"/>
                                    </td>
                                    <td align="center" style="font-weight: bold;">
                                        <xsl:value-of
                                                select="if (testng:testCaseMethodsCount(., '*') > 0) then format-number(testng:testCaseMethodsCount(., 'PASS') div testng:testCaseMethodsCount(., '*'), '###%') else '100%'"/>
                                    </td>
                                    <xsl:if test="compare($testNgXslt.showRuntimeTotals, 'true') = 0">
                                        <td align="center" nowrap="true">
                                            <xsl:value-of select="testng:methodsDuration(./class/test-method)"/>
                                        </td>
                                    </xsl:if>
                                </tr>
                            </xsl:for-each>
                        </table>
                        <br/>
                    </xsl:for-each>
                    <xsl:call-template name="powered-by"/>
                </body>
            </html>
        </xsl:result-document>

        <!-- Reporter output file -->
        <xsl:result-document href="{testng:absolutePath('reporterOutput.html')}" format="xhtml">
            <html xmlns="http://www.w3.org/1999/xhtml">
                <xsl:call-template name="htmlHead"/>
                <body>
                    <h2>Reporter output</h2>
                    <xsl:for-each select="reporter-output/line">
                        <div>
                            <code>
                                <xsl:value-of select="."/>
                            </code>
                        </div>
                    </xsl:for-each>
                    <xsl:call-template name="powered-by"/>
                </body>
            </html>
        </xsl:result-document>
    </xsl:template>

    <xsl:template name="navigationFile">
        <xsl:param name="suiteElements"/>
        <xsl:param name="reporterOutputElement"/>
        <xsl:result-document href="{testng:absolutePath('navigation.html')}" format="xhtml">
            <html xmlns="http://www.w3.org/1999/xhtml">
                <xsl:call-template name="htmlHead"/>
                <body>
                    <h2 style="margin-bottom: 5px;">
                        <xsl:value-of select="testng:getVariableSafe($testNgXslt.reportTitle, 'TestNG Results')"/>
                    </h2>
                    <div>
                        <a href="overview.html" target="content"
                           onclick="javscript:clearAllSelections();">Results overview
                        </a>
                    </div>
                    <div>
                        <a href="reporterOutput.html" target="content"
                           onclick="javscript:clearAllSelections();">Reporter output
                        </a>
                    </div>
                    <div>
                        <xsl:for-each select="$suiteElements">
                            <xsl:variable name="testCaseElements"
                                          select="if (test/@url) then document(test/@url)/test else test"/>
                            <table class="suiteMenuHeader" width="100%" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td nowrap="true">
                                        <b>
                                            <a href="{testng:suiteContentFileName(.)}" target="content"
                                               onclick="javscript:clearAllSelections();">
                                                <xsl:value-of select="@name"/>
                                            </a>
                                        </b>
                                        <div style="margin: 3px 0 3px 0;">
                                            <a href="{testng:suiteGroupsFileName(.)}" target="content"
                                               onclick="javscript:clearAllSelections();">
                                                <xsl:value-of select="count(./groups/group)"/>
                                                Groups
                                            </a>
                                        </div>
                                        <span style="color: red;">
                                            <xsl:value-of select="testng:suiteMethodsCount($testCaseElements, 'FAIL')"/>
                                        </span>
                                        /
                                        <span style="color: green;">
                                            <xsl:value-of select="testng:suiteMethodsCount($testCaseElements, 'PASS')"/>
                                        </span>
                                        /
                                        <span style="color: yellow;">
                                            <xsl:value-of select="testng:suiteMethodsCount($testCaseElements, 'SKIP')"/>
                                        </span>
                                        /
                                        <span>
                                            <xsl:value-of select="testng:suiteMethodsCount($testCaseElements, '*')"/>
                                        </span>
                                    </td>
                                    <td style="font-weight: bold;">
                                        <xsl:value-of
                                                select="format-number(testng:suiteMethodsCount($testCaseElements, 'PASS') div testng:suiteMethodsCount($testCaseElements, '*'), '###%')"/>
                                    </td>
                                </tr>
                            </table>
                            <xsl:call-template name="suiteContentFile">
                                <xsl:with-param name="suiteElement" select="."/>
                            </xsl:call-template>
                            <xsl:call-template name="suiteGroupsFile">
                                <xsl:with-param name="suiteElement" select="."/>
                            </xsl:call-template>
                            <xsl:call-template name="suiteTestCasesLinks">
                                <xsl:with-param name="testCases" select="$testCaseElements"/>
                            </xsl:call-template>
                            <xsl:call-template name="suiteTestCasesContentFiles">
                                <xsl:with-param name="testCases" select="$testCaseElements"/>
                            </xsl:call-template>
                        </xsl:for-each>
                    </div>
                </body>
            </html>
        </xsl:result-document>
    </xsl:template>

    <xsl:template name="suiteContentFile">
        <xsl:param name="suiteElement"/>
        <xsl:variable name="testCaseElements" select="if (test/@url) then document(test/@url)/test else test"/>
        <xsl:result-document href="{testng:absolutePath(testng:suiteContentFileName($suiteElement))}" format="xhtml">
            <html>
                <xsl:call-template name="htmlHead"/>
                <body>
                    <table width="100%" style="font-size: 16px; margin-bottom: 10px;" cellspacing="1">
                        <tr>
                            <td width="100%">
                                All methods in suite
                                <b>
                                    <xsl:value-of select="./@name"/>
                                </b>
                            </td>
                            <xsl:call-template name="percentageOverview">
                                <xsl:with-param name="failedCount"
                                                select="testng:suiteMethodsCount($testCaseElements, 'FAIL')"/>
                                <xsl:with-param name="passedCount"
                                                select="testng:suiteMethodsCount($testCaseElements, 'PASS')"/>
                                <xsl:with-param name="skippedCount"
                                                select="testng:suiteMethodsCount($testCaseElements, 'SKIP')"/>
                                <xsl:with-param name="totalCount"
                                                select="testng:suiteMethodsCount($testCaseElements, '*')"/>
                                <xsl:with-param name="totalDuration"
                                                select="testng:methodsDuration($testCaseElements/class/test-method)"/>
                            </xsl:call-template>
                        </tr>
                    </table>
                    <xsl:call-template name="testMethods">
                        <xsl:with-param name="classes" select="$testCaseElements/class"/>
                        <xsl:with-param name="failedMethods" select="$testCaseElements/class/test-method[@status='FAIL']"/>
                        <xsl:with-param name="passedMethods" select="$testCaseElements/class/test-method[@status='PASS']"/>
                        <xsl:with-param name="skipedMethods" select="$testCaseElements/class/test-method[@status='SKIP']"/>
                    </xsl:call-template>
                    <xsl:call-template name="powered-by"/>
                </body>
            </html>
        </xsl:result-document>
    </xsl:template>

    <xsl:template name="suiteGroupsFile">
        <xsl:param name="suiteElement"/>
        <xsl:result-document href="{testng:absolutePath(testng:suiteGroupsFileName($suiteElement))}" format="xhtml">
            <html xmlns="http://www.w3.org/1999/xhtml">
                <xsl:call-template name="htmlHead"/>
                <body>
                    <h2>
                        Groups for suite:
                        <b>
                            <xsl:value-of select="$suiteElement/@name"/>
                        </b>
                    </h2>
                    <xsl:for-each select="$suiteElement/groups/group">
                        <xsl:sort order="ascending" select="@name"/>
                        <table style="margin-bottom: 20px; font-size: 12px; width:100%;" cellpadding="3"
                               cellspacing="1">
                            <tr>
                                <td style="background-color: #f5f5f5;">
                                    <div style="font-size: 18px;">
                                        <xsl:value-of select="./@name"/>
                                    </div>
                                </td>
                            </tr>
                            <xsl:for-each select="method">
                                <tr>
                                    <td style="background-color: #eaf0f7;">
                                        <xsl:value-of select="@signature"/>
                                    </td>
                                </tr>
                            </xsl:for-each>
                        </table>
                    </xsl:for-each>
                    <xsl:call-template name="powered-by"/>
                </body>
            </html>
        </xsl:result-document>
    </xsl:template>

    <xsl:template name="testMethods">
        <xsl:param name="classes"/>
        <xsl:param name="failedMethods"/>
        <xsl:param name="passedMethods"/>
        <xsl:param name="skipedMethods"/>
        <xsl:param name="filePrefix"/>

        <div style="width: 200px;">
            <label for="groupMethodsCheckBox" style="font-weight: bold; margin: 0;">
                <input id="groupMethodsCheckBox" type="checkbox" onclick="switchTestMethodsView(this)">
                    <xsl:if test="testng:isFilterSelected('BY_CLASS') = 'true'">
                        <xsl:attribute name="checked" select="true"/>
                    </xsl:if>
                </input>
                Group by class
            </label>
            <br/>
            <label for="methodsFilter_ALL" style="font-weight: bold; margin: 0;">
                <input id="methodsFilter_ALL" type="checkbox" onclick="testMethodsFilterChanged(this, 'ALL')">
                    <xsl:if test="testng:isFilterSelected('FAIL') = 'true' and testng:isFilterSelected('PASS') = 'true' and testng:isFilterSelected('SKIP') = 'true' and testng:isFilterSelected('CONF') = 'true'">
                        <xsl:attribute name="checked" select="true"/>
                    </xsl:if>
                </input>
                All
            </label>
        </div>
        <label for="methodsFilter_FAIL" style="margin-left: 20px;">
            <input id="methodsFilter_FAIL" type="checkbox" onclick="testMethodsFilterChanged(this, 'FAIL')">
                <xsl:if test="testng:isFilterSelected('FAIL') = 'true'">
                    <xsl:attribute name="checked" select="true"/>
                </xsl:if>
            </input>
            Failed
        </label>
        <label for="methodsFilter_PASS">
            <input id="methodsFilter_PASS" type="checkbox" onclick="testMethodsFilterChanged(this, 'PASS')">
                <xsl:if test="testng:isFilterSelected('PASS') = 'true'">
                    <xsl:attribute name="checked" select="true"/>
                </xsl:if>
            </input>
            Passed
        </label>
        <label for="methodsFilter_SKIP">
            <input id="methodsFilter_SKIP" type="checkbox" onclick="testMethodsFilterChanged(this, 'SKIP')">
                <xsl:if test="testng:isFilterSelected('SKIP') = 'true'">
                    <xsl:attribute name="checked" select="true"/>
                </xsl:if>
            </input>
            Skipped
        </label>
        <label for="methodsFilter_CONF">
            <input id="methodsFilter_CONF" type="checkbox" onclick="testMethodsFilterChanged(this, 'CONF')">
                <xsl:if test="testng:isFilterSelected('CONF') = 'true'">
                    <xsl:attribute name="checked" select="true"/>
                </xsl:if>
            </input>
            Config
        </label>
        <br/>

        <!-- Display methods list grouped by status -->
        <div id="testMethodsByStatus">
            <xsl:if test="testng:isFilterSelected('BY_CLASS') = 'true'">
                <xsl:attribute name="style" select="'display: none;'"/>
            </xsl:if>
            <table class="testMethodsTable" cellpadding="0" cellspacing="0">
                <tr class="methodsTableHeader">
                    <td width="100%">Name</td>
                    <td nowrap="true">Started</td>
                    <td nowrap="true">Duration</td>
                    <td>Exception</td>
                </tr>
                <xsl:call-template name="testMethodsList">
                    <xsl:with-param name="methodList" select="$failedMethods"/>
                    <xsl:with-param name="category" select="'byStatus_failed'"/>
                </xsl:call-template>
                <xsl:call-template name="testMethodsList">
                    <xsl:with-param name="methodList" select="$passedMethods"/>
                    <xsl:with-param name="category" select="'byStatus_passed'"/>
                </xsl:call-template>
                <xsl:call-template name="testMethodsList">
                    <xsl:with-param name="methodList" select="$skipedMethods"/>
                    <xsl:with-param name="category" select="'byStatus_skiped'"/>
                </xsl:call-template>
            </table>
        </div>

        <!-- Display methods list grouped by class -->
        <div id="testMethodsByClass">
            <xsl:if test="testng:isFilterSelected('BY_CLASS') != 'true'">
                <xsl:attribute name="style" select="'display: none;'"/>
            </xsl:if>
            <xsl:for-each select="$classes">
                <xsl:sort order="ascending" select="@name"/>
                <table class="testMethodsTable" cellpadding="0" cellspacing="0">
                    <tr>
                        <td colspan="4">
                            <h3 style="display: inline;">
                                <xsl:value-of select="./@name"/>
                            </h3>
                        </td>
                    </tr>
                    <tr class="methodsTableHeader">
                        <td width="100%">Name</td>
                        <td nowrap="true">Started</td>
                        <td nowrap="true">Duration</td>
                        <td>Exception</td>
                    </tr>
                    <xsl:call-template name="testMethodsList">
                        <!--<xsl:with-param name="methodList" select="./test-method[not(@is-config)]"/>-->
                        <xsl:with-param name="methodList" select="./test-method"/>
                        <xsl:with-param name="category" select="'byClass'"/>
                        <xsl:with-param name="sortByStartTime" select="'true'"/>
                    </xsl:call-template>
                </table>
                <br/>
            </xsl:for-each>
        </div>
    </xsl:template>

    <xsl:template name="testMethodsList">
        <xsl:param name="methodList"/>
        <xsl:param name="category"/>
        <xsl:param name="sortByStartTime"/>
        <xsl:for-each select="$methodList">
            <xsl:sort order="ascending" select="if (compare($sortByStartTime, 'true') = 0) then @started-at else ''"/>
            <xsl:variable name="methodId" select="concat(../@name, '_', @name, '_', $category, '_', @status, position())"/>
            <xsl:variable name="detailsId" select="concat($methodId, '_details')"/>
            <xsl:variable name="exceptionDetailsId" select="concat($methodId, '_exception')"/>
            <tr id="{concat($methodId, '_row')}" class="{testng:testMethodStatus(.)}">
                <xsl:if test="testng:isFilterSelected(@status) != 'true'">
                    <!--<xsl:attribute name="style" select="'display: none;'"/>-->
                </xsl:if>
                <td width="100%" class="firstMethodCell">
                    <a onclick="toggleDetailsVisibility('{$detailsId}')">
                        <xsl:value-of select="concat(@name, '(', testng:trim(testng:concatParams(./params/param)), ')')"/>
                    </a>
                </td>
                <td nowrap="true">
                    <xsl:value-of select="substring(@started-at, 12, 8)"/>
                </td>
                <td nowrap="true" align="right">
                    <xsl:value-of select="testng:formatDuration(@duration-ms)"/>
                </td>
                <td nowrap="true">
                    <xsl:if test="./exception">
                        <a onclick="toggleDetailsVisibility('{$exceptionDetailsId}')">
                            <xsl:value-of select="concat(exception/@class, ': ', exception/message)"/>
                        </a>
                    </xsl:if>
                    &#160;
                </td>
            </tr>
            <tr>
                <td colspan="4" style="padding: 0; background-color: white; border-style: none; height: 0px;">
                    <div id="{$detailsId}" class="testMethodDetails">
                        <xsl:call-template name="formField">
                            <xsl:with-param name="label" select="'Name'"/>
                            <xsl:with-param name="value" select="@name"/>
                        </xsl:call-template>
                        <xsl:call-template name="formField">
                            <xsl:with-param name="label" select="'Description'"/>
                            <xsl:with-param name="value" select="@description"/>
                        </xsl:call-template>
                        <xsl:call-template name="formField">
                            <xsl:with-param name="label" select="'Signature'"/>
                            <xsl:with-param name="value" select="@signature"/>
                        </xsl:call-template>
                        <xsl:if test="./params">
                            <xsl:call-template name="formField">
                                <xsl:with-param name="label" select="'Parameters'"/>
                                <xsl:with-param name="value"
                                                select="testng:concatParams(./params/param)"/>
                            </xsl:call-template>
                        </xsl:if>
                        <xsl:call-template name="formField">
                            <xsl:with-param name="label" select="'Start time'"/>
                            <xsl:with-param name="value" select="substring(@started-at, 12, 8)"/>
                        </xsl:call-template>
                        <xsl:call-template name="formField">
                            <xsl:with-param name="label" select="'End time'"/>
                            <xsl:with-param name="value" select="substring(@finished-at, 12, 8)"/>
                        </xsl:call-template>
                        <xsl:call-template name="formField">
                            <xsl:with-param name="label" select="'Duration'"/>
                            <xsl:with-param name="value" select="testng:formatDuration(@duration-ms)"/>
                        </xsl:call-template>
                        <xsl:call-template name="formField">
                            <xsl:with-param name="label" select="'In groups:'"/>
                            <xsl:with-param name="value" select="@groups"/>
                        </xsl:call-template>
                        <xsl:if test="@depends-on-methods">
                            <xsl:call-template name="formFieldList">
                                <xsl:with-param name="label" select="'Depends on methods'"/>
                                <xsl:with-param name="value"
                                                select="tokenize(@depends-on-methods, ',')"/>
                            </xsl:call-template>
                        </xsl:if>
                        <xsl:if test="@depends-on-groups">
                            <xsl:call-template name="formFieldList">
                                <xsl:with-param name="label" select="'Depends on groups'"/>
                                <xsl:with-param name="value"
                                                select="tokenize(@depends-on-groups, ',')"/>
                            </xsl:call-template>
                        </xsl:if>
                    </div>
                </td>
            </tr>
            <tr>
                <xsl:if test="exception">
                    <td colspan="4" style="padding: 0; background-color: white; border-style: none; height: 0px;">
                        <div id="{$exceptionDetailsId}" class="testMethodDetails">
                            <xsl:choose>
                                <xsl:when test="exception/full-stacktrace">
                                    <pre style="padding: 5px; margin: 0;">
                                        <xsl:value-of select="testng:trim(exception/full-stacktrace)"/>
                                    </pre>
                                </xsl:when>
                                <xsl:when test="exception/short-stacktrace and not (exception/full-stacktrace)">
                                    <pre style="padding: 5px; margin: 0;">
                                        <xsl:value-of select="testng:trim(exception/short-stacktrace)"/>
                                    </pre>
                                </xsl:when>
                                <xsl:otherwise>
                                    <pre style="padding: 5px; margin: 0;">&lt;No stacktrace information&gt;</pre>
                                </xsl:otherwise>
                            </xsl:choose>
                        </div>
                    </td>
                </xsl:if>
            </tr>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="suiteTestCasesLinks">
        <xsl:param name="testCases"/>
        <xsl:for-each select="$testCases">
            <xsl:sort order="ascending" select="if (compare($testNgXslt.sortTestCaseLinks, 'true') = 0) then @name else ''"/>
            <div class="testCaseLink"
                 onclick="javscript:selectTestCaseLink(this); parent.content.location='{testng:testCaseContentFileName(.)}'">
                <div class="{if (count(./class/test-method[@status='FAIL']) > 0)
                                then 'testCaseFail'
                                else if ((count(./class/test-method[@status='FAIL']) = 0) and (count(./class/test-method[@status='PASS']) > 0))
                                    then 'testCasePass'
                                    else 'testCaseSkip'}">
                </div>
                <xsl:value-of select="@name"/>
            </div>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="suiteTestCasesContentFiles">
        <xsl:param name="testCases"/>
        <xsl:for-each select="$testCases">
            <xsl:result-document href="{testng:absolutePath(testng:testCaseContentFileName(.))}" format="xhtml">
                <html>
                    <xsl:call-template name="htmlHead"/>
                    <body>
                        <table width="100%" style="font-size: 16px; margin-bottom: 10px;" cellspacing="1">
                            <tr>
                                <td width="100%">
                                    Test case
                                    <b>
                                        <xsl:value-of select="./@name"/>
                                    </b>
                                </td>
                                <xsl:call-template name="percentageOverview">
                                    <xsl:with-param name="failedCount" select="testng:testCaseMethodsCount(., 'FAIL')"/>
                                    <xsl:with-param name="passedCount" select="testng:testCaseMethodsCount(., 'PASS')"/>
                                    <xsl:with-param name="skippedCount"
                                                    select="testng:testCaseMethodsCount(., 'SKIP')"/>
                                    <xsl:with-param name="totalCount" select="testng:testCaseMethodsCount(., '*')"/>
                                    <xsl:with-param name="totalDuration"
                                                    select="testng:methodsDuration(./class/test-method)"/>
                                </xsl:call-template>
                            </tr>
                        </table>
                        <xsl:call-template name="testMethods">
                            <xsl:with-param name="classes" select="./class"/>
                            <xsl:with-param name="failedMethods" select="./class/test-method[@status='FAIL']"/>
                            <xsl:with-param name="passedMethods" select="./class/test-method[@status='PASS']"/>
                            <xsl:with-param name="skipedMethods" select="./class/test-method[@status='SKIP']"/>
                        </xsl:call-template>
                        <xsl:call-template name="powered-by"/>
                    </body>
                </html>
            </xsl:result-document>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="percentageOverview">
        <xsl:param name="failedCount"/>
        <xsl:param name="passedCount"/>
        <xsl:param name="skippedCount"/>
        <xsl:param name="totalCount"/>
        <xsl:param name="totalDuration"/>
        <td style="background-color: #FFBBBB; padding: 3px 3px 3px 0;" align="center">
            <div style="width: 50px;">
                <xsl:value-of select="$failedCount"/>
            </div>
        </td>
        <td style="background-color: lightgreen; padding: 3px 3px 3px 0;" align="center">
            <div style="width: 50px;">
                <xsl:value-of select="$passedCount"/>
            </div>
        </td>
        <td style="background-color: #FFFFBB; padding: 3px 3px 3px 0;" align="center">
            <div style="width: 50px;">
                <xsl:value-of select="$skippedCount"/>
            </div>
        </td>
        <td align="center" style="background-color: #eaf0f7; padding: 3px 3px 3px 0;">
            <div style="width: 50px;">
                <xsl:value-of select="$totalCount"/>
            </div>
        </td>
        <td align="center" style="font-weight: bold; background-color: #eaf0f7; padding: 3px 3px 3px 0;">
            <div style="width: 50px;">
                <xsl:value-of
                        select="if ($totalCount > 0) then format-number($passedCount div $totalCount, '###%') else '100%'"/>
            </div>
        </td>
        <xsl:if test="compare($testNgXslt.showRuntimeTotals, 'true') = 0">
            <td style="background-color: #eaf0f7; padding: 3px 3px 3px 0;" align="center" nowrap="true">
                <xsl:value-of select="$totalDuration"/>
            </td>
        </xsl:if>
    </xsl:template>

    <xsl:template name="powered-by">
        <div style="margin-top: 15px; color: gray; text-align: center; font-size: 9px;">
            Generated with
            <a href="http://code.google.com/p/testng-xslt/" style="color: #8888aa;" target="_blank">
                TestNG XSLT
            </a>
        </div>
    </xsl:template>

</xsl:stylesheet>
