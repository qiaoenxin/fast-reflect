package fastreflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;


/**
 * 
 * @author mac
 *
 */
public abstract class MethodBean {
	
	private List<Method> methods;
	
	public MethodBean() {
		super();
	}

	public static MethodBean getMethodBean(Class<?> clsa){
		List<Method> methods = new ArrayList<Method>();
		Class<?> cls = clsa;
		while(true){
			Method[] mths =  cls.getDeclaredMethods();
			if(mths != null){
				for(Method m : mths){
					if(Modifier.isPublic(m.getModifiers()) && !contains(m, methods)){
						methods.add(m);
					}
				}
			}
			cls = cls.getSuperclass();
			if(Object.class.equals(cls)){
				break;
			}
		}
		MethodBean bean = buildClass(clsa, methods);
		bean.methods = methods;
		return bean;
	}
	
	public MethodHandler getMethod(String name, Class<?>[] parameterTypes){
		parameterTypes = parameterTypes == null ? new Class<?>[0] : parameterTypes;
		for(int i = 0, length = methods.size(); i < length; i++){
			Method m = methods.get(i);
			if(m.getName().equals(name)
					&Arrays.equals(m.getParameterTypes(), parameterTypes)){
				
				return new MethodHandler(i);
			}
		}
		throw new RuntimeException("The method not found : " + name);
	}
	
	
	
	public MethodHandler[] getMethodByName(String name){
		List<MethodHandler> list = new ArrayList<MethodBean.MethodHandler>();
		for(int i = 0, length = methods.size(); i < length; i++){
			Method m = methods.get(i);
			if(m.getName().equals(name)){
				list.add( new MethodHandler(i));
			}
		}
		return list.toArray(new MethodHandler[list.size()]);
	}
	
	private static boolean contains(Method m, List<Method> methods) {
		for(Method mthd : methods){
			if(m.getName().equals(mthd.getName())
					&Arrays.equals(m.getParameterTypes(), mthd.getParameterTypes())){
				return true;
			}
		}
		return false;
	}

	private static MethodBean buildClass(Class<?> cls, List<Method> methods2) {
		try {
			String name = cls.getName() + "$MethodBean";
			ClassPool cpool = ClassPool.getDefault(); 
			cpool.insertClassPath(new ClassClassPath(cls));
	        CtClass cc = cpool.makeClass(name, cpool.get(MethodBean.class.getName()));
	        StringBuilder builder = new StringBuilder();
	        builder.append("protected  Object invoke(Object obj, int id,  Object[] params) throws Throwable{\r\n");
	        builder.append("if(obj == null) throw new NullPointerException();\r\n");
	        builder.append(cls.getName()).append(" bean = (").append(cls.getName()).append(") obj;\r\n");
	        builder.append("Object result = null;\r\n");
	        builder.append("switch (id) {\r\n");
	        for (int i = 0, length = methods2.size(); i < length; i++) {
	        	Method m = methods2.get(i);
	        	builder.append("case ").append(String.valueOf(i)).append(":");
	        	
	        	StringBuilder builder2 = new StringBuilder();
	        	builder2.append("bean.").append(m.getName()).append("(");
	        	Class<?>[] types = m.getParameterTypes();
	        	for (int j = 0; j < types.length; j++) {
	        		if(j > 0){
	        			builder2.append(", ");
	        		}
	        		Class<?> type = types[j];
	        		builder2.append(changeTypeToBasic(type, "params[" + String.valueOf(j) + "]"));
				}
	        	builder2.append(")");
	        	
	        	if(!m.getReturnType().getName().equals("void")){
	        		builder.append("result=");
	        		String value = changeTypeToObject(m.getReturnType(), builder2.toString());
	        		builder.append(value);
	        	}else{
	        		builder.append(builder2);
	        	}
	        	
	        	
	        	builder.append(";");
	        	builder.append("break;\r\n");
			}
	        
	        builder.append("default: break;}\r\n" + 
	        "return result;}");
	        System.out.println(builder);
	        CtMethod m = CtMethod.make(builder.toString(), cc);
			cc.addMethod(m);
			
			Class<?> dynamicCls =  MyClassLoader.getLoader(cls).loadMyClass(name, cc.toBytecode());
			return (MethodBean) dynamicCls.newInstance();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static String changeTypeToBasic(Class<?> type, String value) {
		if(type.isArray()){
			return castArray(type, value);
		}
		if(type == int.class) return "((Integer)"+ value + ").intValue()";
		if(type == short.class) return "((Short)"+ value + ").shortValue()";
		if(type == char.class) return "((Character)"+ value + ").charValue()";
		if(type == long.class) return "((Long)"+ value + ").longValue()";
		if(type == byte.class) return "((Byte)"+ value + ").byteValue()";
		if(type == boolean.class) return "((Boolean)"+ value + ").booleanValue()";
		if(type == float.class) return "((Float)"+ value + ").floatValue()";
		if(type == double.class) return "((Double)"+ value + ").doubleValue()";
		
		
		return "(" + type.getName() + ")" + value;
	}
	
	private static String castArray(Class<?> type, String value) {
		StringBuilder builder = new StringBuilder();
		while(true){
			if(type.isArray()){
				builder.append("[]");
				type = type.getComponentType();
			}else{
				break;
			}
		}
		
		if(type == int.class) return "(int"+builder+")"+ value;
		if(type == short.class) return "(short"+builder+")"+ value;
		if(type == char.class) return "(char"+builder+")"+ value;
		if(type == long.class) return "(long"+builder+")"+ value;
		if(type == byte.class) return "(byte"+builder+")"+ value;
		if(type == boolean.class) return "(boolean"+builder+")"+ value;
		if(type == float.class) return "(float"+builder+")"+ value;
		if(type == double.class) return "(double"+builder+")"+ value;
		
		return "(" + type.getName() + builder + ")" + value;
	}

	private static String changeTypeToObject(Class<?> type, String value) {
		if(type == int.class) return "Integer.valueOf("+ value + ")";
		if(type == short.class) return "Short.valueOf("+ value + ")";
		if(type == char.class) return "Character.valueOf("+ value + ")";
		if(type == long.class) return "Long.valueOf("+ value + ")";
		if(type == byte.class) return "Byte.valueOf("+ value + ")";
		if(type == boolean.class) return "Boolean.valueOf("+ value + ")";
		if(type == float.class) return "Float.valueOf("+ value + ")";
		if(type == double.class) return "Double.valueOf("+ value + ")";
		
		return value;
	}

	protected abstract Object invoke(Object obj, int id,  Object[] params) throws Throwable;
	
	public class MethodHandler {
		private int index;
		public MethodHandler(int index) {
			super();
			this.index = index;
		}
		public Object invoke(Object obj, Object[] params) throws Throwable{
			return MethodBean.this.invoke(obj, index, params);
		}
	}
}