package com.wearegoing.WeAreGoing.Info;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wearegoing.WeAreGoing.API.*;

@CrossOrigin(origins = API.CLIENT_CORS)
@RestController
public class InfoController
{

	@Autowired
	InfoService infoService;

	/*@RequestMapping("/api/info/{info_id}")
	public Info getInfo(@PathVariable("info_id") String id)
		{

			try
				{
					return infoService.getInfo(id);
				}
			catch(Exception e)
				{
					return null;
				}
		}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/info/increment")
	public void incrementAmount()
		{
			infoService.incrementAmount();
		}
	
	@RequestMapping(method = RequestMethod.POST, value = "/api/info")
	public void addInfo()
		{
			infoService.incrementAmount();
		}

	@RequestMapping(method = RequestMethod.PUT, value = "/api/info")
	public void updateInfo()
		{
			infoService.incrementAmount();
		}

	@RequestMapping(method = RequestMethod.DELETE, value = "/api/info/{info_id}")
	public void deleteInfo(@PathVariable("info_id") String id)
		{
			infoService.deleteInfo(id);
		}*/

	@RequestMapping("/api/info")
	public Collection<Info> getAllInfo()
		{
			Collection<Info> colInfo = infoService.getAllInfo();

			if (colInfo.isEmpty())
				{
					return null;
				}
			else
				{
					return colInfo;
				}
		}
}

