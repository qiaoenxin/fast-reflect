package fastreflect;


import fastreflect.Bean.InnerClass;
import fastreflect.Bean.InnerClass.IIClass;
import fastreflect.MethodBean.MethodHandler;



public class TestCase {
	
	public static void main(String[] args) throws Throwable{
		Bean bean = new Bean();
		InnerClass innerClass = new InnerClass();
		innerClass.setName("inner");
		bean.setName("wo cao");
		MethodBean mBean = MethodBean.getMethodBean(bean.getClass());
		mBean = MethodBean.getMethodBean(bean.getClass());
		
		ConstructorBean cBean = ConstructorBean.getConstructorBean(bean.getClass());
		Bean newBean = (Bean) cBean.newInstance();
		newBean.setName("13");
		MethodHandler[] md = mBean.getMethodByName("setIntegerArray");
		md[0].invoke(bean, new Object[]{null, 1});
		
		MethodBean innerBeanM = MethodBean.getMethodBean(innerClass.getClass());
		md =innerBeanM.getMethodByName("getName");
		Object o = md[0].invoke(innerClass, null);
		
		
		ConstructorBean innerBeanC = ConstructorBean.getConstructorBean(innerClass.getClass());
		o =  innerBeanC.newInstance();
		System.out.println(o);
	}
	
	static class MyLoader extends ClassLoader{
		
	}
}
