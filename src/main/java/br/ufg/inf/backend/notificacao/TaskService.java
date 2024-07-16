package br.ufg.inf.backend.notificacao;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.backend.tasks.model.Task;
import br.ufg.inf.backend.tasks.model.TaskPriority;

public class TaskService {
	
	

	private static List<Task> tasksList = new ArrayList<>();

	private NotificacaoService notificacaoService;
	
	public TaskService() {
	}
	
	public TaskService(NotificacaoService notificacaoService) {
		this.notificacaoService = notificacaoService;
	}
	
	
	public String adicionar(String myTask, String myPriority) {
		Task newTask = new Task(myTask, searchPriority(myPriority));
		
		for (Task task : tasksList) {
			if (task.getTaskName().equals(newTask.getTaskName())) {
				return "Estah tarefa ja existe!";
			}
		}
		
		tasksList.add(newTask);
		
		return notificacaoService.enviarNotificacao("adicionar");
	}
	
	public void atualizar() {
		
	}
	
	public void remover() {
		
	}
	
	public String listar() {
		if (tasksList.size() == 0) {
			return "Você não possui tarefas!";
		}
		String myTasks = "";
		for (Task task : tasksList) {
			myTasks += tasksList.indexOf(task) + 1 + ": " + task.getTaskName() + " - Prioridade: " + task.getTaskPriority() + "\n";
		}
		return myTasks;
	}
	
	private TaskPriority searchPriority(String myPriority) {
		try { 
			return TaskPriority.getPriority(myPriority);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Prioridade informada invalida!! Informe entre: alta, media ou baixa");
		}
		
	}
}
