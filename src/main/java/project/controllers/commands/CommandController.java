package project.controllers.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class CommandController {
	
	private BuildCatalog buildCatalog;
	
	@Autowired
	public void setBuildCatalog(BuildCatalog buildCatalog) {
		this.buildCatalog = buildCatalog;
	}
	
	public void buildCatalog(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		buildCatalog.execute(request, response, model);
	}
	
	private CatalogChangePage catalogChangePage;
	
	@Autowired
	public void setCatalogChangePage(CatalogChangePage catalogChangePage) {
		this.catalogChangePage = catalogChangePage;
	}
	
	public void catalogNext(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		catalogChangePage.execute(request, response, model);
	}
	
	private BuildProduct buildProduct;
	
	@Autowired
	public void setBuildProduct(BuildProduct buildProduct) {
		this.buildProduct = buildProduct;
	}
	
	public void buildProduct(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		buildProduct.execute(request, response, model);
	}
}
