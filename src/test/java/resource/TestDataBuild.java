package resource;

import java.util.ArrayList;
import java.util.List;

import pojo.AddPlace;
import pojo.Location;

public class TestDataBuild {

	public AddPlace addPlacePayLoad(String name, String language, String address ) {
		AddPlace pl = new AddPlace();
		pl.setAccuracy(50);
		pl.setAddress(address);
		pl.setLanguage(language);
		pl.setPhone_number("(+91) 983 893 3937");
		pl.setWebsite("https://rahulshettyacademy.com");
		pl.setName(name);
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		pl.setTypes(myList);
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		pl.setLocation(l);
		
		return pl;
	}
	
	public String deletePlacePayload(String placeId) {
		return "{\r\n    \"place_id\":\""+placeId+"\"\r\n}";
	}
	
}
