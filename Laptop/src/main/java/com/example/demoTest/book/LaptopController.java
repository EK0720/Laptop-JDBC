package com.example.demoTest.book;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class LaptopController {
	@GetMapping("/laptops")
	public String getLaptops(Model model) throws IOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Laptop> laptops = new ArrayList<Laptop>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_demo", "root", "NguyenTuan2021");
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from laptop");
			
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				int price = resultSet.getInt("price");
				String brand = resultSet.getString("brand");
				Date date = resultSet.getDate("date");
				int paid = resultSet.getInt("paid");
				laptops.add(new Laptop(id, name, price, brand, date, paid==0 ? false:true));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("laptops", laptops);
		return "laptops";
	}
	@GetMapping("/laptop/{id}")
	public String getLaptop(Model model, @PathVariable String id) {
		model.addAttribute("id", id);
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		Laptop laptop = new Laptop();
		if(Integer.valueOf(id)<0) {
			model.addAttribute("laptop", laptop);
			return "laptop-detail";
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_demo", "root", "NguyenTuan2021");
			ps = connection.prepareStatement("select * from laptop where id = ?");
			ps.setInt(1, Integer.valueOf(id));
			result = ps.executeQuery();
			while (result.next()) {
				laptop.setId(result.getInt("id"));
				laptop.setName(result.getString("name"));
				laptop.setPrice(result.getInt("price"));
				laptop.setBrand(result.getString("brand"));
				laptop.setDate(result.getDate("date"));
				laptop.setPaid(result.getInt("paid") != 0 ? true : false);
			}
		} // End of try block
		catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("laptop", laptop);
		return "laptop-detail";
	}
	@PostMapping("/laptop/save/{id}")
	public String addLaptop(Laptop laptop, @PathVariable String id) {
		Connection connection = null;
		PreparedStatement ps = null;
		int result = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_demo", "root", "NguyenTuan2021");
			ps = connection.prepareStatement("INSERT INTO laptop VALUES (?, ?, ?, ?, ?, ?)");
			ps.setInt(1, Integer.valueOf(laptop.getId()));
			ps.setString(2, laptop.getName());
			ps.setInt(3, laptop.getPrice());
			ps.setString(4, laptop.getBrand());
			ps.setDate(5, laptop.getDate());
			ps.setInt(6, laptop.isPaid() ? 1 : 0);
			result = ps.executeUpdate();

			ps.close();
			connection.close();
			// Redirect the response to success page
			return "redirect:/laptops";
			} // End of try block
			catch (Exception e) {
				e.printStackTrace();
			}
			return "error"; // tạo trang Error
	}
	@PutMapping("/laptop/save/{id}")
	public String updateLaptop(Laptop laptop, @PathVariable String id) {
		Connection connection = null;
		PreparedStatement ps = null;
		int result = 0;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_demo", "root", "NguyenTuan2021");
			ps = connection.prepareStatement("UPDATE laptop SET name=?,price=?,brand=?,date=?, paid=? WHERE id=?");
			ps.setString(1, laptop.getName());
			ps.setInt(2, laptop.getPrice());
			ps.setString(3, laptop.getBrand());
			ps.setDate(4, laptop.getDate());
			ps.setInt(5, laptop.isPaid() ? 1 : 0);
			ps.setInt(6, Integer.valueOf(laptop.getId()));
			result = ps.executeUpdate();

			ps.close();
			connection.close();
			// Redirect the response to success page
			return "redirect:/laptops";
			} // End of try block
			catch (Exception e) {
				e.printStackTrace();
		}
			return "error"; // tạo trang Error		
	}
	@DeleteMapping("/laptop/delete/{id}")
	public String deleteLaptop(Laptop laptop, @PathVariable String id) {
		Connection con = null;
    	PreparedStatement ps = null;
    	int result = 0;
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_demo",
                    "root", "NguyenTuan2021");
            ps = con.prepareStatement("delete from laptop WHERE id=?");
            ps.setInt(1, Integer.valueOf(laptop.getId()));
            result = ps.executeUpdate();
            ps.close();
            con.close();
            return "redirect:/laptops";
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	return "error";
	}

}
