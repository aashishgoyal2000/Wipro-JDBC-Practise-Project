package com.wipro.sales.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.wipro.sales.bean.Sales;
import com.wipro.sales.bean.SalesReport;
import com.wipro.sales.util.DBUtil;

public class SalesDao {
	
	private Connection con = null;
	private String insert = "insert into tbl_sales values(?, ?, ?, ?, ?)";
	private String sqlIdentifier = "select seq_sales_id.NEXTVAL from seq_sales_id";
	private String queryView = "SELECT * FROM v_sales_report";
	private PreparedStatement ps = null;
	
	public SalesDao() {
		try {
			con = DBUtil.getDBConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int insertSales(Sales sales) {
		// insert given sales object in sales table
		sales.setSalesId(generateSalesId((Date)sales.getSalesDate()));
		try {
			ps = con.prepareStatement(insert);
			ps.setString(1, sales.getSalesId());
			ps.setDate(2, (Date) sales.getSalesDate());
			ps.setString(3, sales.getProductId());
			ps.setInt(4, sales.getQuantitySold());
			ps.setInt(5, (int) sales.getSalesPricePerUnit());
			ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		};
		
		return 0;
	}
	
	public String generateSalesId(java.util.Date date2) {
		// this method is used to generate sales id using the last2digit of the year part of the given date concatenated with seq_sales_id sequence generated number
		String out = "";
		try {
			String date = "" + date2.toString().charAt(2) + date2.toString().charAt(3);
			ps = con.prepareStatement(sqlIdentifier);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				out += date + rs.getInt(1) + "";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}
	
	public ArrayList<SalesReport> getSalesReport() {
		// this method runs the v_sales_report view and stores every record in SalesReport Bean adding them to an arrayList. which is return back to the user;
		ArrayList<SalesReport> al = new ArrayList<SalesReport>();
		try {
			ps = con.prepareStatement(queryView);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				SalesReport sr = new SalesReport();
				sr.setSalesId(rs.getString(1));
				sr.setSalesDate(rs.getDate(2));
				sr.setProductId(rs.getString(3));
				sr.setProductName(rs.getString(4));
				sr.setQuantitySold(rs.getInt(5));
				sr.setProductUnitPrice(rs.getInt(6));
				sr.setSalesPricePerUnit(rs.getInt(7));
				sr.setProfitAmount(rs.getInt(8));
				al.add(sr);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return al;
	}

}
