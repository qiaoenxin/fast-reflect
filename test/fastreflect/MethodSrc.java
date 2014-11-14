package fastreflect;

public class MethodSrc extends MethodBean {
	protected  Object invoke(Object obj, int id,  Object[] params) throws Throwable{
		if(obj == null) throw new NullPointerException();
		fastreflect.Bean bean = (fastreflect.Bean) obj;
		Object result = null;
		switch (id) {
		case 0:result=bean.getName();break;
		case 1:bean.setName((java.lang.String)params[0]);break;
		case 2:result=Integer.valueOf(bean.getAge());break;
		case 3:bean.setAge(((Integer)params[0]).intValue());break;
		case 4:result=Byte.valueOf(bean.getByteType());break;
		case 5:bean.setByteType(((Byte)params[0]).byteValue());break;
		case 6:result=Boolean.valueOf(bean.isBool());break;
		case 7:bean.setBool(((Boolean)params[0]).booleanValue());break;
		case 8:result=Float.valueOf(bean.getFloatType());break;
		case 9:bean.setFloatType(((Float)params[0]).floatValue());break;
		case 10:result=Double.valueOf(bean.getDoub());break;
		case 11:bean.setDoub(((Double)params[0]).doubleValue());break;
		case 12:result=Long.valueOf(bean.getLongType());break;
		case 13:bean.setLongType(((Long)params[0]).longValue());break;
		case 14:result=Short.valueOf(bean.getShortType());break;
		case 15:bean.setShortType(((Short)params[0]).shortValue());break;
		case 16:result=Character.valueOf(bean.getCharType());break;
		case 17:bean.setCharType(((Character)params[0]).charValue());break;
		case 18:result=bean.getIntArray();break;
		case 19:bean.setIntArray((int[])params[0]);break;
		case 20:result=bean.getLongAry();break;
		case 21:bean.setLongAry((long[][])params[0]);break;
		case 22:result=bean.getIntegerArray();break;
		case 23:bean.setIntegerArray((java.lang.Integer[][][])params[0], ((Integer)params[1]).intValue());break;
		default: break;}
		return result;
	}
}
