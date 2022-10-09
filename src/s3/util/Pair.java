package s3.util;

public class Pair<T1,T2> {
	public T1 m_a;
	public T2 m_b;
	public Pair(T1 a,T2 b) {
		m_a = a;
		m_b = b;
	}

	public T1 getX(){
		return m_a;
	}

	public T2 getY(){
		return m_b;
	}

	public void setX(T1 a){
		m_a = a;
	}

	public void setY(T2 b){
		m_b = b;
	}
}
