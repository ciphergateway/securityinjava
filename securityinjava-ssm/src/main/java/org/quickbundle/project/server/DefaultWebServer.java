package org.quickbundle.project.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Locale;
import java.util.StringTokenizer;

import org.quickbundle.tools.helper.RmStringHelper;

public class DefaultWebServer {
	
	public static void main(String[] args) throws Exception {
		createPidDesc();
//		RmWebServer server = new RmWebServer();
//		server.start(getPort(args));
	}
	
	static int getPort(String[] args) {
		int port = 8080;
		if(args.length > 0) {
			String arg = args[0];
			String[] keyValues = arg.split("\\s+");
			for(String kv : keyValues) {
				if(kv.startsWith("port=")) {
					String portStr = kv.substring("port=".length());
					try {
						port = Integer.parseInt(portStr);
					} catch (Exception e) {
						e.printStackTrace();
						//ignore
					}
				}
			}
		}
		return port;
	}
	
	static void createPidDesc() {
		String javaPid = getPID();
		File pidFile = new File(System.getProperty("java.io.tmpdir") + File.separator + "df.pid");
		RmStringHelper.writeStringToFile(javaPid, pidFile);
	}

	
	static String getPID() {
		String pid = System.getProperty("pid");
		if (pid == null) {
			// first, reliable with sun jdk (http://golesny.de/wiki/code:javahowtogetpid)
			final RuntimeMXBean rtb = ManagementFactory.getRuntimeMXBean();
			final String processName = rtb.getName();
			/* tested on: */
			/* - windows xp sp 2, java 1.5.0_13 */
			/* - mac os x 10.4.10, java 1.5.0 */
			/* - debian linux, java 1.5.0_13 */
			/* all return pid@host, e.g 2204@antonius */

			if (processName.indexOf('@') != -1) {
				pid = processName.substring(0, processName.indexOf('@'));
			} else {
				pid = getPIDFromOS();
			}
			System.setProperty("pid", pid);
		}
		return pid;
	}
	
	static String getPIDFromOS() {
		String pid;
		// following is not always reliable as is (for example, see issue 3 on solaris 10
		// or http://blog.igorminar.com/2007/03/how-java-application-can-discover-its.html)
		// Author: Santhosh Kumar T, http://code.google.com/p/jlibs/, licence LGPL
		// Author getpids.exe: Daniel Scheibli, http://www.scheibli.com/projects/getpids/index.html, licence GPL
		final String[] cmd;
		File tempFile = null;
		Process process = null;
		try {
			try {
				if (!System.getProperty("os.name").toLowerCase(Locale.getDefault())
						.contains("windows")) {
					cmd = new String[] { "/bin/sh", "-c", "echo $$ $PPID" };
				} else {
					// getpids.exe is taken from http://www.scheibli.com/projects/getpids/index.html (GPL)
					tempFile = File.createTempFile("getpids", ".exe");

					// extract the embedded getpids.exe file from the jar and save it to above file
					pump(DefaultWebServer.class.getResourceAsStream("resource/getpids.exe"),
							new FileOutputStream(tempFile), true, true);
					cmd = new String[] { tempFile.getAbsolutePath() };
				}
				process = Runtime.getRuntime().exec(cmd);
				final ByteArrayOutputStream bout = new ByteArrayOutputStream();
				pump(process.getInputStream(), bout, false, true);

				final StringTokenizer stok = new StringTokenizer(bout.toString());
				stok.nextToken(); // this is pid of the process we spanned
				pid = stok.nextToken();

				// waitFor nécessaire sous windows server 2003
				// (sinon le fichier temporaire getpidsxxx.exe n'est pas effacé)
				process.waitFor();
			} finally {
				if (process != null) {
					// évitons http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6462165
					process.getInputStream().close();
					process.getOutputStream().close();
					process.getErrorStream().close();
					process.destroy();
				}
				if (tempFile != null && !tempFile.delete()) {
					tempFile.deleteOnExit();
				}
			}
		} catch (final InterruptedException e) {
			pid = e.toString();
		} catch (final IOException e) {
			pid = e.toString();
		}
		return pid;
	}
	
	private static void pump(InputStream is, OutputStream os, boolean closeIn, boolean closeOut)
			throws IOException {
		try {
			pump(is, os);
		} finally {
			try {
				if (closeIn) {
					is.close();
				}
			} finally {
				if (closeOut) {
					os.close();
				}
			}
		}
	}
	
	static void pump(InputStream input, OutputStream output) throws IOException {
		final byte[] bytes = new byte[4 * 1024];
		int length = input.read(bytes);
		while (length != -1) {
			output.write(bytes, 0, length);
			length = input.read(bytes);
		}
	}
}
