package br.com.silvestre.zipcodeSearch.repository;

import br.com.silvestre.zipcodeSearch.model.ZipcodeSearchRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZipcodeSearchRepository extends JpaRepository<ZipcodeSearchRequest, Long> {

  // save method heritage from JpaRepository
  // zipcodeSearchRepository.save(request);

    //search ordered history
    List<ZipcodeSearchRequest> findAllByOrderByRequestTimeDesc();


}
