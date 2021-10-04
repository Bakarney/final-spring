package project.controllers;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.NoHandlerFoundException;

import project.controllers.commands.Invoker;

@Controller
public class FrontController {
	
	private Invoker command;
	private Map<String, String> messages;
	static final Logger logger = LogManager.getLogger(FrontController.class);
	
	@Autowired
	public FrontController(Invoker command) {
		this.command = command;
		messages = new HashMap<>();
		messages.put("403", "Access Denied");
		messages.put("404", "Page not Found");
		messages.put("500", "Server Exception");
	}
	
	@GetMapping("/*")
	public void pageNotFound(HttpServletResponse response) throws IOException {
		response.sendRedirect("error?code=404");
	}
	
	@ExceptionHandler(Throwable.class)
    public void handleException(HttpServletResponse response, Throwable e) throws IOException {
        logger.error(e.getStackTrace());
        response.sendRedirect("error?code=500");
    }
	
	@GetMapping("/error")
	public String getAccessDenied(HttpServletRequest request, Model model) {
		String code = request.getParameter("code");
		String message = messages.get(code);
		if (message == null)
			message = "";
		model.addAttribute("code", code);
		model.addAttribute("message", message);
		return "error";
	}
	
	@GetMapping("/home")
	public String getHome() {
		return "home";
	}
	
	@GetMapping("/catalog")
	public String getCatalog(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("buildCatalog", request, response, model);
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
	public String getProfile(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("buildProfile", request, response, model);
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
	
	@GetMapping("/create_product")
	public String getCreateProduct(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "admin_create_product";
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
		command.execute("confirmOrder", request, response, model);
	}
	
	@PostMapping("/manage_carts")
	public void manageConflict(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("manageOrderConflict", request, response, model);
	}
	
	@PostMapping("/update_product")
	public void updateProduct(@RequestParam CommonsMultipartFile file, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String path=request.getSession().getServletContext().getRealPath("/");
			String filename=file.getOriginalFilename();
	        byte barr[]=file.getBytes();
	        BufferedOutputStream bout=new BufferedOutputStream(new FileOutputStream(path+"/"+filename));
	        bout.write(barr);
	        bout.flush();
	        bout.close();
	        model.addAttribute("file", filename);
		} catch(IOException e) {
			e.printStackTrace();
		}
		command.execute("updateProduct", request, response, model);
	}
	
	@PostMapping("/create_product")
	public void createProduct(@RequestParam CommonsMultipartFile file, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String path=request.getSession().getServletContext().getRealPath("/");
			String filename=file.getOriginalFilename();
	        byte barr[]=file.getBytes();
	        BufferedOutputStream bout=new BufferedOutputStream(new FileOutputStream(path+"/"+filename));
	        bout.write(barr);
	        bout.flush();
	        bout.close();
	        model.addAttribute("file", filename);
		} catch(IOException e) {
			e.printStackTrace();
		}
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
	
	@PostMapping("/set_paid")
	public void orderSetPaid(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("orderSetPaid", request, response, model);
	}
	
	@PostMapping("/reject_order")
	public void orderReject(HttpServletRequest request, HttpServletResponse response, Model model) {
		command.execute("orderReject", request, response, model);
	}
}
