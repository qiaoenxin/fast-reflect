package fastreflect;


import fastreflect.MethodBean.MethodHandler;



public class TestCase {
	
	public static void main(String[] args) throws Throwable{
		Bean bean = new Bean();
		bean.setName("wo cao");
		MethodBean mBean = MethodBean.getMethodBean(bean.getClass());
		
		ConstructorBean cBean = ConstructorBean.getConstructorBean(bean.getClass());
		Bean newBean = (Bean) cBean.newInstance();
		newBean.setName("13");
		Object obj = mBean.invoke(bean, 0, null);
		MethodHandler[] md = mBean.getMethodByName("setIntegerArray");
		System.out.println(obj);
		md[0].invoke(bean, new Object[]{null, 1});
//		System.out.println(mBean);
	}
	
	static class MyLoader extends ClassLoader{
		
		
	}
}
