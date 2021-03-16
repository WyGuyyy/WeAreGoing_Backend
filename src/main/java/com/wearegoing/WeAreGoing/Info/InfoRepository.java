package com.wearegoing.WeAreGoing.Info;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

public interface InfoRepository extends CrudRepository<Info, Integer>
{
	@Modifying
	@Transactional
	@Query(value="UPDATE info SET amount = amount + 1 WHERE info_id = 1", nativeQuery=true)
	@RestResource(exported = false)
	public void incrementAmount();
	
	@Modifying
	@Transactional
	@Query(value="UPDATE info SET amount = 0 WHERE info_id = 1", nativeQuery=true)
	@RestResource(exported = false)
	public void resetAmount();
	
	@Modifying
	@Transactional
	@Query(value="UPDATE info SET amount = ?1 WHERE info_id = 1", nativeQuery=true)
	@RestResource(exported = false)
	public void setAmount(int newAmount);
}
