package fastreflect;

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
		try {
			String name = cls.getName() + "$ConstructorBean";
			ClassPool cpool = ClassPool.getDefault(); 
			cpool.insertClassPath(new ClassClassPath(cls));
	        CtClass cc = cpool.makeClass(name, cpool.get(ConstructorBean.class.getName()));
	        StringBuilder builder = new StringBuilder();
	        builder.append("public Object newInstance(){try{ return new ");
	        builder.append(cls.getName());
	        builder.append("();}catch(Exception e){throw new RuntimeException(e);}}");
	        System.out.println(builder);
	        CtMethod m =  CtMethod.make(builder.toString(), cc);
			cc.addMethod(m);
			Class<?> dynamicCls =  MyClassLoader.getLoader(cls).loadMyClass(name, cc.toBytecode());
			return (ConstructorBean) dynamicCls.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public abstract Object newInstance();
}
