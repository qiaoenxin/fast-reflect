package fastreflect;

import java.lang.reflect.Modifier;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;


/**
 * 
 * @author exqiao
 */
public abstract class ConstructorBean {
	
	public static ConstructorBean getConstructorBean(Class<?> cls){
		
		String fileName = cls.getName() + "__ConstructorBean";
		MyClassLoader loader = MyClassLoader.getLoader(cls);
		try {
			Class<?> methodCls = loader.loadClass(fileName);
			return (ConstructorBean) methodCls.newInstance();
		} catch (ClassNotFoundException e1) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		try {
			if(!Modifier.isPublic(cls.getModifiers())){
				throw new RuntimeException(cls.getName() + " is not public.");
			}
			if(cls.isMemberClass() && !Modifier.isStatic(cls.getModifiers())){
				throw new RuntimeException(cls.getName() + " is not static class.");
			}
			
			
			String className = cls.getName();
//			if(cls.isMemberClass()){
//				className = cls.getName().replaceAll("\\$", ".");
//			}
			ClassPool cpool = ClassPool.getDefault(); 
			cpool.insertClassPath(new ClassClassPath(cls));
	        CtClass cc = cpool.makeClass(fileName, cpool.get(ConstructorBean.class.getName()));
	        StringBuilder builder = new StringBuilder();
	        builder.append("public Object newInstance(){try{ return new ");
	        builder.append(className);
	        builder.append("();}catch(Exception e){throw new RuntimeException(e);}}");
//	        System.out.println(builder);
	        CtMethod m =  CtMethod.make(builder.toString(), cc);
			cc.addMethod(m);
			Class<?> dynamicCls =  loader.loadMyClass(fileName, cc.toBytecode());
			return (ConstructorBean) dynamicCls.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public abstract Object newInstance();
}
