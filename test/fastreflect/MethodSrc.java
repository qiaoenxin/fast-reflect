package fastreflect;

public class MethodSrc extends MethodBean {
	protected  Object invoke(Object obj, int id,  Object[] params) throws Throwable{
		if(obj == null) throw new NullPointerException();
		fastreflect.Bean.InnerClass bean = (fastreflect.Bean.InnerClass) obj;
		Object result = null;
		switch (id) {
		case 0:result=bean.getName();break;
		case 1:bean.setName((java.lang.String)params[0]);break;
		default: break;}
		return result;}
}
