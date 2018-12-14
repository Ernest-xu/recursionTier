package text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
/**
 * 递归创建层级目录
 * 根据你想要的数据名称来展现
 * @author Ernest
 *
 */
public class recursionTier {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		String newID = UUID.randomUUID().toString();
		Map<String, Object> parentMap = new HashMap<String, Object>();
		parentMap.put("sID", newID);
		parentMap.put("sName", "项目组织");
		parentMap.put("sParentID",null);
		parentMap.put("sMd5Str", "");
		list.add(parentMap);
		Map<String, Object> map = new HashMap<String, Object>();//区分list中需要的字段
		String sID = "sID";
		String sName = "sName";
		String fProjectQuanName = "fProjectQuanName";
		String sParentID = newID;
		String sMd5Str1 = "";
		map.put("sID", sID);
		map.put("sName", sName);
		map.put("fProjectQuanName", fProjectQuanName);
		map.put("sParentID", sParentID);
		map.put("sMd5Str", sMd5Str1);
		list.add(map);
		Map<String, String> map1= new HashMap<String, String>();//将字段重新命名
		map1.put("id", "sID");
		map1.put("label", "sName");
		map1.put("fProjectQuanName", "fProjectQuanName");
		map1.put("sParentID", "sParentID");
		map1.put("sMd5Str", "sMd5Str");
		Map<String, String> Parentmap= new HashMap<String, String>();//重新命名后关联的字段key是外键，value为主键
		Parentmap.put("sParentID", "id");
		//list为你要操作的数据,
		int startfloor = 1;//开始的层级重1开始
		int  depth= 2;//深度展现的深度（超出会自动停止）
		Object str = null;//对应开始层级的外键
		List<Map<String, Object>> list2= getChildren(list, map1, Parentmap, startfloor, depth, str);
		for(int i = 0;i<list2.size();i++){
			System.out.println(list2.get(i).toString());
		}

	}
	
	
	
	/**
	 * 获取层级的递归方法
	 * 
	 * @param list 要处理的数据List
	 * @param map 返回的数据key:返回的名称value：数据的名称
	 * @param Parentmap 父子关联key：父value：子
	 * @param number 填1对应obj为null,大于1时填Parentmap当前关联的子一般为当前行主键
	 * @param tier 与number的差值为层数(注：tier>number)
	 * @param obj 当前层的关联（子）和number有关
	 * @return 返回List
	 * 
	 */
	 
	public static List<Map<String, Object>> getChildren(List<Map<String, Object>> list,Map<String ,String> map,Map<String ,String> Parentmap,int number,int tier,Object obj){
		List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
		int number1=number+1;
		boolean falge = true;
		if(list==null){
			falge=false;
		}
		if(map==null){
			falge=false;
		}
		if(Parentmap==null){
			falge=false;
		}
		if(number<0){
			falge=false;
		}
		if(tier<0){
			falge=false;
		}
		if(number>tier){
			falge=false;
		}
		if(falge){
			Object ParentID=null;
			Object ID = null;
			String a=null;
			for(String key:Parentmap.keySet()){
				a=key;
			}
			for(int i = 0;i<list.size();i++){
				Map<String ,Object> map2 = new HashMap<String, Object>();
					for(String key : map.keySet()){
						map2.put(key,list.get(i).get(map.get(key)));
					}
					ParentID=map2.get(a);
					ID = map2.get(Parentmap.get(a));
				if(number==1){
					
					if(ParentID==null||"".equals(ParentID)){
						List<Map<String, Object>> children=null;
						Map<String,Object> map1 = new HashMap<String, Object>(); 
						if(tier>=number1){
							children = getChildren(list,map,Parentmap,number1,tier,ID);
						}
						for(String key : map.keySet()){
							if("sName".equals(key)){
								map1.put(key,map2.get(key)==null?"":map2.get(key));
							}else{
								map1.put(key,map2.get(key));
							}
						}
						map1.put("children", children);
						list1.add(map1);
					}
				}else{
					
					if(obj.equals(ParentID)){
						Map<String,Object> map1 = new HashMap<String, Object>(); 
						List<Map<String, Object>> children=null;
						if(tier>=number1){
							children = getChildren(list,map,Parentmap,number1,tier,ID);
						}
						
						for(String key : map.keySet()){
							if("sName".equals(key)){
								map1.put(key,map2.get(key)==null?"":map2.get(key));
							}else{
								map1.put(key,map2.get(key));
							}
						}
						map1.put("children", children);
						list1.add(map1);
					}
				}
				
			};
		}
		
		return list1;
	}; 
	
	
}
