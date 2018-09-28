package br.com.furb.gerenciadorprocessos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Manager {

	private Process process;
	private Thread thread;
	
	@Scheduled(fixedDelay = 60000)
	public void executarTask() throws IOException {
		if (process != null)
			process.destroy();

		System.out.println("Restarting cordinator");
		ProcessBuilder pb = new ProcessBuilder("java", "-jar", "C:/dev/repo/exclusaomutua/server.jar");
		process = pb.start();
		BufferedReader output = getOutput(process);

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				String ligne;
				try {
					while ((ligne = output.readLine()) != null) {
						System.out.println(ligne);
					}
				} catch (IOException e) {}
			}
		};
		thread = new Thread(runnable);
		thread.start();
	}

	private static BufferedReader getOutput(Process p) {
		return new BufferedReader(new InputStreamReader(p.getInputStream()));
	}

}
