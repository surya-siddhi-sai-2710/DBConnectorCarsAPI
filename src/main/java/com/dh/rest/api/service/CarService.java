package com.dh.rest.api.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.CallableStatement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dh.rest.api.model.Cars;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import oracle.jdbc.OracleTypes;

@Component
public class CarService {

	@Autowired
	private DataSource dataSource; // DataSource is the configured object in yml file to connect the DB
	
	@Autowired
	private ObjectMapper objectMapper; // used to read and write json data
	
	// to add a new car
	public void addCars(Cars car) {

		Connection conn = null;
		CallableStatement pstmt = null;
		try {
			
			conn = dataSource.getConnection();
			String strProcedure = "CALL InsertCar(?,?,?)";
			pstmt = conn.prepareCall(strProcedure);
			pstmt.setInt(1, car.getModelno());
			pstmt.setString(2, car.getCname());
			pstmt.setDouble(3, car.getPrice());
			// execute query
			pstmt.execute();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// to view all cars
	public ArrayNode viewAllCars() {
		
		Connection conn = null;
		CallableStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			conn = dataSource.getConnection();
			String strProcedure = "CALL Get_All_Cars(?)";
			pstmt = conn.prepareCall(strProcedure);
			pstmt.registerOutParameter(1, OracleTypes.CURSOR);
			pstmt.execute();
			rs = (ResultSet) pstmt.getObject(1);
			ArrayNode getAllCarsObjectList = objectMapper.createArrayNode();
			while (rs.next()) {
				
				ObjectNode getAllCarsObject = objectMapper.createObjectNode();
				getAllCarsObject.put("modelno", rs.getInt(1));
				getAllCarsObject.put("cname", rs.getString(2));
				getAllCarsObject.put("price", rs.getInt(3));
				getAllCarsObjectList.add(getAllCarsObject);
			}
			
			return getAllCarsObjectList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// to view car by model number
	public ObjectNode viewCarByModelno(Cars car) {
		
		int modelno = car.getModelno();
		Connection conn = null;
		CallableStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			conn = dataSource.getConnection();
			String strProcedure = "CALL Get_Car_By_Modelno(?,?)";
			pstmt = conn.prepareCall(strProcedure);
			pstmt.setInt(1, modelno);
			pstmt.registerOutParameter(2, OracleTypes.CURSOR);
			pstmt.execute();
			rs = (ResultSet) pstmt.getObject(2);
			if (rs.next()) {
				
				ObjectNode getCarByModelnoObject = objectMapper.createObjectNode();
				getCarByModelnoObject.put("modelno", rs.getInt(1));
				getCarByModelnoObject.put("cname", rs.getString(2));
				getCarByModelnoObject.put("price", rs.getInt(3));
				return getCarByModelnoObject;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}