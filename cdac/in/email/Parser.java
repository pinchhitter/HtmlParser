package cdac.in.email;

import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.jsoup.*;
import java.util.*;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

class Parser{

	static Map<String, String> done = new HashMap<String, String>();

	/* http://www.sahodayaschools.org/list_of_sahodaya.php */

	static void Sahodya(String url){
		try{
			Document doc = Jsoup.connect( url ). get();
			//System.out.println( doc.title() );
			Elements links = doc.select("a[href]");
			//System.out.println("nLinks: "+links.size());
			System.out.println("Sahodaya Name, First Name, Last Name, Email, Website Address, No of Member School, Country, State, City, Zip Code, Phone, Fax");
			for (Element link : links) {
				if( link.attr("abs:href").indexOf("popup.php") >= 0 ){
					Document tdoc = Jsoup.connect( link.attr("abs:href")  ). get();
					String data = tdoc.text().trim();
					data = data.replace("Sahodaya Name:","\"");
					data = data.replace("First Name:","\",\"");
					data = data.replace("Last Name:","\",\"");
					data = data.replace("Email:","\",\"");
					data = data.replace("Website Address:","\",\"");
					data = data.replace("No of Member School:","\",\"");
					data = data.replace("Country:","\",\"");
					data = data.replace("State:","\",\"");
					data = data.replace("City:","\",\"");
					data = data.replace("Zip Code:","\",\"");
					data = data.replace("Phone:","\",\"");
					data = data.replace("Fax:","\",\"");
					data = data.replaceAll("\" ","\"");
					data = data.replaceAll(" \"","\"") + "\"";
					//System.out.println(data);
					String tokens[] = data.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
					String first = "";	
					for(String token: tokens){
						if( token.indexOf(",") >= 0){
							System.out.print(first+""+ token.trim());
						}else{
							System.out.print(first+""+token.replaceAll("\"", "").trim());
						}
						first = ", ";
					}
					System.out.println();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/*https://kvsangathan.nic.in/about-kvs/directories/kvs-directory?shs_term_node_tid_depth=All&field_kv_directory_tid=&field_state_directory_tid=All*/
	static void KVD(String url){
		System.out.println("Sr. No., KV Name, Principal Name, Office Address, Email Address, Phone No, Website, State, District, Remarks");
		KVD(url, true);
	}	

	static void KVD(String url, boolean header){
		try{
			Document doc = Jsoup.connect( url ). get();
			//System.out.println( doc.title() );
			Elements trs = doc.select("tr");
			boolean firstL = true;
			for(Element row: trs){
				if( firstL ){
					firstL = false;
					continue;
				} 
		 
				Elements tds = row.select("td");
				String first = "";	
				for(Element td: tds){
					String token = td.text();
					if( token.indexOf(",") >= 0){
						System.out.print(first+""+token.trim());
					}else{
						System.out.print(first+""+token.replaceAll("\"", "").trim());
					}
					first = ", ";
				}
				System.out.println();
			}

			Elements links = doc.select("a[href]");
			//System.out.println("nLinks: "+links.size());
			for (Element link : links) {
				if( link.attr("abs:href").indexOf("shs_term_node_tid_depth") >= 0 ){
					if( !done.containsKey(  link.attr("abs:href").trim() ) ){
						//System.out.println( link.attr("abs:href").trim() );
						done.put( link.attr("abs:href").trim(),  "true" );
						KVD( link.attr("abs:href").trim(), true );
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	static void CBSE(String url, boolean header){
		try{
			Response response = Jsoup.connect("http://cbseaff.nic.in/cbse_aff/schdir_Report/userview.aspx")
			.cookie("ASP.NET_SessionId","o3gyibs2w2v5kgfeixm4v3na")
			.userAgent("Mozilla/5.0")
			.timeout(10 * 1000)
			.method(Method.POST)
			.data("__VIEWSTATEGENERATOR","AC872EDD")
			.data("__VIEWSTATEENCRYPTED","")
			.data("__EVENTTARGET","")
			.data("__EVENTARGUMENT","")
			.data("__LASTFOCUS","")
			.data("__LASTFOCUS","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")
			.data("ctl00%24ContentPlaceHolder1%24optlist","State+Wise")
			.data("ctl00%24ContentPlaceHolder1%24ddlitem","25")
			.data("ctl00%24ContentPlaceHolder1%24DropDownDistrict","08")
			.data("ctl00$ContentPlaceHolder1$keytext","")
			.data("ctl00%24ContentPlaceHolder1%24search","Search")
			.execute();
            
            		//parse the document from response
            		Document doc = response.parse();

			System.out.println( doc.text() );
			System.out.println( doc.outerHtml() );

			//Document doc = Jsoup.connect( url ). get();
			//System.out.println( doc.title() );

			Elements trs = doc.select("div");
			boolean firstL = true;
			for(Element row: trs){
				if( firstL ){
					firstL = false;
					continue;
				} 
		 
				Elements tds = row.select("td");
				String first = "";	
				for(Element td: tds){
					String token = td.text();
					if( token.indexOf(",") >= 0){
						System.out.print(first+""+token.trim());
					}else{
						System.out.print(first+""+token.replaceAll("\"", "").trim());
					}
					first = ", ";
				}
				System.out.println();
			}

			/*
			Elements links = doc.select("a[href]");
			//System.out.println("nLinks: "+links.size());
			for (Element link : links) {
				if( link.attr("abs:href").indexOf("shs_term_node_tid_depth") >= 0 ){
					if( !done.containsKey(  link.attr("abs:href").trim() ) ){
						//System.out.println( link.attr("abs:href").trim() );
						done.put( link.attr("abs:href").trim(),  "true" );
						CBSE( link.attr("abs:href").trim(), true );
					}
				}
			}
			*/
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public static void main(String[] args){
		String url = null;
		String what = "KV";
		try{
			int i  = 0;
			
			while ( i < args.length ) {
				if( args[i].equals("-u") )
					url = args[ ++i ].trim();
				if( args[i].equals("-w") )
					what = args[ ++i ].trim();
				i++;
			}
			if( url == null ){
				System.err.println("Uses: -u <url-to-be-parsed> -w <KV/SAH>");
				System.exit(0);
			}

			CBSE(url, true);
			/*
			if( what.equals("KV") )
				KVD( url );
			else if ( what.equals("SAH")  )
				Sahodya(url);
			*/
		}catch(Exception e){
			e.printStackTrace();
		}

	}
}
