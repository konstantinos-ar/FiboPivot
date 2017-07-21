package fibopivot;

import java.io.DataInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Fpivot {
	
	static ArrayList<Double> alist;
	static ArrayList<Double> blist;
	static ArrayList<String> sym = new ArrayList<String>();
    	static String TITLE = "title";
    	static String DESC = "description";
    	static String THUMB = "thumbnail";
    	static boolean buy = false, sell = true;
    	static double high, low, close = 0, hh = 0, ll = 1000, newt = 0, pp, s1, s2, s3, r1, r2, r3, sum = 0, sum2 = 0, ss = 0, p = 0, p2 = 0, c = 0, f = 0, fund = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		alist = new ArrayList<Double>();
		blist = new ArrayList<Double>();
		// YQL JSON URL
		String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20%3D%20%22ebay%22%20and%20startDate%20%3D%20%222012-09-01%22%20and%20endDate%20%3D%20%222014-01-31%22%20%20%7C%20sort(field%3D%22Date%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
		String url2 = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20%3D%20%22BAC%22%20and%20startDate%20%3D%20%222009-10-01%22%20and%20endDate%20%3D%20%222009-10-31%22%20%20%7C%20sort(field%3D%22Date%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
		String url3 = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20%3D%20%22BAC%22%20and%20startDate%20%3D%20%222009-11-01%22%20and%20endDate%20%3D%20%222009-11-30%22%20%20%7C%20sort(field%3D%22Date%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
		
		JSONArray json_result = null;
		JSONArray json_result2 = null;
		DataInputStream dis = new DataInputStream(System.in);
		Scanner input = new Scanner(System.in);
		String newsym = null;
		int d = 10, y = 9;
		//sym.add("%5EGDAXI");
		//sym.add("CAC");
		
		try {
			
			DefaultHttpClient httpclient = new DefaultHttpClient();
			int i = 0, j = 0, k = 0;
			//double high, low, close = 0, hh = 0, ll = 1000, newt = 0, pp, s1, s2, s3, r1, r2, r3;
			String edate = null, dayin = null, dayout = null;
	                
	                JSONObject json_data = JSONfuntions.getJSONfromURL(url);
	                JSONObject json_query = json_data.getJSONObject("query");
			JSONObject json_results = json_query.getJSONObject("results");
			json_result = json_results.getJSONArray("quote");
					
			if (json_result != null)
				for (int ii = 0; ii < 21; ii++) 
				{
					//HashMap<String, String> map = new HashMap<String, String>();
					JSONObject vo = json_result.getJSONObject(ii);
					//System.out.print("\rSymbol: " + vo.optString("Name") + ", Price: " + vo.optString("LastTradePriceOnly") + ", Ask Price: " + vo.optString("AskRealtime") + ", Bid Price: " + vo.optString("BidRealtime") + ", Change: " + vo.optString("ChangeinPercent") + ", Last Trade: " + vo.optString("LastTradeDate") + " " + vo.optString("LastTradeTime"));

					edate = vo.optString("Date");
					close = vo.optDouble("Close");
					high = vo.optDouble("High");
					low = vo.optDouble("Low");
							
					//System.out.println("Date is : " + edate);
							
					if (high > hh)
						hh = high;
					if (low < ll)
						ll = low;
				}

		/*			pp = (hh + ll + close)/3;
					s1 = pp - ( 0.382 * (hh - ll));
					s2 = pp - ( 0.618 * (hh - ll));
					s3 = pp - ( 1 * (hh - ll));
					r1 = pp + ( 0.382 * (hh - ll));
					r2 = pp + ( 0.618 * (hh - ll));
					r3 = pp + ( 1 * (hh - ll));
		*/
					fibocalc(hh,ll);
					System.out.println("Pivot is: " + pp + "\nSupport1 is : " + s1 + "\nSupport2 is : " + s2 + "\nSupport3 is : " + s3 + "\nResistance1 is : " + r1 + "\nResistance2 is : " + r2 + "\nResistance3 is : " + r3);
					
		/*			json_data = JSONfuntions.getJSONfromURL(url2);
	                json_query = json_data.getJSONObject("query");
					json_results = json_query.getJSONObject("results");
					json_result = json_results.getJSONArray("quote");
		*/			
					if (json_result != null)
						for (int ii = 21; ii < json_result.length(); ii++) 
						{
							//HashMap<String, String> map = new HashMap<String, String>();
							JSONObject vo = json_result.getJSONObject(ii);
							//System.out.print("\rSymbol: " + vo.optString("Name") + ", Price: " + vo.optString("LastTradePriceOnly") + ", Ask Price: " + vo.optString("AskRealtime") + ", Bid Price: " + vo.optString("BidRealtime") + ", Change: " + vo.optString("ChangeinPercent") + ", Last Trade: " + vo.optString("LastTradeDate") + " " + vo.optString("LastTradeTime"));

							edate = vo.optString("Date");
							close = vo.optDouble("Close");
							high = vo.optDouble("High");
							low = vo.optDouble("Low");
							
							if (Integer.parseInt(edate.substring(5, 7)) > d || Integer.parseInt(edate.substring(2, 4)) > y)
							{
								fibocalc(hh,ll);
								System.out.println("Pivot is: " + pp + "\nSupport1 is : " + s1 + "\nSupport2 is : " + s2 + "\nSupport3 is : " + s3 + "\nResistance1 is : " + r1 + "\nResistance2 is : " + r2 + "\nResistance3 is : " + r3);
								hh = 0;
								ll = 1000;
							}
							
							d = Integer.parseInt(edate.substring(5, 7));
							y = Integer.parseInt(edate.substring(2, 4));
							System.out.println("d is: " + d + "\ty is: " + y);
							
							if (high > hh)
								hh = high;
							if (low < ll)
								ll = low;
							
							//System.out.println("Date is : " + edate);
						
						//	pp = (high + low + close)/3;
							
							// Standard Pivot Points
						/*	s1 = pp*2 - high;
							s2 = pp - (high - low);
							r1 = pp*2 - low;
							r2 = pp + (high - low);
						*/
							
							//Fibonacci Pivot Points
						/*	s1 = pp - ( 0.382 * (high - low));
							s2 = pp - ( 0.618 * (high - low));
							s3 = pp - ( 1 * (high - low));
							r1 = pp + ( 0.382 * (high - low));
							r2 = pp + ( 0.618 * (high - low));
							r3 = pp + ( 1 * (high - low));
							
							System.out.println("Pivot is: " + pp + "\nSupport1 is : " + s1 + "\nSupport2 is : " + s2 + "\nSupport3 is : " + s3 + "\nResistance1 is : " + r1 + "\nResistance2 is : " + r2 + "\nResistance3 is : " + r3);
						*/	
							//if (ii < json_result.length() - 1)
							//{
							/*	vo = json_result.getJSONObject(ii + 1);
								edate = vo.optString("Date");
								close = vo.optDouble("Close");
								high = vo.optDouble("High");
								low = vo.optDouble("Low");
							*/	
								System.out.println("Date is : " + edate);
								System.out.println("High is : " + high);
								System.out.println("Low is : " + low);
								System.out.println("Close is : " + close);
								
								if (low < s2 && buy == false)
								{
									System.out.println("BUY!!");
									buy = true;
									sell = false;
									newt = low;
									sum = 10 * s2;
									fund = fund + sum;
									f++;
									sum2 = 10 * s2;
									if (dayin == null)
										dayin = edate;
								}
								if (high > r1 && sell == false)
								{
									System.out.println("SELL!!");
									buy = false;
									sell = true;
									newt = high;
									sum = 10 * r1 - sum;
									ss = ss + sum;
									c++;
									p = ss / sum2;
									p2 = p2 + p;//(p + sum / ss) / c;
									sum = 0;
									dayout = edate;
								}
								
							//}

						}
					System.out.println("\r");
					
					System.out.println("Fund is : " + fund/f + "\nSum is: " + ss + "\nPerformance is: " + new DecimalFormat("##.##").format(p*100) + "%" + "\nDay in is: " + dayin + "\nDayout is: " + dayout);
					
					
					
					
					
					
/*					json_data = JSONfuntions.getJSONfromURL(url2);
	                json_query = json_data.getJSONObject("query");
					json_results = json_query.getJSONObject("results");
					json_result = json_results.getJSONArray("quote");
					
					if (json_result != null)
						for (int ii = 0; ii < json_result.length(); ii++) 
						{
							//HashMap<String, String> map = new HashMap<String, String>();
							JSONObject vo = json_result.getJSONObject(ii);
							//System.out.print("\rSymbol: " + vo.optString("Name") + ", Price: " + vo.optString("LastTradePriceOnly") + ", Ask Price: " + vo.optString("AskRealtime") + ", Bid Price: " + vo.optString("BidRealtime") + ", Change: " + vo.optString("ChangeinPercent") + ", Last Trade: " + vo.optString("LastTradeDate") + " " + vo.optString("LastTradeTime"));
							edate = vo.optString("Date");
							close = vo.optDouble("Close");
							high = vo.optDouble("High");
							low = vo.optDouble("Low");
							
							//System.out.println("Date is : " + edate);
							
							if (high > hh)
								hh = high;
							if (low < ll)
								ll = low;
						}
					pp = (hh + ll + newt)/3;
					s1 = pp - ( 0.382 * (hh - ll));
					s2 = pp - ( 0.618 * (hh - ll));
					s3 = pp - ( 1 * (hh - ll));
					r1 = pp + ( 0.382 * (hh - ll));
					r2 = pp + ( 0.618 * (hh - ll));
					r3 = pp + ( 1 * (hh - ll));
					
					System.out.println("Pivot is: " + pp + "\nSupport1 is : " + s1 + "\nSupport2 is : " + s2 + "\nSupport3 is : " + s3 + "\nResistance1 is : " + r1 + "\nResistance2 is : " + r2 + "\nResistance3 is : " + r3);
					
					json_data = JSONfuntions.getJSONfromURL(url3);
	                json_query = json_data.getJSONObject("query");
					json_results = json_query.getJSONObject("results");
					json_result = json_results.getJSONArray("quote");
					
					if (json_result != null)
						for (int ii = 0; ii < json_result.length(); ii++) 
						{
							//HashMap<String, String> map = new HashMap<String, String>();
							JSONObject vo = json_result.getJSONObject(ii);
							//System.out.print("\rSymbol: " + vo.optString("Name") + ", Price: " + vo.optString("LastTradePriceOnly") + ", Ask Price: " + vo.optString("AskRealtime") + ", Bid Price: " + vo.optString("BidRealtime") + ", Change: " + vo.optString("ChangeinPercent") + ", Last Trade: " + vo.optString("LastTradeDate") + " " + vo.optString("LastTradeTime"));
							edate = vo.optString("Date");
							close = vo.optDouble("Close");
							high = vo.optDouble("High");
							low = vo.optDouble("Low");
							
							//System.out.println("Date is : " + edate);
						
						//	pp = (high + low + close)/3;
							
							// Standard Pivot Points
						/*	s1 = pp*2 - high;
							s2 = pp - (high - low);
							r1 = pp*2 - low;
							r2 = pp + (high - low);
						*/
							
							//Fibonacci Pivot Points
						/*	s1 = pp - ( 0.382 * (high - low));
							s2 = pp - ( 0.618 * (high - low));
							s3 = pp - ( 1 * (high - low));
							r1 = pp + ( 0.382 * (high - low));
							r2 = pp + ( 0.618 * (high - low));
							r3 = pp + ( 1 * (high - low));
							
							System.out.println("Pivot is: " + pp + "\nSupport1 is : " + s1 + "\nSupport2 is : " + s2 + "\nSupport3 is : " + s3 + "\nResistance1 is : " + r1 + "\nResistance2 is : " + r2 + "\nResistance3 is : " + r3);
						*/	
							//if (ii < json_result.length() - 1)
							//{
							/*	vo = json_result.getJSONObject(ii + 1);
								edate = vo.optString("Date");
								close = vo.optDouble("Close");
								high = vo.optDouble("High");
								low = vo.optDouble("Low");
							*/	
/*								System.out.println("Date is : " + edate);
								System.out.println("High is : " + high);
								System.out.println("Low is : " + low);
								System.out.println("Close is : " + close);
								
								if (low < s2)
									System.out.println("BUY!!");
								if (high > r1)
									System.out.println("SELL!!");
								
							//}
						}
					System.out.println("\r");
					
					
					
					
					
					
					
					
				//}
				
/*			if (rs != null)
				rs.close();
			rs = null;
*/			
			
		} //catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//} catch (SQLException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//} 
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void fibocalc (double h, double l)
	{
		pp = (h + l + close)/3;
		s1 = pp - ( 0.382 * (h - l));
		s2 = pp - ( 0.618 * (h - l));
		s3 = pp - ( 1 * (h - l));
		r1 = pp + ( 0.382 * (h - l));
		r2 = pp + ( 0.618 * (h - l));
		r3 = pp + ( 1 * (h - l));
	}

}