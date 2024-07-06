package br.ufg.inf.backend.tasks.model;

public enum TaskPriority {
	ALTA,
	MEDIA,
	BAIXA;
	
	

	public static TaskPriority getPriority(String priority) {
		for (TaskPriority item : TaskPriority.values()) {
			if (item.toString() == priority.toUpperCase()) {
				return item;
			}
		}
		return null;
	}
}
