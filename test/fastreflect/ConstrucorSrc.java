package fastreflect;

public class ConstrucorSrc extends ConstructorBean {

	public Object newInstance() {
		try {
			return new fastreflect.Bean();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
