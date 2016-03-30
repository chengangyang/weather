package app;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WeatherInterface {
	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new MyTask2(),1000,1000*60*60*24);
		timer.schedule(new MyTask1(),1000,1000*60);
	}
	
	
}
class MyTask2 extends TimerTask{
	@Override
	public void run(){
		getDayWeather();
	}
	public void getDayWeather(){
		String uri = null;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/weather";
		String username = "root";
		String password = "root";
		Connection conn = null;
		int i = 0;
		try {
			Class.forName(driver);
			conn = (Connection) DriverManager.getConnection(url,username,password);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			uri = "http://v.juhe.cn/weather/index?format=2&cityname="+URLEncoder.encode("北京", "utf-8")+"&key=d28a669fc3b21ad56332b4a3bb9aae34";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = PureNetUtil.get(uri);
		if(result!=null){
			JSONObject obj = JSONObject.fromObject(result);
			result = obj.getString("resultcode");
			if(result.equals("200")){
				result = obj.getString("result");
				obj = JSONObject.fromObject(result);
				
				
				
				PreparedStatement pstmt;
				String today = obj.getString("today");
				JSONObject todayobj = JSONObject.fromObject(today);
				String date_y = todayobj.getString("date_y");
				String sql1 = "select * from today_weather where date_y ='"+date_y+"'";
				try {
					Statement statement = conn.createStatement();
					ResultSet rs = statement.executeQuery(sql1);
					if(!rs.next()){
						String sql2 = "insert into today_weather(temperature,weather,wind,week,city,date_y,dressing_index,dressing_advice,uv_index,comfort_index,wash_index,travel_index,exercise_index,drying_index) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						pstmt = (PreparedStatement) conn.prepareStatement(sql2);
						pstmt.setString(1,todayobj.getString("temperature"));
						pstmt.setString(2, todayobj.getString("weather"));
						pstmt.setString(3, todayobj.getString("wind"));
						pstmt.setString(4, todayobj.getString("week"));
						pstmt.setString(5, todayobj.getString("city"));
						pstmt.setString(6, todayobj.getString("date_y"));
						pstmt.setString(7, todayobj.getString("dressing_index"));
						pstmt.setString(8, todayobj.getString("dressing_advice"));
						pstmt.setString(9, todayobj.getString("uv_index"));
						pstmt.setString(10, todayobj.getString("comfort_index"));
						pstmt.setString(11, todayobj.getString("wash_index"));
						pstmt.setString(12, todayobj.getString("travel_index"));
						pstmt.setString(13, todayobj.getString("exercise_index"));
						pstmt.setString(14, todayobj.getString("drying_index"));
						pstmt.executeUpdate();
						pstmt.close();
						System.out.println("jintianruku");
					}
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
				String future = obj.getString("future");
				JSONArray arr = JSONArray.fromObject(future);
				JSONObject obj0 = JSONObject.fromObject(arr.get(0).toString());
				JSONObject obj1 = JSONObject.fromObject(arr.get(1).toString());
				JSONObject obj2 = JSONObject.fromObject(arr.get(2).toString());
				JSONObject obj3 = JSONObject.fromObject(arr.get(3).toString());
				JSONObject obj4 = JSONObject.fromObject(arr.get(4).toString());
				JSONObject obj5 = JSONObject.fromObject(arr.get(5).toString());
				JSONObject obj6 = JSONObject.fromObject(arr.get(6).toString());
				String sql3 = "select * from future_weather";
				String sql4 = "select * from future_weather where date = '"+obj6.getString("date")+"'";
				String sql5 = "insert into future_weather (temperature,weather,wind,week,date) values(?,?,?,?,?)";
				Statement statement;
				try {
					statement = conn.createStatement();
					ResultSet rs = statement.executeQuery(sql3);
					
					if(!rs.next()){
						pstmt = (PreparedStatement) conn.prepareStatement(sql5);
						pstmt.setString(1, obj0.getString("temperature"));
						pstmt.setString(2, obj0.getString("weather"));
						pstmt.setString(3, obj0.getString("wind"));
						pstmt.setString(4, obj0.getString("week"));
						pstmt.setString(5, obj0.getString("date"));
						pstmt.executeUpdate();
						pstmt.setString(1, obj1.getString("temperature"));
						pstmt.setString(2, obj1.getString("weather"));
						pstmt.setString(3, obj1.getString("wind"));
						pstmt.setString(4, obj1.getString("week"));
						pstmt.setString(5, obj1.getString("date"));
						pstmt.executeUpdate();
						pstmt.setString(1, obj2.getString("temperature"));
						pstmt.setString(2, obj2.getString("weather"));
						pstmt.setString(3, obj2.getString("wind"));
						pstmt.setString(4, obj2.getString("week"));
						pstmt.setString(5, obj2.getString("date"));
						pstmt.executeUpdate();
						pstmt.setString(1, obj3.getString("temperature"));
						pstmt.setString(2, obj3.getString("weather"));
						pstmt.setString(3, obj3.getString("wind"));
						pstmt.setString(4, obj3.getString("week"));
						pstmt.setString(5, obj3.getString("date"));
						pstmt.executeUpdate();
						pstmt.setString(1, obj4.getString("temperature"));
						pstmt.setString(2, obj4.getString("weather"));
						pstmt.setString(3, obj4.getString("wind"));
						pstmt.setString(4, obj4.getString("week"));
						pstmt.setString(5, obj4.getString("date"));
						pstmt.executeUpdate();
						pstmt.setString(1, obj5.getString("temperature"));
						pstmt.setString(2, obj5.getString("weather"));
						pstmt.setString(3, obj5.getString("wind"));
						pstmt.setString(4, obj5.getString("week"));
						pstmt.setString(5, obj5.getString("date"));
						pstmt.executeUpdate();
						pstmt.setString(1, obj6.getString("temperature"));
						pstmt.setString(2, obj6.getString("weather"));
						pstmt.setString(3, obj6.getString("wind"));
						pstmt.setString(4, obj6.getString("week"));
						pstmt.setString(5, obj6.getString("date"));
						pstmt.executeUpdate();
						pstmt.close();
						System.out.println("zhezhouruku");
					}else{
						String sql00="update future_weather set temperature ='"+obj0.getString("temperature")+"',weather='"+obj0.getString("weather")+"',wind='"+obj0.getString("wind")+"',week='"+obj0.getString("week")+"',date='"+obj0.getString("date")+"' where date = '"+obj0.getString("date")+"'";
						pstmt = (PreparedStatement) conn.prepareStatement(sql00);
						pstmt.executeUpdate();
						String sql11="update future_weather set temperature ='"+obj1.getString("temperature")+"',weather='"+obj1.getString("weather")+"',wind='"+obj1.getString("wind")+"',week='"+obj1.getString("week")+"',date='"+obj1.getString("date")+"' where date = '"+obj1.getString("date")+"'";
						pstmt = (PreparedStatement) conn.prepareStatement(sql11);
						pstmt.executeUpdate();
						String sql22="update future_weather set temperature ='"+obj2.getString("temperature")+"',weather='"+obj2.getString("weather")+"',wind='"+obj2.getString("wind")+"',week='"+obj2.getString("week")+"',date='"+obj2.getString("date")+"' where date = '"+obj2.getString("date")+"'";
						pstmt = (PreparedStatement) conn.prepareStatement(sql22);
						pstmt.executeUpdate();
						String sql33="update future_weather set temperature ='"+obj3.getString("temperature")+"',weather='"+obj3.getString("weather")+"',wind='"+obj3.getString("wind")+"',week='"+obj3.getString("week")+"',date='"+obj3.getString("date")+"' where date = '"+obj3.getString("date")+"'";
						pstmt = (PreparedStatement) conn.prepareStatement(sql33);
						pstmt.executeUpdate();
						String sql44="update future_weather set temperature ='"+obj4.getString("temperature")+"',weather='"+obj4.getString("weather")+"',wind='"+obj4.getString("wind")+"',week='"+obj4.getString("week")+"',date='"+obj4.getString("date")+"' where date = '"+obj4.getString("date")+"'";
						pstmt = (PreparedStatement) conn.prepareStatement(sql44);
						pstmt.executeUpdate();
						String sql55="update future_weather set temperature ='"+obj5.getString("temperature")+"',weather='"+obj5.getString("weather")+"',wind='"+obj5.getString("wind")+"',week='"+obj5.getString("week")+"',date='"+obj5.getString("date")+"' where date = '"+obj5.getString("date")+"'";
						pstmt = (PreparedStatement) conn.prepareStatement(sql55);
						pstmt.executeUpdate();
						pstmt.close();
						ResultSet rs1 = statement.executeQuery(sql4);
						if(!rs1.next()){
							pstmt = (PreparedStatement) conn.prepareStatement(sql5);
							pstmt.setString(1, obj6.getString("temperature"));
							pstmt.setString(2, obj6.getString("weather"));
							pstmt.setString(3, obj6.getString("wind"));
							pstmt.setString(4, obj6.getString("week"));
							pstmt.setString(5, obj6.getString("date"));
							pstmt.executeUpdate();
							pstmt.close();
							System.out.println("zuihouyitiangengxin");
						}else{
							String sql66="update future_weather set temperature ='"+obj6.getString("temperature")+"',weather='"+obj6.getString("weather")+"',wind='"+obj6.getString("wind")+"',week='"+obj6.getString("week")+"',date='"+obj6.getString("date")+"' where date = '"+obj6.getString("date")+"'";

							pstmt = (PreparedStatement) conn.prepareStatement(sql66);
							pstmt.executeUpdate();
							pstmt.close();
						}
						System.out.println("zhezhougengxin");
					}
					
					statement.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}


class MyTask1 extends TimerTask{
	@Override
	public void run(){
		getWeather();
	}
	public void getWeather(){
		String uri = null;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/weather";
		String username = "root";
		String password = "root";
		Connection conn = null;
		int i = 0;
		try {
			Class.forName(driver);
			conn = (Connection) DriverManager.getConnection(url,username,password);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			uri = "http://v.juhe.cn/weather/index?format=2&cityname="+URLEncoder.encode("北京", "utf-8")+"&key=d28a669fc3b21ad56332b4a3bb9aae34";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = PureNetUtil.get(uri);
		if(result!=null){
			JSONObject obj = JSONObject.fromObject(result);
			result = obj.getString("resultcode");
			if(result.equals("200")){
				result = obj.getString("result");
				obj = JSONObject.fromObject(result);
				
				
				
				String sk = obj.getString("sk");
				JSONObject skobj = JSONObject.fromObject(sk);
				//String temp = skobj.getString("temp");
				String sql = "insert into hour_weather (city,temp,wind_direction,wind_strength,humidity,time) values(?,?,?,?,?,?)";
				PreparedStatement pstmt;
				try {
					pstmt = (PreparedStatement) conn.prepareStatement(sql);
					pstmt.setString(1,"北京");
					pstmt.setString(2,skobj.getString("temp"));
					pstmt.setString(3,skobj.getString("wind_direction"));
					pstmt.setString(4, skobj.getString("wind_strength"));
					pstmt.setString(5,skobj.getString("humidity"));
					pstmt.setString(6, skobj.getString("time"));
					i= pstmt.executeUpdate();
					pstmt.close();
//					conn.close();
					System.out.println("xiaoshi");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
				
	}
	
}