package com.wearegoing.WeAreGoing.Info;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoService
{

	@Autowired
	private InfoRepository infoRepository;

	public Collection<Info> getAllInfo()
		{
			List<Info> info = new ArrayList<>();
			infoRepository.findAll().forEach(info::add);

			return info;
		}

	public Info getInfo(String id)
		{
			Info info;

			try
				{
					info = infoRepository.findById(Integer.parseInt(id)).get();
				}
			catch(NoSuchElementException ex)
				{
					info = null;
				}

			return info;
		}

	public void incrementAmount()
		{
			infoRepository.incrementAmount();
		}
	
	public void resetAmount() {
		infoRepository.resetAmount();
	}

	public void deleteInfo(String id)
		{
			infoRepository.deleteById(Integer.parseInt(id));
		}

}
