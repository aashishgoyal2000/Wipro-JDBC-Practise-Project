package com.wipro.sales.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.wipro.sales.bean.Product;
import com.wipro.sales.util.DBUtil;

public class StockDoa {
	
	private Connection con = null;
	private String insert = "insert into tbl_stock values(?, ?, ?, ?, ?)";
	private String sqlIdentifier = "select (next value for seq_product_id) as nextValue";
	private String update = "UPDATE tbl_stock SET quantity_on_hand = ? WHERE Product_id = ?";
	private String delete = "delete from tbl_stock where product_id = ?";
	private String search = "select * from tbl_stock where product_id = ?";
	private PreparedStatement ps = null;
	
	public StockDoa() {
		try {
			con = DBUtil.getDBConnection();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int insertStock(Product sales) {
		// this method is used to insert the given stock obj into tbl_stock table
		sales.setProductID((String)generateProductId(sales.getProductName()));
		try {
			ps = con.prepareStatement(insert);
			ps.setString(1,  sales.getProductID());
			ps.setString(2, sales.getProductName());
			ps.setInt(3, sales.getQuantityOnHand());
			ps.setInt(4, (int)sales.getProductUnitPrice());
			ps.setInt(5, sales.getReorderLevel());
			ps.execute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public String generateProductId(String productName) {
		// this method is used to generate stock id using the first 2 letters of the given product name concatenated with the seq_product_id sequence generated number
		String out ="";
		
		try {
			ps = con.prepareStatement(sqlIdentifier);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				out += productName.charAt(0) + productName.charAt(1) + rs.getInt(1) + "";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}
	
	public boolean updateStock(String productId, int soldQty) {
		// this method is used to update the stock table by subtracting the current quantity_on_hand by the given soldQty of the given productId
		try {
			ps = con.prepareStatement(update);
			Product searchRes = getStock(productId);
			int diff = searchRes.getQuantityOnHand() - soldQty;
			ps.setInt(1,  diff);
			ps.setString(2, productId);
			ps.executeQuery();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public Product getStock(String productId) {
		// this method is used to fetch a specific record details from the stock table for the given productId, store the information to a tock bean object the return the same
		try {
			ps = con.prepareStatement(search);
			ps.setString(1, productId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Product temp = new Product();
				temp.setProductID(rs.getString(1));
				temp.setProductName(rs.getString(2));
				temp.setProductUnitPrice(rs.getDouble(4));
				temp.setQuantityOnHand(rs.getInt(3));
				temp.setReorderLevel(rs.getInt(5));
				return temp;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int deleteStock(String productId) {
		// this method is used to delete the stock record of the given product id;
		try {
			ps = con.prepareStatement(delete);
			ps.setString(1, productId);
			ps.executeQuery();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
		
	}
}
