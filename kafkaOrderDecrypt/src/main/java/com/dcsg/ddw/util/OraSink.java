package com.dcsg.ddw.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dcsg.ddw.ord.OrderDDW;
import com.dcsg.ddw.ord.OrderHeaderDDW;
import com.dcsg.ddw.ord.OrderLineDDW;
import com.dcsg.ddw.ord.OrdersDDW;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class OraSink {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional 
	public int sink( OrdersDDW ddwOrders)
	{
		//int result = jdbcTemplate.queryForObject(
		//    	"select count(1) from stg_nrt_inventory where rownum < 5", Integer.class);
		//         System.out.println(result);  
		         
		         
		 			//P.S. don't add a try/catch; it will prevent a transaction rollback
					final java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());
					String sql =  "INSERT INTO STG_ORDER (ORDER_NUM, ORDER_DATE,STORE_ID,GRAND_TOTAL_AMT,PURCHASE_PRICE_AMT,"
		 				    + "NUM_ORDER_ITEMS,TOTAL_SHIPPING_CHARGE,LOYALITY_ID,DATE_ADDED) VALUES (?, to_date(?,'MM/DD/YYYY HH24:MI'), ?, ?, ?, ?,?,?,? )";

					jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() 
					{
							
						     @Override
							  public void setValues(PreparedStatement ps, int i) throws SQLException{ 
							

						       OrderHeaderDDW ordHdr = ddwOrders.getOrderHeaders().get(i);
							   if (ordHdr != null)
							   {
								   ps.setString(1, ordHdr.getOrderNum());				
								   ps.setString(2, ordHdr.getOrderSubmitDt());
								   ps.setString(3, ordHdr.getOrderWebStore());
								   ps.setString(4, ordHdr.getOrderTotAmt());
								   ps.setString(5, ordHdr.getPurhasePriceAmt());								   								  
								   ps.setString(6, ordHdr.getOrderTotUnits());
								   ps.setString(7, ordHdr.getOrderShipAmt());	
								   ps.setString(8, ordHdr.getLoyalityId());
								   ps.setTimestamp(9, ts);
							   }
								 
							 				  
							  }
						  
							  @Override
							  public int getBatchSize() {
							   return ddwOrders.getOrderHeaders().size();
							  }
					});
					/*
					
					String sql =  "INSERT INTO STG_ORDER (ORDER_NUM, ORDER_PLACED_DATE,STORE_ID,GRAND_TOTAL_AMT,"
		 				    + "NUM_ORDER_ITEMS,TOTAL_SHIPPING_CHARGE,DATE_ADDED) VALUES (?, to_date(?,'MM/DD/YYYY HH24:MI'), ?, ?, ?, ?,? )";

		 			//insert order header info
		 			jdbcTemplate.update(sql, new PreparedStatementSetter(){  
		 			   
		 				public void setValues(PreparedStatement ps) throws SQLException{ 
						
							   ps.setString(1, ddwOrders.getOrderHeader().getOrderNum());				
							   ps.setString(2, ddwOrders.getOrderHeader().getOrderSubmitDt());
							   ps.setString(3, ddwOrders.getOrderHeader().getOrderWebStore());
							   ps.setString(4, ddwOrders.getOrderHeader().getOrderTotAmt());
							   ps.setString(5,ddwOrders.getOrderHeader().getOrderTotUnits());
							   ps.setString(6, ddwOrders.getOrderHeader().getOrderShipAmt());								   
							   ps.setTimestamp(7, ts);
						   
							 
						 				  
						  }
		 			});    
                    */
		 			//insert order item details
					sql = "INSERT INTO STG_ORDER_ITEM " + "(ORDER_NUM,SKU,QTY,ORIGINAL_PRICE,PURCHASE_PRICE,DISCOUNT_AMT,SHIP_CITY,SHIP_STATE,SHIP_ZIP,DATE_ADDED) VALUES (?, ?, ?, ?,?,?,?,?,?,?)";
						
					//log.info("Beginning line item insert attempt...");
					jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() 
					{
							
						     @Override
							  public void setValues(PreparedStatement ps, int i) throws SQLException{ 
							
							   //OrderLineDDW ord = ddwOrder.getOrderLines().get(i);
						       OrderLineDDW ordLn = ddwOrders.getOrderLines().get(i);
							   if (ordLn != null)
							   {
								   ps.setString(1, ordLn.getOrderNum());				
								   ps.setString(2, ordLn.getSku());
								   ps.setString(3, ordLn.getQty());
								   ps.setString(4, ordLn.getOrigPrice());
								   ps.setString(5, ordLn.getPurchasePrice());
								   ps.setString(6, ordLn.getDiscountAmt());
								   
								   
								   ps.setString(7, ordLn.getShipCity());
								   ps.setString(8, ordLn.getShipState());
								   ps.setString(9, ordLn.getShipZip());								   
								   ps.setTimestamp(10, ts);
							   }
								 
							 				  
							  }
						  
							  @Override
							  public int getBatchSize() {
							   return ddwOrders.getOrderLines().size();
							  }
					});
					
					 log.info("Inserts complete.");
					 return 0;
			       

	}
}


