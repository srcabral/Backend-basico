package br.ufg.inf.backend.tasks.model;


public class Task {
	private String myTask;
	private String description;
	private TaskPriority priority;
	
	
	public Task(String myTask, String description, TaskPriority priority) {
		this.myTask = myTask;
		this.priority = priority;
		this.description = description;
	}
	
	
	public String getMyTask() {
		return this.myTask;
	}
	
	public void setMyTask(String myTask) {
		this.myTask = myTask;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public TaskPriority getPriority() {
		return this.priority;
	}
	
	public void setTaskPriority(TaskPriority priority) {
		this.priority = priority;
	}
	
}
