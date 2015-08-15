package net.xupoh.megalauncher.main;

/**
 *
 * @author Илья
 */
public class Starter {
	public static void log(String text) {
		System.out.println(String.format("[STDINFO]: %s", text));
	}

	public static void main(String[] args) {
		long start = System.nanoTime();
		Starter.log("[Starter] Started with " + args.length + " args.");

		Engine.init(start);
	}
}
