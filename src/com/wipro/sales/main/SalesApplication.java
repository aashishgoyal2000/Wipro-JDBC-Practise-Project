package com.wipro.sales.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.wipro.sales.bean.Product;
import com.wipro.sales.bean.Sales;
import com.wipro.sales.bean.SalesReport;
import com.wipro.sales.service.Administrator;

public class SalesApplication {
	public static void main(String[] args) throws NumberFormatException, IOException, ParseException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Choose operation\n1. Insert Stock\n2. Delete Stock\n3. Insert Sales\n4. View Sales Report\n");
		int choice = Integer.parseInt(reader.readLine());
		Administrator administrator = new Administrator();
		String result = "";
		
		switch (choice) {
		case 1:
			Product stock = new Product();
			System.out.print("\nEnter Product name: ");
			stock.setProductName(reader.readLine());
			System.out.print("\nEnter Quantity On Hand: ");
			stock.setQuantityOnHand(Integer.parseInt(reader.readLine()));
			System.out.print("\nEnter Product Unit Price: ");
			stock.setProductUnitPrice(Double.parseDouble(reader.readLine()));
			System.out.print("\nEnter Reorder Level: ");
			stock.setReorderLevel(Integer.parseInt(reader.readLine()));
			
			result = administrator.insertStock(stock);
			System.out.println(result);
			break;
		case 2:
			System.out.print("\nEnter Product ID: ");
			String productID = reader.readLine();
			
			result = administrator.deleteStock(productID);
			System.out.println(result);
			break;
		case 3:
			Sales sales = new Sales();
			System.out.print("\nEnter Sales Date (use dd/MM/yyyy): ");
			sales.setSalesDate(new SimpleDateFormat("dd/MM/yyyy").parse(reader.readLine()));
			System.out.print("\nEnter Product ID: ");
			sales.setProductId(reader.readLine());
			System.out.print("\nEnter Quantity sold: ");
			sales.setQuantitySold(Integer.parseInt(reader.readLine()));
			System.out.print("\nEnter Sales Price Per Unit: ");
			sales.setSalesPricePerUnit(Double.parseDouble(reader.readLine()));
			
			result = administrator.insertSales(sales);
			System.out.println(result);
			break;
		case 4:
			ArrayList<SalesReport> salesReports = administrator.getSalesReport();
		
			for (SalesReport salesReport: salesReports) {
				System.out.println(salesReport.getSalesId() + ", " + salesReport.getSalesDate() + ", " + salesReport.getProductId() + ", " + salesReport.getProductName() + ", " + salesReport.getQuantitySold() + ", " + salesReport.getProductUnitPrice() + ", " + salesReport.getSalesPricePerUnit() + ", " + salesReport.getProfitAmount());
			}
			break;
		default:
			break;
		}
	}
}
