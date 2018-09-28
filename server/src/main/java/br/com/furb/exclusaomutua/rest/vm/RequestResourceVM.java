package br.com.furb.exclusaomutua.rest.vm;

import br.com.furb.exclusaomutua.model.Process;
import br.com.furb.exclusaomutua.model.Resource;

public class RequestResourceVM {
	
	private Process process;
	private Resource resource;
	
	public RequestResourceVM(Process process, Resource resource) {
		super();
		this.process = process;
		this.resource = resource;
	}
	
	public Process getProcess() {
		return process;
	}
	public void setProcess(Process process) {
		this.process = process;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	
}
