package project.entities;

import java.util.ArrayList;
import java.util.List;

public class Order {

	private int id;
	private int userId;
	private String state;
	private List<Integer> cart;
	
	public Order() {
		cart = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Integer> getCart() {
		return cart;
	}

	public void setCart(List<Integer> cart) {
		this.cart = cart;
	}
}
