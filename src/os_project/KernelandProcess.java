package os_project;

public class KernelandProcess {

	private static int nextpid = 1;
	private int pid = nextpid--;
	private boolean hasStarted;
	private Thread thread;
	private Priority priority;
	private long wakeTime;

	public KernelandProcess(UserlandProcess up) {
		KernelandProcess.nextpid++;
		this.pid = nextpid--;
		this.hasStarted = false;
		this.thread = new Thread(up);
		this.priority = Priority.INTERACTIVE;
	}
	
	public KernelandProcess(UserlandProcess up, Priority priority) {
		KernelandProcess.nextpid++;
		this.pid = nextpid--;
		this.hasStarted = false;
		this.thread = new Thread(up);
		this.priority = priority;
	}

	/*
	 * Retrieves pid
	 * @Return int pid
	 */
	public int getThreadPid() {
		return pid;
	}

	/*
	 * Check if current process is alive. If yes, suspends the current process
	 */
	@SuppressWarnings("removal")
	public void stop() {
		if (thread.isAlive()) {
			try {
				thread.suspend();
			}catch(Exception e) {};
		}
	}

	/*
	 * Checks if the current process hasStart and isAlive. If yes, returns true otherwise returns false
	 * @Return boolean
	 */
	public boolean isDone() {
		if (hasStarted && !thread.isAlive()) {
			return true;
		}
		return false;
	}


	/*
	 * Retrieves hasStarted
	 * @Return boolean hasStarted
	 */
	public boolean isHasStarted() {
		return hasStarted;
	}

	/*
	 * resume or start, update started
	 * Check if the current thread has not started. If true, start the thread otherwise check if the 
	 * current thread is waiting. If true, resume the thread
	 */
	@SuppressWarnings("removal")
	public void run() {
		if (!isHasStarted()) {
			thread.start();
			hasStarted = true;
		}else if(thread.getState() == Thread.State.WAITING) {

			try {
				thread.resume();
			}catch(Exception e) {};
		}
	}
	
	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public long getWakeTime() {
		return wakeTime;
	}

	public void setWakeTime(long wakeTime) {
		this.wakeTime = wakeTime;
	}
	
	
}
