package c231010;

public class HobbyBean {

	private String name;
	private int num;
	
public HobbyBean(){} //꼭 하나는 필요하다
	
	public HobbyBean(String name){
		this.name = name;
	}
	
	public void setName(String name) { 
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setNum(int num) { 
		this.num = num;
	}
	public int getNumber() {
		return num;
	}
	
}