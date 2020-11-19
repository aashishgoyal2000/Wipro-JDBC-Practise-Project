package com.wipro.sales.service;

import java.util.Date;
import java.util.ArrayList;

import com.wipro.sales.bean.Product;
import com.wipro.sales.bean.Sales;
import com.wipro.sales.bean.SalesReport;
import com.wipro.sales.dao.SalesDao;
import com.wipro.sales.dao.StockDoa;

public class Administrator {
	
	StockDoa st = null;
	
	public Administrator() {
		st = new StockDoa();
	}
	
	public String insertStock(Product obj) {
		
		if (obj == null || obj.getProductName().length() <= 2) {
			return "Data not valid for insertion";
		}
		
		
		String productID = (st.generateProductId((String)obj.getProductName()));
		obj.setProductID((String)productID);
		
		int out = st.insertStock(obj);
		
		if (out == 0) {
			return productID;
		}
		return "";
	}
	
	public String deleteStock(String productId) {
		int out = st.deleteStock(productId);
		if (out == 0) {
			return "deleted";
		}
		return "record cannot be deleted";
	}
	
	public String insertSales(Sales salesObj) {
        if (salesObj != null) {
        	StockDoa stockDao = new StockDoa();
        	Product stock = stockDao.getStock(salesObj.getProductId());
        	
        	if (stock != null) {
        		try {
        			if (stock.getQuantityOnHand() > salesObj.getQuantitySold()) {
        				if (!salesObj.getSalesDate().after(new Date())) {
        					SalesDao salesDao = new SalesDao();
        					String salesID = salesDao.generateSalesId(salesObj.getSalesDate());
        					salesObj.setSalesId(salesID);
        					
        					if (salesDao.insertSales(salesObj) > 0) {
        						if (stockDao.updateStock(salesObj.getProductId(), salesObj.getQuantitySold())) {
        							return "Sales Completed";
        						} else {
        							return "Update Error";
        						}
        					} else {
        						return "Insert Error";
        					}
        				} else {
        					return "invalid date";
        				}
        			} else {
        				return "Not enough stock on hand for sales";
        			}
        		} catch (Exception e) {
        			System.out.println("Error in Administrator.insertSales");
				}
        	} else {
        		return "Unknown Product for sales";
        	}
        } else {
            return "Object not valid for insertion";
        }
        
		return null;
    }
	
	public ArrayList<SalesReport> getSalesReport() {
    	SalesDao salesDao = new SalesDao();
    	ArrayList<SalesReport> salesReports = salesDao.getSalesReport();
    	return salesReports;
    }

}
