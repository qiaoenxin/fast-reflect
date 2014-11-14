package fastreflect;

import java.util.ArrayList;
import java.util.List;


public class MyClassLoader extends ClassLoader{
	
	private static List<MyClassLoader> LOADERS = new ArrayList<MyClassLoader>();
	
	public static MyClassLoader getLoader(Class<?> cls){
		synchronized (LOADERS) {
			ClassLoader parent = cls.getClassLoader();
			for(MyClassLoader loader : LOADERS){
				if(loader.getParent() == parent){
					return loader;
				}
			}
			MyClassLoader loader = new MyClassLoader(parent);
			LOADERS.add(loader);
			return loader;
		}
	}
	
	public MyClassLoader(ClassLoader parent) {
		super(parent);
	}

	public Class<?> loadMyClass(String name, byte[] byts){
		return defineClass(name, byts, 0, byts.length);
	}
}
