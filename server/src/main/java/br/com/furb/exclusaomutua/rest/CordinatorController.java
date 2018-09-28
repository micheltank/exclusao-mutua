package br.com.furb.exclusaomutua.rest;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.furb.exclusaomutua.model.Resource;
import br.com.furb.exclusaomutua.rest.vm.RequestResourceVM;
import br.com.furb.exclusaomutua.service.CordinatorService;

@RestController
@RequestMapping("/api")
public class CordinatorController {

	@Autowired
	private CordinatorService service;
	
	@PostMapping("/request-resource")
	public ResponseEntity<Resource> requestResource(@RequestBody RequestResourceVM requestResourceVM) throws URISyntaxException {
		if (service.requestResource(requestResourceVM)) {
			return new ResponseEntity<Resource>(requestResourceVM.getResource(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Resource>(requestResourceVM.getResource(), HttpStatus.LOCKED);
		}
	}
	
	@PostMapping("/release-resource")
	public ResponseEntity<Void> releaseResource(@RequestBody Resource resource) throws URISyntaxException {
		service.releaseResource(resource);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
