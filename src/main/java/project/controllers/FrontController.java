package project.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import project.controllers.commands.CommandController;

@Controller
public class FrontController {
	
	private CommandController command;
	
	@Autowired
	public FrontController(CommandController command) {
		this.command = command;
	}
	
	@GetMapping("/home")
	public String getHome() {
		return "home";
	}
	
	@GetMapping("/catalog")
	public String getCatalog(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			command.execute("buildCatalog", request, response, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "catalog";
	}
	
	@GetMapping("/catalog_next")
	public void getNextPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("change", 1);
		command.execute("catalogChangePage", request, response, model);
	}
	
	@GetMapping("/catalog_prev")
	public void getPrevPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("change", -1);
		command.execute("catalogChangePage", request, response, model);
	}
	
	@GetMapping("/product")
	public String getProduct(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("buildProduct", request, response, model);
		return "product";
	}
	
	@GetMapping("/sign_in")
	public String getSignIn() {
		return "sign_in";
	}
	
	@GetMapping("/sign_up")
	public String getSignUp() {
		return "sign_up";
	}
	
	@GetMapping("/delivery")
	public String getDelivery() {
		return "delivery";
	}
	
	@GetMapping("/about")
	public String getAbout() {
		return "about";
	}
	
	@GetMapping("/contacts")
	public String getContacts() {
		return "contacts";
	}
	
	@GetMapping("/profile")
	public String getProfile() {
		return "profile";
	}
	
	@GetMapping("/cart")
	public String getCart(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("buildCart", request, response, model);
		return "cart";
	}
	
	@GetMapping("/order_conflict")
	public String getOrderConflict(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("buildOrderConflict", request, response, model);
		return "order_conflict";
	}
	
	@GetMapping("/admin_catalog")
	public String getAdminCatalog(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("buildAdminCatalog", request, response, model);
		return "admin_catalog";
	}
	
	@GetMapping("/admin_product")
	public String getAdminProduct(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("buildAdminProduct", request, response, model);
		return "admin_product";
	}
	
	@GetMapping("/admin_users")
	public String getAdminUsers(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("buildAdminUsers", request, response, model);
		return "admin_users";
	}
	
	@GetMapping("/admin_orders")
	public String getAdminOrders(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("buildAdminOrders", request, response, model);
		return "admin_orders";
	}
	
	@PostMapping("/sign_in")
	public void signIn(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("signIn", request, response, model);
	}
	
	@PostMapping("/sign_up")
	public void signUp(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("signUp", request, response, model);
	}
	
	@PostMapping("/sign_out")
	public void signOut(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("signOut", request, response, model);
	}
	
	@PostMapping("/add_product")
	public void addProduct(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("orderAddProduct", request, response, model);
	}
	
	@PostMapping("/remove_product")
	public void removeProduct(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("orderRemoveProduct", request, response, model);
	}
	
	@PostMapping("/confirm_order")
	public void confirmOrder(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("congirmOrder", request, response, model);
	}
	
	@PostMapping("/manage_order_conflict")
	public void manageConflict(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("manageOrderConflict", request, response, model);
	}
	
	@PostMapping("/update_product")
	public void updateProduct(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("updateProduct", request, response, model);
	}
	
	@PostMapping("/create_product")
	public void createProduct(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("createProduct", request, response, model);
	}
	
	@PostMapping("/delete_product")
	public void deleteProduct(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("deleteProduct", request, response, model);
	}
	
	@PostMapping("/block_user")
	public void blockUser(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("blockUser", request, response, model);
	}
	
	@PostMapping("/admin_user")
	public void adminUser(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("adminUser", request, response, model);
	}
	
	@PostMapping("/delete_user")
	public void deleteUser(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("deleteUser", request, response, model);
	}
	
	@PostMapping("/order_set_paid")
	public void orderSetPaid(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("orderSetPaid", request, response, model);
	}
	
	@PostMapping("/order_reject")
	public void orderReject(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("orderReject", request, response, model);
	}
}
