package br.com.furb.exclusaomutua.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.furb.exclusaomutua.model.Process;
import br.com.furb.exclusaomutua.model.Resource;
import br.com.furb.exclusaomutua.rest.vm.RequestResourceVM;

@Component
@Scope("singleton")
public class CordinatorService {

	private Map<Resource, Process> mapResourceProcess;
	
	public CordinatorService() {
		mapResourceProcess = new HashMap<Resource, Process>();
		setProcess(new Resource("A"), null);
		setProcess(new Resource("B"), null);
		setProcess(new Resource("C"), null);
	}

	public boolean requestResource(RequestResourceVM requestResourceVM) {
		System.out.println(String.format("[REQUESTING] Process: %d. Resource: %s", requestResourceVM.getProcess().getId(), requestResourceVM.getResource().getName()));
		Resource resource = findResource(requestResourceVM.getResource().getName());
		Process process = requestResourceVM.getProcess();
		boolean resourceReleased = resourceRelesead(resource);
		if (resourceReleased) {	
			setProcess(resource, process);
		}
		if (resourceReleased) {
			System.out.println("Resource linked to process id " + process.getId());
		} else {
			System.out.println("Resource is in use by another process");
		}
		return resourceReleased;
	}

	private boolean resourceRelesead(Resource resource) {
		Process processUsingResource = getProcess(resource);
		boolean resourceReleased = processUsingResource == null;
		return resourceReleased;
	}

	public Resource findResource(String resource) {
		for (Resource resourceMap : getResources()) {
			if (resourceMap == null) continue;
			if (resource.equals(resourceMap.getName())) {
				return resourceMap;
			}			
		}
		return null;
	}

	public Process findProcess(Integer process) {
		for (Process resourceMap : getProcesses()) {
			if (resourceMap == null) continue;
			if (process == resourceMap.getId()) {
				return resourceMap;
			}			
		}
		return null;
	}
	
	public void releaseResource(Resource resource) {
		System.out.println("[RELEASING] Resource: " + resource.getName());
		Resource resourceSearched = findResource(resource.getName());
		setProcess(resourceSearched, null);
	}
	
	public synchronized void setProcess(Resource resource, Process process) {
		mapResourceProcess.put(resource, process);
	}
	
	public synchronized Set<Resource> getResources() {
		return mapResourceProcess.keySet();
	}
	
	public synchronized Collection<Process> getProcesses() {
		return mapResourceProcess.values();
	}
	
	public synchronized Process getProcess(Resource resource) {
		return mapResourceProcess.get(resource);
	}
}
