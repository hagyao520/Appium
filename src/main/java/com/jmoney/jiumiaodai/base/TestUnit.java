package com.jmoney.jiumiaodai.base;

import java.util.LinkedHashMap;

/**
 * <br>对应于XML文件中的unit元素</br>
 *
 * @version 1.0
 * @since   1.0
 */
public class TestUnit extends TestBase{
	
	private LinkedHashMap<String,TestCase> caseMap;

    public LinkedHashMap<String,TestCase> getCaseMap() {
        return caseMap;
    }

    public void setCaseMap(LinkedHashMap<String,TestCase> caseMap) {
        this.caseMap = caseMap;
    }

	@Override
	public String toString() {
		return "TestUnit [caseMap=" + caseMap + "]";
	}
}
