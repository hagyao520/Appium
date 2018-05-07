
            
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
            
        