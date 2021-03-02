package application;

public class LibraryObject 
{
	private String m_title = "";
	private String m_publisher = "";
	private int m_qtyTotal = 0;
	private int m_qtyOnHand = 0;
	
	
	//accessor and modifier functions
	public int getM_qtyTotal() {
		return m_qtyTotal;
	}
	public void setM_qtyTotal(int m_qtyTotal) {
		this.m_qtyTotal = m_qtyTotal;
	}
	public int getM_qtyOnHand() {
		return m_qtyOnHand;
	}
	public void setM_qtyOnHand(int m_qtyOnHand) {
		this.m_qtyOnHand = m_qtyOnHand;
	}
	public String getM_title() {
		return m_title;
	}
	public void setM_title(String m_title) {
		this.m_title = m_title;
	}
	public String getM_publisher() {
		return m_publisher;
	}
	public void setM_publisher(String m_publisher) {
		this.m_publisher = m_publisher;
	}
	
	
}
