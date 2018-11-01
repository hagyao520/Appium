package AutomationTestSystem.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>TODO(描述该类的作用)</br>
 *
 * @author  102051
 * @email   email
 * @date    2017年7月28日 上午11:03:31
 * @version 1.0
 * @since   1.0
 */
public class maintest {
	public static Map<String,Object> localmap = AppiumUtil.localmap;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws Exception{
		List<Map<String, Object>> st = new ArrayList<>();
		Map<String,Object> map = new HashMap();
		map.put("A", "1");
		map.put("B", "2");
		
		Map<String,Object> map1 = new HashMap();
		map1.put("C", "3");
		map1.put("D", "4");
		
		Map<String,Object> map2 = new HashMap();
		map2.put("E", "3");
		map2.put("F", "4");
		map2.put("G", map1);
		
		st.add(map);
		st.add(map2);
		
		List<String> p = new ArrayList<>();
		p.add("5");
		p.add("6");
		
		localmap.put("RR", p);
		localmap.put("SS", "ok");
		localmap.put("TT",st);
		
		System.out.println(AppiumUtil.parseStringHasEls("rr[1]}"));
		System.out.println(AppiumUtil.parseStringHasEls("${rr[1]}"));
		System.out.println(AppiumUtil.parseStringHasEls("${ss}"));
		System.out.println(AppiumUtil.parseEL("${tt[0]}"));
		System.out.println(AppiumUtil.parseEL("${tt}"));
		System.out.println(AppiumUtil.parseEL("${tt[0].a}"));
		System.out.println(AppiumUtil.parseEL("${tt.g.c}"));
	}
}